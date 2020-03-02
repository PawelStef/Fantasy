package PremierLeague.Fantasy.service;

import PremierLeague.Fantasy.repository.AccountRepository;
import PremierLeague.Fantasy.repository.AccountRoleRepository;
import PremierLeague.Fantasy.model.Account;
import PremierLeague.Fantasy.model.AccountRole;
import PremierLeague.Fantasy.model.UserPhoto;
import PremierLeague.Fantasy.model.dto.AccountPasswordResetRequest;
import PremierLeague.Fantasy.repository.UserPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;
    private AccountRoleRepository accountRoleRepository;
    private UserPhotoRepository userPhotoRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService, AccountRoleRepository accountRoleRepository, UserPhotoRepository userPhotoRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
        this.accountRoleRepository = accountRoleRepository;
        this.userPhotoRepository = userPhotoRepository;
    }

    public boolean register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return false;
        }

        // szyfrowanie hasła
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountRoles(accountRoleService.getDefaultRoles());

        // zapis do bazy
        accountRepository.save(account);

        return true;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void toggleLock(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);
            account.setLocked(!account.isLocked());

            accountRepository.save(account);
        }
    }

    public void remove(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);

            if (!account.isAdmin()) {
                accountRepository.delete(account);
            }
        }
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public void resetPassword(AccountPasswordResetRequest request) {
        if (accountRepository.existsById(request.getAccountId())) {
            Account account = accountRepository.getOne(request.getAccountId());

            account.setPassword(passwordEncoder.encode(request.getResetpassword()));

            accountRepository.save(account);
        }
    }

    public void editRoles(Long accountId, HttpServletRequest request) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);

            // kluczem w form parameters jest nazwa parametru th:name
            Map<String, String[]> formParameters = request.getParameterMap();
            Set<AccountRole> newCollectionOfRoles = new HashSet<>();

            for (String roleName : formParameters.keySet()) {
                String[] values = formParameters.get(roleName);

                if (values[0].equals("on")) {
                    Optional<AccountRole> accountRoleOptional = accountRoleRepository.findByName(roleName);

                    if (accountRoleOptional.isPresent()) {
                        newCollectionOfRoles.add(accountRoleOptional.get());
                    }
                }
            }

            account.setAccountRoles(newCollectionOfRoles);

            accountRepository.save(account);
        }
    }

    public void savePhotoFor(String name, MultipartFile photo) {
        Optional<Account> account = accountRepository.findByUsername(name);
        if (account.isPresent()) {
            Account acc = account.get();

            try {
                UserPhoto userPhoto = acc.getPhoto() != null ? acc.getPhoto() : new UserPhoto();
                userPhoto.setFoto(photo.getBytes());

                userPhotoRepository.save(userPhoto);

                acc.setPhoto(userPhoto);
                accountRepository.save(acc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        error
    }

    public String findPhotoByUsername(String name) {
        Optional<Account> account = accountRepository.findByUsername(name);
        if (account.isPresent()) {
            Account acc = account.get();

            return acc.convertBinImageToString();
        }
        return "";
    }
}

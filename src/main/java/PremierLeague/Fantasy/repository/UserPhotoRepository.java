package PremierLeague.Fantasy.repository;

import PremierLeague.Fantasy.model.login.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
}

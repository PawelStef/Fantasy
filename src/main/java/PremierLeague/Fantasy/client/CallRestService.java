package PremierLeague.Fantasy.client;

import PremierLeague.Fantasy.model.appLogic.teamHistory.TeamHistory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallRestService implements CommandLineRunner {

    private static void callRestServiceTaeamHistory() {


        //Uri uri= new Uri("https://fantasy.premierleague.com/api/entry/"+teamId+"/history/");
        String uri = "https://fantasy.premierleague.com/api/entry/" + "390411" + "/history/";
        RestTemplate restTemplate = new RestTemplate();
        //TeamHistory teamHistory = restTemplate.getForObject(uri,TeamHistory.class);
        //TeamHistory teamHistory = new TeamHistory();
        System.out.println("pobrano dane twojego teamu"); //+ teamHistory.toString());

    }

    @Override
    public void run(String... args) throws Exception {
        callRestServiceTaeamHistory();
    }


}

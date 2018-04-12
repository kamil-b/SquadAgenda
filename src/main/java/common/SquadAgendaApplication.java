package common;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SquadAgendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquadAgendaApplication.class, args);
    }

}

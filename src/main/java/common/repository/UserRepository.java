package common.repository;

import common.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findById(String id);

    Mono<UserDetails> findByUsername(String username);

    Mono<User> findByEmail(String email);
}


//https://spring.io/blog/2016/11/28/going-reactive-with-spring-data
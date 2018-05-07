package common.repository;

import common.model.Board;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BoardRepository extends ReactiveMongoRepository<Board, String> {

    Mono<Board> findById(String id);

    Mono<Board> findByName(String name);
}

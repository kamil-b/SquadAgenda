package common.repository;

import common.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {

    Optional<Board> findById(String id);
}

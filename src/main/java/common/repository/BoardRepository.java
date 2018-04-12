package common.repository;

import common.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

    Optional<Board> findById(String id);
}

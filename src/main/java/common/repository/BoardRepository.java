package common.repository;

import common.entities.Board;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, Long> {

    Optional<Board> findById(Long id);
}

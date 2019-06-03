package kanban.board.server.repository;

import kanban.board.server.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
}

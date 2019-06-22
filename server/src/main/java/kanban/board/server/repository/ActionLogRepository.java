package kanban.board.server.repository;

import kanban.board.server.model.ActionLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionLogRepository extends MongoRepository<ActionLog, String> {
}

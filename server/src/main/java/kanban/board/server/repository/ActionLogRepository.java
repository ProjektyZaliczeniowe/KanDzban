package kanban.board.server.repository;

import kanban.board.server.model.ActionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActionLogRepository extends MongoRepository<ActionLog, String> {
    List<ActionLog> getActionLogByObjectType(String objectType);
}

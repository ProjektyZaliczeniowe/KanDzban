package kanban.board.server.repository;

import kanban.board.server.model.Label;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LabelRepository extends MongoRepository<Label, String> {
}

package kanban.board.server.repository;

import kanban.board.server.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {

    ConfirmationToken findByTokenCode(String code);
}

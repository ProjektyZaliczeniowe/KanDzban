package kanban.board.server.repository;


import kanban.board.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);
    User findByEmail(String email);

}

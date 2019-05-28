package kanban.board.server.service.interfaces;

import kanban.board.server.model.Task;
import kanban.board.server.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface TaskService {
    List<Task> getAll();

    Task add(Task task);

    void deleteTaskById(String id);

    List<User> getTaskUsers(String id);

    Task modify(Task task);
}

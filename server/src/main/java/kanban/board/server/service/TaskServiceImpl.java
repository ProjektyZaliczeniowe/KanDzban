package kanban.board.server.service;

import kanban.board.server.model.Task;
import kanban.board.server.model.User;
import kanban.board.server.repository.TaskRepository;
import kanban.board.server.service.interfaces.TaskService;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task add(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(String id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found. Id: "+ id));
        taskRepository.delete(task);
    }

    @Override
    public List<User> getTaskUsers(String id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found. Id: " + id));
        return task.getAssignedUsers();
    }

    @Override
    public Task modify(Task task) {
        taskRepository
                .findById(task.getId())
                .orElseThrow(() -> new NotFoundException("Task not found. Id: " + task.getId()));
        return taskRepository.save(task);
    }
}

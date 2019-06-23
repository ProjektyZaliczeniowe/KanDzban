package kanban.board.server.service;

import kanban.board.server.model.ActionLog;
import kanban.board.server.model.Task;
import kanban.board.server.model.User;
import kanban.board.server.repository.TaskRepository;
import kanban.board.server.service.interfaces.ActionLogService;
import kanban.board.server.service.interfaces.TaskService;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ActionLogService actionLogService;

    public TaskServiceImpl(TaskRepository taskRepository, ActionLogService actionLogService) {
        this.taskRepository = taskRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task add(Task task) {
        Task savedTask = taskRepository.save(task);
        logTaskAction(savedTask, "Created");
        return savedTask;
    }

    @Override
    public void deleteTaskById(String id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found. Id: "+ id));
        taskRepository.delete(task);
        logTaskAction(task, "Deleted");
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
        Task modifiedTask = taskRepository.save(task);
        logTaskAction(modifiedTask, "Modified");
        return modifiedTask;
    }

    private void logTaskAction(Task task, String action) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("assignedUserIds", task.getAssignedUsers().stream().map(User::getId).collect(Collectors.toList()));
        logTaskAction(task, action, params);
    }

    private void logTaskAction(Task task, String action, Map<String, List<String>> params) {
        ActionLog actionLog = new ActionLog();
        actionLog.setObjectType(Task.class.getName());
        actionLog.setObjectId(task.getId());
        actionLog.setAction(action);
        actionLog.setActionDate(new Date());
        actionLog.setParams(params);
        actionLogService.add(actionLog);
    }
}

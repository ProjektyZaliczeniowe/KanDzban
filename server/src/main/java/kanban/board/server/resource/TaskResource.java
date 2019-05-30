package kanban.board.server.resource;

import kanban.board.server.model.Task;
import kanban.board.server.service.interfaces.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskResource {

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/tasks")
    public ResponseEntity getTasks() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/tasks")
    public ResponseEntity addTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.add(task), HttpStatus.CREATED);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable String id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/tasks/{id}/users")
    public ResponseEntity getAllTaskUsers(@PathVariable String id) {
        return new ResponseEntity<>(taskService.getTaskUsers(id), HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PutMapping("/tasks")
    public ResponseEntity editTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.modify(task), HttpStatus.OK);
    }
}

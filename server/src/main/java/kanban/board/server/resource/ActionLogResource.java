package kanban.board.server.resource;

import kanban.board.server.model.ActionLog;
import kanban.board.server.model.Task;
import kanban.board.server.repository.ActionLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActionLogResource {
    private final ActionLogRepository actionLogRepository;

    public ActionLogResource(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @GetMapping("/logActions/tasks/users/{userId}")
    public ResponseEntity getTaskActionsByUserId(@PathVariable String userId) {
        List<ActionLog> actionLogs = actionLogRepository.getActionLogByObjectType(Task.class.getName());
        return new ResponseEntity<>(actionLogs.stream().filter(l -> l.getParams().get("assignedUserIds").contains(userId)), HttpStatus.OK);
    }
}

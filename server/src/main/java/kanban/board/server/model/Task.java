package kanban.board.server.model;

import kanban.board.server.utils.TaskPriority;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<User> assignedUsers;
    private TaskPriority priority;

}

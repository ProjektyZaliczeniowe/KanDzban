package kanban.board.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Label {

    @Id
    String id;
    String name;
    Integer orderNumber;
    @DBRef
    List<Task> taskList;
}

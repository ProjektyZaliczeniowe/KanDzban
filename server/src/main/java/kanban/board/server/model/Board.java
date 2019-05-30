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
public class Board {

    @Id
    private String id;
    private String name;
    @DBRef
    private User owner;
    @DBRef
    private List<Label> labelList;
    @DBRef
    private List<User> boardMembers;
}

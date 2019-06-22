package kanban.board.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActionLog {

    @Id
    private String id;
    private String action;
    private String objectType;
    private String objectId;
    private Map<String, List<String>> params;
    private Date actionDate;
}

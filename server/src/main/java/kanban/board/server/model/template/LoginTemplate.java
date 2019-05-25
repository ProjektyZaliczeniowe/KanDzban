package kanban.board.server.model.template;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginTemplate {
    private String login;
    private String password;
}

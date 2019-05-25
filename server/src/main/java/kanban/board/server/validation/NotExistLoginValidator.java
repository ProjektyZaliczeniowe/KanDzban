package kanban.board.server.validation;

import kanban.board.server.validation.interfaces.NotExistLogin;
import kanban.board.server.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class NotExistLoginValidator implements ConstraintValidator<NotExistLogin, String> {

    @Autowired
    private UserService userService;

    public boolean isValid(String login, ConstraintValidatorContext context) {
        return login == null || isNull(userService.getUserByLogin(login));
    }

    public void initialize(NotExistLogin constraint) {
    }
}

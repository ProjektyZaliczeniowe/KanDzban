package kanban.board.server.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kanban.board.server.model.ConfirmationToken;
import kanban.board.server.model.User;
import kanban.board.server.model.template.LoginTemplate;
import kanban.board.server.service.interfaces.ConfirmationTokenService;
import kanban.board.server.service.interfaces.EmailSenderService;
import kanban.board.server.service.interfaces.UserService;
import kanban.board.server.utils.ApplicationError;
import kanban.board.server.utils.ApplicationResponse;
import kanban.board.server.utils.ErrorCodes;
import kanban.board.server.utils.ProjektUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
public class UserResource {

    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserResource(UserService userService, EmailSenderService emailSenderService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenService = confirmationTokenService;
    }


    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity saveUser(@Valid @RequestBody User user) throws URISyntaxException {
        String passwordEncode = ProjektUtils.passwordEncode(user.getPassword());
        user.setPassword(passwordEncode);
        userService.registerUser(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user.getId());
        confirmationTokenService.save(confirmationToken);
        sendEmailWithConfirmationTokenToUser(user, confirmationToken);

        return ResponseEntity.created(new URI("https://peaceful-sierra-14544.herokuapp.com/users/" + user.getLogin())).build();
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/confirm-account")
    public ResponseEntity confirmAccount(@RequestParam("tokenCode") String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.getByTokenCode(confirmationToken);

        if (token != null) {
            User user = userService.getUserById(token.getUserId());
            if (user.isEnabled()) {
                return new ResponseEntity<>(new ApplicationError(ErrorCodes.ACCOUNT_ALREADY_EXIST), HttpStatus.CONFLICT);
            } else {
                user.setEnabled(true);
                userService.registerUser(user);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new ApplicationError(ErrorCodes.TOKEN_NOT_FOUND), HttpStatus.NOT_FOUND);

    }


    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/users/{login}")
    public @ResponseBody
    ResponseEntity getUser(@PathVariable String login) {
        if (null != userService.getUserByLogin(login)) {
            return new ResponseEntity<>(userService.getUserByLogin(login), HttpStatus.OK);
        } else if (null != userService.getUserByEmail(login)) {
            return new ResponseEntity<>(userService.getUserByEmail(login), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApplicationError(ErrorCodes.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/users")
    public @ResponseBody
    ResponseEntity getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity postLogin(@RequestBody LoginTemplate loginTemplate) {
        User user = userService.getUserByLogin(loginTemplate.getLogin());

        if (user != null) {
            if (ProjektUtils.passwordVerify(user.getPassword(),loginTemplate.getPassword())) {
                return createToken(user);
            } else
                return new ResponseEntity<>(new ApplicationError(ErrorCodes.WRONG_PASSWORD), HttpStatus.NOT_FOUND);
        }

        user = userService.getUserByEmail(loginTemplate.getLogin());
        if (user != null) {
            if (ProjektUtils.passwordVerify(user.getPassword(),loginTemplate.getPassword())) {
                return createToken(user);
            } else
                return new ResponseEntity<>(new ApplicationError( ErrorCodes.WRONG_PASSWORD), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApplicationError(ErrorCodes.WRONG_LOGIN), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity createToken(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        String token = Jwts.builder()
                .claim("login", user.getLogin())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 7200000))
                .signWith(SignatureAlgorithm.HS512, "TbUL55^O|T<;UyT".getBytes())
                .compact();

        return new ResponseEntity<>(new ApplicationResponse(token), HttpStatus.OK);
    }

    private void sendEmailWithConfirmationTokenToUser(User user, ConfirmationToken confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration to ProjectBus!");
        mailMessage.setFrom("projektbus2019@gmail.com");
        mailMessage.setText("To confirm your account, please click here : \n"
                + "https://peaceful-sierra-14544.herokuapp.com/confirm-account?tokenCode=" + confirmationToken.getTokenCode());

        emailSenderService.sendEmail(mailMessage);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @DeleteMapping("/users/{login}")
    public ResponseEntity deleteUser(@PathVariable("login") String login) {
        User user = userService.getUserByLogin(login);
        if (null != user) {
            userService.deleteUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApplicationError(ErrorCodes.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}

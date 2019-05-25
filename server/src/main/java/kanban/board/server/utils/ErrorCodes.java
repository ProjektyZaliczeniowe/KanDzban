package kanban.board.server.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {
    /*
            USER
     */
    WRONG_LOGIN("Zły login"),
    WRONG_PASSWORD("Złe hasło"),
    ACCOUNT_ALREADY_EXIST("Konto już potwierdzone"),
    TOKEN_NOT_FOUND("Nie znaleziono tokenu"),
    MAIL_SEND("Mail został wysłany"),
    USER_NOT_FOUND("Nie znaleziono użytkownika");

    private String response;
}

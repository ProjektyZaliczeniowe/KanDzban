package kanban.board.server.service.interfaces;

import kanban.board.server.model.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken save(ConfirmationToken confirmationToken);
    ConfirmationToken getByTokenCode(String code);
}

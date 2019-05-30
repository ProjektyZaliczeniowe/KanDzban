package kanban.board.server.service;

import kanban.board.server.model.ConfirmationToken;
import kanban.board.server.repository.ConfirmationTokenRepository;
import kanban.board.server.service.interfaces.ConfirmationTokenService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmationToken getByTokenCode(String code) {
        return confirmationTokenRepository.findByTokenCode(code);
    }
}

package kanban.board.server.service;

import kanban.board.server.model.ActionLog;
import kanban.board.server.repository.ActionLogRepository;
import kanban.board.server.service.interfaces.ActionLogService;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ActionLogServiceImpl implements ActionLogService {

    private final ActionLogRepository actionLogRepository;

    public ActionLogServiceImpl(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    public ActionLog add(ActionLog actionLog) {
        return actionLogRepository.save(actionLog);
    }
}

package kanban.board.server.service.interfaces;

import kanban.board.server.model.ActionLog;

public interface ActionLogService {
    ActionLog add(ActionLog actionLog);
}

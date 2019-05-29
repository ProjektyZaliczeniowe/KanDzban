package kanban.board.server.service.interfaces;

import kanban.board.server.model.Label;

import java.util.List;

public interface LabelService {
    List<Label> getAllLabels();

    Label add(Label label);

    void deleteLabelById(String id);

    Label modify(Label task);
}

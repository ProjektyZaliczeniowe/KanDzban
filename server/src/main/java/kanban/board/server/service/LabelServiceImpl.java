package kanban.board.server.service;

import kanban.board.server.model.Label;
import kanban.board.server.repository.LabelRepository;
import kanban.board.server.service.interfaces.LabelService;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @Override
    public Label add(Label label) {
        return labelRepository.save(label);
    }

    @Override
    public void deleteLabelById(String id) {
        Label label = labelRepository.findById(id).orElseThrow(() -> new NotFoundException("Label not found. Id: "+ id));
        labelRepository.delete(label);
    }

    @Override
    public Label modify(Label label) {
        labelRepository
                .findById(label.getId())
                .orElseThrow(() -> new NotFoundException("Label not found. Id: " + label.getId()));
        return labelRepository.save(label);
    }
}

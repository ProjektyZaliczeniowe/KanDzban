package kanban.board.server.service;

import kanban.board.server.model.ActionLog;
import kanban.board.server.model.Label;
import kanban.board.server.model.Task;
import kanban.board.server.repository.LabelRepository;
import kanban.board.server.service.interfaces.ActionLogService;
import kanban.board.server.service.interfaces.LabelService;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final ActionLogService actionLogService;

    public LabelServiceImpl(LabelRepository labelRepository, ActionLogService actionLogService) {
        this.labelRepository = labelRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @Override
    public Label add(Label label) {
        Label savedLabel = labelRepository.save(label);
        logLabelAction(savedLabel, "Created");
        return savedLabel;
    }

    @Override
    public void deleteLabelById(String id) {
        Label label = labelRepository.findById(id).orElseThrow(() -> new NotFoundException("Label not found. Id: "+ id));
        labelRepository.delete(label);
        logLabelAction(label, "Deleted");
    }

    @Override
    public Label modify(Label label) {
        labelRepository
                .findById(label.getId())
                .orElseThrow(() -> new NotFoundException("Label not found. Id: " + label.getId()));
        Label modifiedLabel = labelRepository.save(label);
        logLabelAction(modifiedLabel, "Modified");
        return modifiedLabel;
    }

    private void logLabelAction(Label label, String action) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("taskListIds", label.getTaskList().stream().map(Task::getId).collect(Collectors.toList()));
        logLabelAction(label, action, params);
    }

    private void logLabelAction(Label label, String action, Map<String, List<String>> params) {
        ActionLog actionLog = new ActionLog();
        actionLog.setObjectType(Label.class.getName());
        actionLog.setObjectId(label.getId());
        actionLog.setAction(action);
        actionLog.setActionDate(new Date());
        actionLog.setParams(params);
        actionLogService.add(actionLog);
    }
}

package kanban.board.server.service;

import kanban.board.server.model.ActionLog;
import kanban.board.server.model.Board;
import kanban.board.server.model.Label;
import kanban.board.server.model.User;
import kanban.board.server.repository.BoardRepository;
import kanban.board.server.repository.LabelRepository;
import kanban.board.server.service.interfaces.ActionLogService;
import kanban.board.server.service.interfaces.BoardService;
import kanban.board.server.utils.exception.ConflictException;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final LabelRepository labelRepository;
    private final ActionLogService actionLogService;

    public BoardServiceImpl(BoardRepository boardRepository, LabelRepository labelRepository, ActionLogService actionLogService) {
        this.boardRepository = boardRepository;
        this.labelRepository = labelRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board add(Board board) {
        if(isDuplicatedOrderNumber(board.getLabelList())) {
            throw new ConflictException("Duplicated order number.");
        }
        Board createdBoard = boardRepository.save(board);
        logBoardAction(createdBoard, "Created");
        return createdBoard;
    }

    @Override
    public void deleteBoardById(String id) {
        Board board = boardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Board not found. Id: "+ id));
        boardRepository.delete(board);
        logBoardAction(board, "Deleted");
    }

    @Override
    public Board modify(Board board) {
        boardRepository
                .findById(board.getId())
                .orElseThrow(() -> new NotFoundException("Board not found. Id: " + board.getId()));
        if(isDuplicatedOrderNumber(board.getLabelList())) {
            throw new ConflictException("Duplicated order number.");
        }
        Board modifiedBoard = boardRepository.save(board);
        logBoardAction(modifiedBoard, "Modified");
        return modifiedBoard;
    }

    private boolean isDuplicatedOrderNumber(List<Label> labelList) {
        List<String> labelIdList = labelList.stream()
                .map(Label::getId)
                .collect(Collectors.toList());

        List<Label> referencedLabelList = StreamSupport
                .stream(labelRepository.findAllById(labelIdList).spliterator(),false)
                .collect(Collectors.toList());

        int individualNumberOfOrderNumber = referencedLabelList.stream()
                .map(Label::getOrderNumber)
                .collect(Collectors.toCollection(HashSet::new))
                .size();

        return individualNumberOfOrderNumber != referencedLabelList.size();
    }

    private void logBoardAction(Board board, String action) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("OwnerId", Collections.singletonList(board.getOwner().getId()));
        params.put("labelListIds", board.getLabelList().stream().map(Label::getId).collect(Collectors.toList()));
        params.put("boardMemberListIds", board.getBoardMembers().stream().map(User::getId).collect(Collectors.toList()));
        logBoardAction(board, action, params);
    }

    private void logBoardAction(Board board, String action, Map<String, List<String>> params) {
        ActionLog actionLog = new ActionLog();
        actionLog.setObjectType(Board.class.getName());
        actionLog.setObjectId(board.getId());
        actionLog.setAction(action);
        actionLog.setActionDate(new Date());
        actionLog.setParams(params);
        actionLogService.add(actionLog);
    }
}

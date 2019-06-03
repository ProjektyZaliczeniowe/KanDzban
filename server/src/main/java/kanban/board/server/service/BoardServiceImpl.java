package kanban.board.server.service;

import kanban.board.server.model.Board;
import kanban.board.server.model.Label;
import kanban.board.server.repository.BoardRepository;
import kanban.board.server.repository.LabelRepository;
import kanban.board.server.service.interfaces.BoardService;
import kanban.board.server.utils.exception.ConflictException;
import kanban.board.server.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final LabelRepository labelRepository;

    public BoardServiceImpl(BoardRepository boardRepository, LabelRepository labelRepository) {
        this.boardRepository = boardRepository;
        this.labelRepository = labelRepository;
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
        return boardRepository.save(board);
    }

    @Override
    public void deleteBoardById(String id) {
        Board board = boardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Board not found. Id: "+ id));
        boardRepository.delete(board);
    }

    @Override
    public Board modify(Board board) {
        boardRepository
                .findById(board.getId())
                .orElseThrow(() -> new NotFoundException("Board not found. Id: " + board.getId()));
        if(isDuplicatedOrderNumber(board.getLabelList())) {
            throw new ConflictException("Duplicated order number.");
        }
        return boardRepository.save(board);
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
}

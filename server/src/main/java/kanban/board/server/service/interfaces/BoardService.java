package kanban.board.server.service.interfaces;

import kanban.board.server.model.Board;

import java.util.List;

public interface BoardService {
    List<Board> getAll();

    Board add(Board board);

    void deleteBoardById(String id);

    Board modify(Board board);
}


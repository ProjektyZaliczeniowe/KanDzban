package kanban.board.server.resource;

import kanban.board.server.model.Board;
import kanban.board.server.model.User;
import kanban.board.server.service.interfaces.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardResource {

    private final BoardService boardService;

    public BoardResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/boards")
    public ResponseEntity getBoards() {
        return new ResponseEntity<>(boardService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/boards/{userLogin}")
    public ResponseEntity getBoardsForUser(@PathVariable String userLogin) {
        List<Board> boards = new ArrayList<>();
        for(Board board: boardService.getAll()){
            for(User tempUser : board.getBoardMembers()){
                if(tempUser.getLogin().equals(userLogin)) {
                    boards.add(board);
                    break;
                }
            }
        }
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
  
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/boards")
    public ResponseEntity addBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.add(board), HttpStatus.CREATED);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @DeleteMapping("/boards/{id}")
    public ResponseEntity deleteBoard(@PathVariable String id) {
        boardService.deleteBoardById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PutMapping("/boards")
    public ResponseEntity editBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.modify(board), HttpStatus.OK);
    }
}

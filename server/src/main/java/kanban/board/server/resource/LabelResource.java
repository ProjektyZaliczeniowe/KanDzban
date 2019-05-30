package kanban.board.server.resource;

import kanban.board.server.model.Label;
import kanban.board.server.service.interfaces.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LabelResource {

    private final LabelService labelService;

    public LabelResource(LabelService labelService) {
        this.labelService = labelService;
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/labels")
    public ResponseEntity getAllLabels() {
        return new ResponseEntity<>(labelService.getAllLabels(), HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/labels")
    public ResponseEntity addLabel(@RequestBody Label label) {
        return new ResponseEntity<>(labelService.add(label), HttpStatus.CREATED);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @DeleteMapping("/labels/{id}")
    public ResponseEntity deleteLabel(@PathVariable String id) {
        labelService.deleteLabelById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @PutMapping("/labels")
    public ResponseEntity editLabel(@RequestBody Label label) {
        return new ResponseEntity<>(labelService.modify(label), HttpStatus.OK);
    }
}

package kanban.board.server.utils;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String priority;

    TaskPriority(String priority) {
        this.priority = priority;
    }
}

package duke;

import java.io.IOException;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private int index;
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task t = tasks.delete(index);
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return ui.showDeleteTask(t, tasks.size());
    }
}

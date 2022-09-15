package duke;

import java.io.IOException;

/**
 * Duke is a chatbot that helps you keep track of your tasks.
 * It can add, delete, mark tasks as done, and list all your tasks.
 * Entry point of the Duke application.
 * Initializes the application and starts the interaction with the user.
 * @author Marcus Tan
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private boolean isExit = false;
    /**
     * Initializes the application.
     * @param filePath the path to the file where the tasks are stored
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            System.err.println(e.getMessage());
            tasks = new TaskList();
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            if (c.isExit()) {
                this.isExit = true;
            }
            return c.execute(tasks, ui, storage);
        } catch (DukeException e) {
            return ui.showError(e.getMessage(), "Please try again :-)");
        }
    }

    public boolean isExit() {
        return isExit;
    }
}

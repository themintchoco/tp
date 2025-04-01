package tutorly.logic.commands;

import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;

public class UndoCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_undo_success() {
        CommandResult expectedCommandResult = new CommandResult.Builder(UndoCommand.MESSAGE_SUCCESS)
                .reverseLast()
                .build();
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);
    }
}

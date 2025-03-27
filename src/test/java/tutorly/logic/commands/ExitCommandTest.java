package tutorly.logic.commands;

import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult.Builder(MESSAGE_EXIT_ACKNOWLEDGEMENT).exit().build();
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}

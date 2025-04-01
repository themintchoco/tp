package tutorly.logic.commands;

import tutorly.model.Model;

/**
 * Undoes the last command executed.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_STRING = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Last command undone!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult.Builder(MESSAGE_SUCCESS).reverseLast().build();
    }

}

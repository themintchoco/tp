package tutorly.logic.commands;

import tutorly.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_STRING = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions."
            + "\n\nExample: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult.Builder(SHOWING_HELP_MESSAGE).showHelp().build();
    }
}

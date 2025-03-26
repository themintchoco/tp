package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorly.commons.core.LogsCenter;
import tutorly.logic.commands.ClearCommand;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.ExitCommand;
import tutorly.logic.commands.HelpCommand;
import tutorly.logic.commands.SessionCommand;
import tutorly.logic.commands.StudentCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser implements Parser<Command> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        if (userInput.isBlank()) {
            return defaultCommand();
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        try {
            return parseCommand(commandWord, arguments);
        } catch (ParseException e) {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw e;
        }
    }

    /**
     * Parses a command word and arguments into a command.
     */
    protected Command parseCommand(String command, String args) throws ParseException {
        switch (command) {
        case StudentCommand.COMMAND_WORD:
            return new StudentCommandParser().parse(args);

        case SessionCommand.COMMAND_WORD:
            return new SessionCommandParser().parse(args);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    protected Command defaultCommand() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

}

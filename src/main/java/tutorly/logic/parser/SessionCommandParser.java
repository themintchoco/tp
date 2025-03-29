package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.commands.AssignSessionCommand;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.SearchSessionCommand;
import tutorly.logic.commands.SessionCommand;
import tutorly.logic.commands.UnassignSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Subparser for the session command.
 */
public class SessionCommandParser extends AddressBookParser {

    @Override
    protected Command parseCommand(String command, String args) throws ParseException {
        switch (command) {
        case AddSessionCommand.COMMAND_WORD:
            return new AddSessionCommandParser().parse(args);

        case SearchSessionCommand.COMMAND_WORD:
            return new SearchSessionCommandParser().parse(args);

        case AssignSessionCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(args);

        case UnassignSessionCommand.COMMAND_WORD:
            return new UnassignCommandParser().parse(args);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    @Override
    protected Command defaultCommand() {
        return new SessionCommand();
    }

}

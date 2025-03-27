package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.SessionCommand;
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

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    @Override
    protected Command defaultCommand() {
        return new SessionCommand();
    }

}

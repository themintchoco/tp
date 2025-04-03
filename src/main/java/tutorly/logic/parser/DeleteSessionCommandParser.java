package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.ParserUtil.parseSessionId;

import tutorly.logic.commands.DeleteSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSessionCommand object
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSessionCommand
     * and returns a DeleteSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteSessionCommand parse(String args) throws ParseException {
        try {
            int sessionId = parseSessionId(args.trim());
            return new DeleteSessionCommand(sessionId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE), pe);
        }
    }
}

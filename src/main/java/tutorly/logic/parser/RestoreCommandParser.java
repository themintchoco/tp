package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tutorly.commons.core.index.Index;
import tutorly.logic.commands.RestoreStudentCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RestoreCommand object
 */
public class RestoreCommandParser implements Parser<RestoreStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreStudentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RestoreStudentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreStudentCommand.MESSAGE_USAGE), pe);
        }
    }

}

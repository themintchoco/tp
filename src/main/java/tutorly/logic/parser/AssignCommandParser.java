package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.ParserUtil.parseId;

import java.util.Optional;

import tutorly.logic.commands.AssignStudentCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.Identity;

/**
 * Parses input arguments and creates a new AssignStudentCommand object
 */
public class AssignCommandParser implements Parser<AssignStudentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignStudentCommand
     * and returns a AssignStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION);
        Optional<String> sessionId = argMultimap.getValue(PREFIX_SESSION);

        if (sessionId.isEmpty() || sessionId.get().isBlank() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignStudentCommand.MESSAGE_USAGE));
        }

        Identity identity = ParserUtil.parseIdentity(argMultimap.getPreamble());
        return new AssignStudentCommand(identity, parseId(sessionId.get()));
    }
}

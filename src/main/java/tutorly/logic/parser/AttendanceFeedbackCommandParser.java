package tutorly.logic.parser;

import static tutorly.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.ParserUtil.parseId;

import java.util.Optional;

import tutorly.logic.commands.AttendanceFeedbackCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.Identity;

/**
 * Parses input arguments and creates a new AttendanceFeedbackCommandParser object
 */
public class AttendanceFeedbackCommandParser implements Parser<AttendanceFeedbackCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AttendanceFeedbackCommand
     * and returns a AttendanceFeedbackCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendanceFeedbackCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION, PREFIX_FEEDBACK);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_FEEDBACK);
        Optional<String> sessionId = argMultimap.getValue(PREFIX_SESSION);
        Optional<String> feedback = argMultimap.getValue(PREFIX_FEEDBACK);

        if (sessionId.isEmpty() || sessionId.get().isBlank()
                || argMultimap.getPreamble().isEmpty() || feedback.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceFeedbackCommand.MESSAGE_USAGE));
        }

        Identity identity = ParserUtil.parseIdentity(argMultimap.getPreamble());

        return new AttendanceFeedbackCommand(identity, parseId(sessionId.get()), feedback.get());
    }
}

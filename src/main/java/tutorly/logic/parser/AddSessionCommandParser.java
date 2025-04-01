package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import java.util.stream.Stream;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.session.Session;
import tutorly.model.session.Timeslot;

/**
 * Parses input arguments and creates a new AddSessionCommand object.
 */
public class AddSessionCommandParser implements Parser<AddSessionCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddSessionCommand
     * and returns an AddSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIMESLOT, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TIMESLOT, PREFIX_SUBJECT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TIMESLOT, PREFIX_SUBJECT);

        Timeslot timeslot = ParserUtil.parseTimeslot(argMultimap.getValue(PREFIX_TIMESLOT).get());
        String subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());

        Session session = new Session(timeslot, subject);
        return new AddSessionCommand(session);
    }
}

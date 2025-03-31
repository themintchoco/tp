package tutorly.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import tutorly.logic.Messages;
import tutorly.logic.commands.EditSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditSessionCommand object
 */
public class EditSessionCommandParser implements Parser<EditSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSessionCommand
     * and returns an EditSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_SESSION,
                PREFIX_TIMESLOT,
                PREFIX_SUBJECT);

        int sessionId;
        try {
            sessionId = ParserUtil.parseId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditSessionCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_TIMESLOT, PREFIX_SUBJECT);

        EditSessionCommand.EditSessionDescriptor editSessionDescriptor = new EditSessionCommand.EditSessionDescriptor();

        if (argMultimap.getValue(PREFIX_TIMESLOT).isPresent()) {
            editSessionDescriptor.setTimeslot(ParserUtil.parseTimeslot(argMultimap.getValue(PREFIX_TIMESLOT).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editSessionDescriptor.setSubject(ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }

        if (!editSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSessionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSessionCommand(sessionId, editSessionDescriptor);
    }
}

package tutorly.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_ID;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import tutorly.logic.commands.DeleteCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.Identity;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_ID, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID, PREFIX_NAME);

        Identity identity = new Identity();
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            identity.setId(ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            identity.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        return new DeleteCommand(identity);
    }

}

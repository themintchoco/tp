package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.Optional;

import tutorly.logic.commands.SearchCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Optional<String> query = argMultimap.getValue(PREFIX_NAME);

        if (query.isEmpty() || query.get().isBlank() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        String[] nameKeywords = query.get().trim().split("\\s+");

        return new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.ParserUtil.parseIndex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import tutorly.logic.commands.SearchCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.AttendSessionPredicate;
import tutorly.model.person.NameContainsKeywordsPredicate;
import tutorly.model.person.Person;
import tutorly.model.person.PhoneContainsKeywordsPredicate;
import tutorly.model.person.PredicateFilter;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION, PREFIX_NAME, PREFIX_PHONE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_NAME, PREFIX_PHONE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        return new SearchCommand(initFilter(argMultimap));
    }

    /**
     * Initializes filter combining all predicates for filtering persons using the given {@code ArgumentMultimap}.
     */
    private static PredicateFilter initFilter(ArgumentMultimap argumentMultimap) throws ParseException {
        List<Predicate<Person>> predicates = new ArrayList<>();

        Optional<String> sessionIdQuery = argumentMultimap.getValue(PREFIX_SESSION);
        if (sessionIdQuery.isPresent() && !sessionIdQuery.get().isBlank()) {
            int sessionId = parseIndex(sessionIdQuery.get()).getOneBased();
            predicates.add(new AttendSessionPredicate(sessionId));
        }

        Optional<String> nameQuery = argumentMultimap.getValue(PREFIX_NAME);
        if (nameQuery.isPresent() && !nameQuery.get().isBlank()) {
            String[] nameKeywords = nameQuery.get().trim().split("\\s+");
            predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        Optional<String> phoneQuery = argumentMultimap.getValue(PREFIX_PHONE);
        if (phoneQuery.isPresent() && !phoneQuery.get().isBlank()) {
            String[] phoneKeywords = phoneQuery.get().trim().split("\\s+");
            predicates.add(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
        }

        return new PredicateFilter(predicates);
    }
}

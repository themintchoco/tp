package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.ParserUtil.parseId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tutorly.logic.commands.SearchStudentCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.filter.AttendSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.filter.PhoneContainsKeywordsFilter;
import tutorly.model.person.Person;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchStudentCommandParser implements Parser<SearchStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION, PREFIX_NAME, PREFIX_PHONE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_NAME, PREFIX_PHONE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
        }

        return new SearchStudentCommand(initFilter(argMultimap));
    }

    /**
     * Initializes filter combining all predicates for filtering persons using the given {@code ArgumentMultimap}.
     */
    private static Filter<Person> initFilter(ArgumentMultimap argMultimap) throws ParseException {
        List<Filter<Person>> filters = new ArrayList<>();

        Optional<String> sessionIdQuery = argMultimap.getValue(PREFIX_SESSION);
        if (sessionIdQuery.isPresent() && !sessionIdQuery.get().isBlank()) {
            int sessionId = parseId(sessionIdQuery.get());
            filters.add(new AttendSessionFilter(sessionId));
        }

        Optional<String> nameQuery = argMultimap.getValue(PREFIX_NAME);
        if (nameQuery.isPresent() && !nameQuery.get().isBlank()) {
            String[] nameKeywords = nameQuery.get().trim().split("\\s+");
            filters.add(new NameContainsKeywordsFilter(Arrays.asList(nameKeywords)));
        }

        Optional<String> phoneQuery = argMultimap.getValue(PREFIX_PHONE);
        if (phoneQuery.isPresent() && !phoneQuery.get().isBlank()) {
            String[] phoneKeywords = phoneQuery.get().trim().split("\\s+");
            filters.add(new PhoneContainsKeywordsFilter(Arrays.asList(phoneKeywords)));
        }

        return Filter.any(filters);
    }
}

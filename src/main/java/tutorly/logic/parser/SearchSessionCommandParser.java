package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.ParserUtil.parseDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tutorly.logic.commands.SearchSessionCommand;
import tutorly.logic.commands.SearchStudentCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.filter.DateSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.SubjectContainsKeywordsFilter;
import tutorly.model.session.Session;

/**
 * Parses input arguments and creates a new SearchSessionCommand object
 */
public class SearchSessionCommandParser implements Parser<SearchSessionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchSessionCommand
     * and returns a SearchSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_SUBJECT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_NAME, PREFIX_PHONE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
        }

        return new SearchSessionCommand(initFilter(argMultimap));
    }

    /**
     * Initializes filter combining all predicates for filtering sessions using the given {@code ArgumentMultimap}.
     */
    private static Filter<Session> initFilter(ArgumentMultimap argMultimap) throws ParseException {
        List<Filter<Session>> filters = new ArrayList<>();

        Optional<String> dateQuery = argMultimap.getValue(PREFIX_DATE);
        if (dateQuery.isPresent() && !dateQuery.get().isBlank()) {
            LocalDate date = parseDate(dateQuery.get());
            filters.add(new DateSessionFilter(date));
        }

        Optional<String> subjectQuery = argMultimap.getValue(PREFIX_SUBJECT);
        if (subjectQuery.isPresent() && !subjectQuery.get().isBlank()) {
            String[] subjectKeywords = subjectQuery.get().trim().split("\\s+");
            filters.add(new SubjectContainsKeywordsFilter(Arrays.asList(subjectKeywords)));
        }

        return Filter.any(filters);
    }
}

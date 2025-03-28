package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.DATE_DESC;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorly.logic.commands.CommandTestUtil.VALID_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.SearchSessionCommand;
import tutorly.logic.commands.SearchStudentCommand;
import tutorly.model.filter.DateSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.SubjectContainsKeywordsFilter;
import tutorly.model.session.Session;

public class SearchSessionCommandParserTest {

    private final SearchSessionCommandParser parser = new SearchSessionCommandParser();

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(
                parser,
                PREAMBLE_NON_EMPTY + DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankKeywords_returnsSearchCommand() {
        Filter<Session> filter = Filter.any(List.of());
        SearchSessionCommand expectedSearchCommand = new SearchSessionCommand(filter);

        assertParseSuccess(
                parser,
                " " + PREFIX_DATE + " \t \n " + PREFIX_SUBJECT + " \n \t ",
                expectedSearchCommand);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        Filter<Session> filters = Filter.any(Arrays.asList(
                new DateSessionFilter(LocalDate.parse(VALID_DATE)),
                new SubjectContainsKeywordsFilter(Arrays.asList("Math", "En"))));
        SearchSessionCommand expectedSearchCommand = new SearchSessionCommand(filters);
        assertParseSuccess(
                parser,
                " " + DATE_DESC + " " + PREFIX_SUBJECT + "Math En",
                expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(
                parser,
                " " + DATE_DESC + " " + PREFIX_SUBJECT + " \n Math \n \t En  \t ",
                expectedSearchCommand);
    }
}

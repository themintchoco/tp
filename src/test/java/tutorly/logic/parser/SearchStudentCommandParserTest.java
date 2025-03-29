package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.SearchStudentCommand;
import tutorly.model.filter.Filter;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.filter.PhoneContainsKeywordsFilter;
import tutorly.model.person.Person;

public class SearchStudentCommandParserTest {

    private SearchStudentCommandParser parser = new SearchStudentCommandParser();

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(
                parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankKeywords_returnsSearchCommand() {
        Filter<Person> filter = Filter.any(List.of());
        SearchStudentCommand expectedSearchCommand = new SearchStudentCommand(filter);

        assertParseSuccess(
                parser,
                " " + PREFIX_SESSION + " \t \n " + PREFIX_NAME + " \t \n " + PREFIX_PHONE + " \n \t ",
                expectedSearchCommand);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        Filter<Person> filters = Filter.any(Arrays.asList(
                new NameContainsKeywordsFilter(Arrays.asList("Alice", "Bob")),
                new PhoneContainsKeywordsFilter(Arrays.asList("913", "8476"))));
        SearchStudentCommand expectedSearchCommand = new SearchStudentCommand(filters);
        assertParseSuccess(
                parser,
                " " + PREFIX_NAME + "Alice Bob " + PREFIX_PHONE + "913 8476",
                expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(
                parser,
                " " + PREFIX_NAME + " \n Alice \n \t Bob  \t " + PREFIX_PHONE + " \n 913 \t 8476  \n \t",
                expectedSearchCommand);
    }

}

package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.SearchCommand;
import tutorly.model.person.NameContainsKeywordsPredicate;
import tutorly.model.person.PhoneContainsKeywordsPredicate;
import tutorly.model.person.PredicateFilter;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(
                parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankKeywords_returnsSearchCommand() {
        PredicateFilter filter = new PredicateFilter(Collections.emptyList());
        SearchCommand expectedSearchCommand = new SearchCommand(filter);

        assertParseSuccess(
                parser,
                " " + PREFIX_NAME + " \t \n " + PREFIX_PHONE + " \n \t ",
                expectedSearchCommand);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        PredicateFilter filter = new PredicateFilter(Arrays.asList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new PhoneContainsKeywordsPredicate(Arrays.asList("913", "8476"))));
        SearchCommand expectedSearchCommand = new SearchCommand(filter);
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

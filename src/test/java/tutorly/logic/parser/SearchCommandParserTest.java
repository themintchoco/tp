package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.SearchCommand;
import tutorly.model.person.NameContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(
                parser,
                PREFIX_NAME + "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(
                parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedSearchCommand);
    }

}

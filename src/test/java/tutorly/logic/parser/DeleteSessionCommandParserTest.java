package tutorly.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.DeleteSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;

public class DeleteSessionCommandParserTest {

    private final DeleteSessionCommandParser parser = new DeleteSessionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteSessionCommand() throws Exception {
        DeleteSessionCommand command = parser.parse("1");
        assertEquals(new DeleteSessionCommand(1), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("a"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}

package tutorly.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.session.Session;

public class AddSessionCommandParserTest {

    private final AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_validInput_success() throws Exception {
        // Valid input
        String userInput = " " + PREFIX_DATE + "2025-03-25 " + PREFIX_SUBJECT + "Mathematics";

        // Expected session
        Session expectedSession = new Session(0, LocalDate.of(2025, 3, 25), "Mathematics");
        AddSessionCommand expectedCommand = new AddSessionCommand(expectedSession);

        // Parse and check
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        // Missing date prefix
        String userInput = " " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        // Missing subject prefix
        String userInput = " " + PREFIX_DATE + "2025-03-18";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        // Invalid date format
        String userInput = " " + PREFIX_DATE + "18-03-2025 " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        // Empty subject
        String userInput = " " + PREFIX_DATE + "2025-03-18 " + PREFIX_SUBJECT;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

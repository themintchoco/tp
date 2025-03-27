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
        String userInput = " " + PREFIX_DATE + "invalid-date " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser
                .parse(userInput), "Invalid date format. Please use YYYY-MM-DD.");
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2023-10-10 " + PREFIX_SUBJECT + "";
        assertThrows(ParseException.class, () -> parser.parse(userInput), "Subject cannot be empty.");
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        // Empty subject (no value after prefix)
        String userInput = " " + PREFIX_DATE + "2025-03-18 " + PREFIX_SUBJECT;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateDate_throwsParseException() {
        // Duplicate date prefix
        String userInput = " " + PREFIX_DATE
                + "2025-03-25 " + PREFIX_DATE + "2025-03-26 " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

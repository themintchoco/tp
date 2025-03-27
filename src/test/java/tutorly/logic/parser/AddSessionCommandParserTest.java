package tutorly.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.session.Session;

public class AddSessionCommandParserTest {

    private final AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_validInput_success() throws Exception {
        String userInput = " " + PREFIX_DATE + "2025-03-25 " + PREFIX_SUBJECT + "Mathematics";
        Session expectedSession = new Session(0, LocalDate.of(2025, 3, 25), "Mathematics");
        AddSessionCommand expectedCommand = new AddSessionCommand(expectedSession);
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        String userInput = " " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-18";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2023-10-10 " + PREFIX_SUBJECT + "";
        assertThrows(ParseException.class, () -> parser.parse(userInput), "Subject cannot be empty.");
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-18 " + PREFIX_SUBJECT;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateDate_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-25 " + PREFIX_DATE
                + "2025-03-26 " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-32 " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> parser
                .parse(userInput), "Invalid date format. Please use YYYY-MM-DD.");
    }

    @Test
    public void parse_invalidDateTimeParseException_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "not-a-date " + PREFIX_SUBJECT + "Mathematics";
        assertThrows(ParseException.class, () -> {
            try {
                parser.parse(userInput);
            } catch (DateTimeParseException e) {
                throw new ParseException("Invalid date format. Please use YYYY-MM-DD.");
            }
        });
    }

    @Test
    public void parse_illegalArgumentException_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-25 " + PREFIX_SUBJECT + " ";
        assertThrows(ParseException.class, () -> {
            try {
                parser.parse(userInput);
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }
        });
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        String invalidArgs = " d/2025/03/27 s/Mathematics"; // Wrong date format (slashes instead of dashes)

        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(invalidArgs));
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void parse_invalidDateValue_throwsParseException() {
        String invalidArgs = " d/2025-02-30 s/Mathematics"; // Invalid date (Feb 30 does not exist)

        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(invalidArgs));
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertEquals(expectedMessage, exception.getMessage()); // Adjust based on actual message from ParserUtil
    }

    @Test
    void parse_missingPrefixes_throwsParseException() {
        String invalidArgs = " 2025-03-27 Mathematics"; // Missing 'd/' and 's/' prefixes

        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(invalidArgs));
        assertTrue(exception.getMessage().contains("Invalid command format"));
    }

}

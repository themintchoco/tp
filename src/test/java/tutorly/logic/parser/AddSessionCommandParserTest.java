package tutorly.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}

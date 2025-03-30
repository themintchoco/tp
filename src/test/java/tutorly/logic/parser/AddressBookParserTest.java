package tutorly.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalIdentities.IDENTITY_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.commands.AddStudentCommand;
import tutorly.logic.commands.ClearCommand;
import tutorly.logic.commands.DeleteStudentCommand;
import tutorly.logic.commands.EditStudentCommand;
import tutorly.logic.commands.EditStudentCommand.EditPersonDescriptor;
import tutorly.logic.commands.EnrolSessionCommand;
import tutorly.logic.commands.ExitCommand;
import tutorly.logic.commands.HelpCommand;
import tutorly.logic.commands.ListStudentCommand;
import tutorly.logic.commands.RestoreStudentCommand;
import tutorly.logic.commands.SearchStudentCommand;
import tutorly.logic.commands.UnenrolSessionCommand;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.filter.AttendSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.filter.PhoneContainsKeywordsFilter;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.model.session.Timeslot;
import tutorly.testutil.EditPersonDescriptorBuilder;
import tutorly.testutil.PersonBuilder;
import tutorly.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddStudentCommand command = (AddStudentCommand) parser.parse(PersonUtil.getAddCommand(person));
        assertEquals(new AddStudentCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parse(ClearCommand.COMMAND_STRING) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_STRING + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteStudentCommand command = (DeleteStudentCommand) parser.parse(
                DeleteStudentCommand.COMMAND_STRING + " " + IDENTITY_FIRST_PERSON.getId());
        assertEquals(new DeleteStudentCommand(IDENTITY_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditStudentCommand command = (EditStudentCommand) parser.parse(EditStudentCommand.COMMAND_STRING + " "
                + IDENTITY_FIRST_PERSON.getId() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditStudentCommand(IDENTITY_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parse(ExitCommand.COMMAND_STRING) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_STRING + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        int sessionId = 1;
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchStudentCommand command = (SearchStudentCommand) parser.parse(
                SearchStudentCommand.COMMAND_STRING
                        + " " + PREFIX_SESSION + sessionId
                        + " " + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" "))
                        + " " + PREFIX_PHONE + keywords.stream().collect(Collectors.joining(" ")));
        Filter<Person> filter = Filter.any(Arrays.asList(
                new AttendSessionFilter(sessionId),
                new NameContainsKeywordsFilter(keywords),
                new PhoneContainsKeywordsFilter(keywords)));
        assertEquals(new SearchStudentCommand(filter), command);
    }

    // TODO: Uncomment when SearchSessionCommand is implemented with timeslot
    //    @Test
    //    public void parseCommand_searchSession() throws Exception {
    //        LocalDate date = LocalDate.of(2025, 1, 1);
    //        List<String> keywords = Arrays.asList("foo", "bar", "baz");
    //        SearchSessionCommand command = (SearchSessionCommand) parser.parse(
    //                SearchSessionCommand.COMMAND_STRING
    //                        + " " + PREFIX_DATE + date
    //                        + " " + PREFIX_SUBJECT + keywords.stream().collect(Collectors.joining(" ")));
    //        Filter<Session> filter = Filter.any(Arrays.asList(
    //                new DateSessionFilter(date),
    //                new SubjectContainsKeywordsFilter(keywords)));
    //        assertEquals(new SearchSessionCommand(filter), command);
    //    }

    @Test
    public void parseCommand_enrol() throws Exception {
        int id = 1;
        Identity identity = new Identity(id);
        EnrolSessionCommand command = (EnrolSessionCommand) parser.parse(
                EnrolSessionCommand.COMMAND_STRING + " " + id
                        + " " + PREFIX_SESSION + id
        );
        assertEquals(new EnrolSessionCommand(identity, id), command);
    }

    @Test
    public void parseCommand_unenrol() throws Exception {
        int id = 1;
        Identity identity = new Identity(id);
        UnenrolSessionCommand command = (UnenrolSessionCommand) parser.parse(
                UnenrolSessionCommand.COMMAND_STRING + " " + id
                        + " " + PREFIX_SESSION + id
        );
        assertEquals(new UnenrolSessionCommand(identity, id), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parse(HelpCommand.COMMAND_STRING) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_STRING + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parse(ListStudentCommand.COMMAND_STRING) instanceof ListStudentCommand);
        assertTrue(parser.parse(ListStudentCommand.COMMAND_STRING + " 3") instanceof ListStudentCommand);
    }

    @Test
    public void parseCommand_restore() throws Exception {
        assertTrue(parser.parse(RestoreStudentCommand.COMMAND_STRING + " 3") instanceof RestoreStudentCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parse(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parse("unknownCommand"));
    }

    @Test
    public void parseCommand_addSession_validInput() throws Exception {
        String timeslot = "25 Mar 2025 10:00-12:00";
        String subject = "Mathematics";
        Timeslot validTimeslot = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
                LocalDateTime.of(2025, 3, 25, 12, 0));
        Session expectedSession = new Session(validTimeslot, subject);

        AddSessionCommand expectedCommand = new AddSessionCommand(expectedSession);
        AddSessionCommand actualCommand = (AddSessionCommand) parser.parse(
                AddSessionCommand.COMMAND_STRING + " " + PREFIX_TIMESLOT + timeslot + " " + PREFIX_SUBJECT + subject);

        assertEquals(expectedCommand, actualCommand);
    }
}

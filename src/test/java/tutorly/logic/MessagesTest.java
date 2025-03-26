package tutorly.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import tutorly.logic.parser.Prefix;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;
import tutorly.model.session.Session;
import tutorly.model.tag.Tag;

/**
 * Test class for Messages.
 */
public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_validPrefixes_correctMessage() {
        Prefix p1 = new Prefix("n/");
        Prefix p2 = new Prefix("p/");
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/ p/";
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(p1, p2));
    }

    @Test
    public void format_person_correctFormatting() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        Email email = new Email("john@example.com");
        Address address = new Address("123 Street");
        Memo memo = new Memo("Important client");

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        tags.add(new Tag("student"));

        Person person = new Person(name, phone, email, address, tags, memo);

        String expectedFormat = "id: 0; Name: John Doe; Phone: 12345678; Email: john@example.com; Address: 123 Street; "
                + "Tags: [student, friend]; Memo: Important client";

        assertEquals(expectedFormat, Messages.format(person));
    }

    @Test
    public void format_session_correctFormatting() {
        Session session = new Session(1, LocalDate.of(2025, 3, 20), "Mathematics");

        String expectedFormat = "Date: 2025-03-20; Subject: Mathematics";
        assertEquals(expectedFormat, Messages.format(session));
    }
}

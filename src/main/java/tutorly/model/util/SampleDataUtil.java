package tutorly.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import tutorly.model.AddressBook;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.attendancerecord.Feedback;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;
import tutorly.model.session.Session;
import tutorly.model.session.Timeslot;
import tutorly.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("OLevels"), new Memo("Adept at calculus")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("ALevels", "Priority"), new Memo("Struggles with memorising chemical compounds")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("ALevels"), new Memo("Needs help with biology")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("PSLE"), new Memo("Enjoys solving geometry problems")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("ALevels"), new Memo("Interested in electronics and circuits")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("OLevels"), new Memo("Working on essay writing skills"))
        };
    }

    public static Session[] getSampleSessions() {
        return new Session[] {
            new Session(
                    new Timeslot(
                            LocalDateTime.of(2025, 2, 20, 11, 30),
                            LocalDateTime.of(2025, 2, 20, 13, 30)),
                    "Math"),
            new Session(
                    new Timeslot(
                            LocalDateTime.of(2025, 2, 21, 23, 0),
                            LocalDateTime.of(2025, 2, 22, 1, 0)),
                    "English"),
        };
    }

    public static AttendanceRecord[] getSampleAttendanceRecords() {
        return new AttendanceRecord[] {
            new AttendanceRecord(1, 1, true, Feedback.empty()),
            new AttendanceRecord(3, 1, false, Feedback.empty()),
            new AttendanceRecord(5, 1, true, Feedback.empty()),
            new AttendanceRecord(1, 2, false, Feedback.empty()),
            new AttendanceRecord(2, 2, true, Feedback.empty()),
            new AttendanceRecord(4, 2, false, Feedback.empty()),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Session sampleSession : getSampleSessions()) {
            sampleAb.addSession(sampleSession);
        }

        for (AttendanceRecord sampleRecord : getSampleAttendanceRecords()) {
            sampleAb.addAttendanceRecord(sampleRecord);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

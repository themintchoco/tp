package tutorly.testutil;

import static tutorly.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_MEMO_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_MEMO_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorly.model.AddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.model.session.Timeslot;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAddressBook {

    public static final Person ALICE = new PersonBuilder().withId(1)
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withId(2)
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withId(3)
            .withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withId(4)
            .withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withId(5)
            .withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withId(6)
            .withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withId(7)
            .withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withId(8)
            .withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withId(9)
            .withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withId(10)
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withMemo(VALID_MEMO_AMY).build();
    public static final Person BOB = new PersonBuilder().withId(11)
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withMemo(VALID_MEMO_BOB).build();

    public static final Timeslot MATH_TIMESLOT = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
            LocalDateTime.of(2025, 3, 25, 12, 0));
    public static final Timeslot MATH_TIMESLOT_OVERLAP = new Timeslot(LocalDateTime.of(2025, 3, 25, 11, 0),
            LocalDateTime.of(2025, 3, 25, 13, 0));
    public static final Timeslot ENGLISH_TIMESLOT = new Timeslot(LocalDateTime.of(2025, 3, 26, 8, 0),
            LocalDateTime.of(2025, 3, 26, 10, 0));
    public static final Session MATH_SESSION = new SessionBuilder().withId(1)
            .withSubject("Math").withTimeslot(MATH_TIMESLOT).build();
    public static final Session ENGLISH_SESSION = new SessionBuilder().withId(2)
            .withSubject("English").withTimeslot(ENGLISH_TIMESLOT).build();
    public static final Session MATH_SESSION_OVERLAP = new SessionBuilder().withId(3)
            .withSubject("Math").withTimeslot(MATH_TIMESLOT_OVERLAP).build();
    public static final AttendanceRecord ALICE_ATTEND_MATH = new AttendanceRecordBuilder()
            .withPerson(ALICE).withSession(MATH_SESSION).withIsPresent(true).build();
    public static final AttendanceRecord BENSON_ATTEND_MATH = new AttendanceRecordBuilder()
            .withPerson(BENSON).withSession(MATH_SESSION).withIsPresent(true).build();
    public static final AttendanceRecord DANIEL_NOT_ATTEND_MATH = new AttendanceRecordBuilder()
            .withPerson(DANIEL).withSession(MATH_SESSION).withIsPresent(false).build();
    public static final AttendanceRecord ELLE_NOT_ATTEND_MATH = new AttendanceRecordBuilder()
            .withPerson(ELLE).withSession(MATH_SESSION).withIsPresent(false).build();
    public static final AttendanceRecord ALICE_ATTEND_ENGLISH = new AttendanceRecordBuilder()
            .withPerson(ALICE).withSession(ENGLISH_SESSION).withIsPresent(true).build();
    public static final AttendanceRecord CARL_ATTEND_ENGLISH = new AttendanceRecordBuilder()
            .withPerson(CARL).withSession(ENGLISH_SESSION).withIsPresent(true).build();
    public static final AttendanceRecord DANIEL_NOT_ATTEND_ENGLISH = new AttendanceRecordBuilder()
            .withPerson(DANIEL).withSession(ENGLISH_SESSION).withIsPresent(false).build();
    public static final AttendanceRecord FIONA_NOT_ATTEND_ENGLISH = new AttendanceRecordBuilder()
            .withPerson(FIONA).withSession(ENGLISH_SESSION).withIsPresent(false).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalAddressBook() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        List<Person> persons = getTypicalPersons();
        List<Session> sessions = getTypicalSessions();
        List<AttendanceRecord> attendanceRecords = getTypicalAttendanceRecords();

        int nextPersonId = persons.stream().mapToInt(Person::getId).max().orElse(0) + 1;
        int nextSessionId = sessions.stream().mapToInt(Session::getId).max().orElse(0) + 1;

        AddressBook ab = new AddressBook(nextPersonId, nextSessionId);

        for (Person person : persons) {
            ab.addPerson(person);
        }

        for (Session session : sessions) {
            ab.addSession(session);
        }

        for (AttendanceRecord attendanceRecord : attendanceRecords) {
            ab.addAttendanceRecord(attendanceRecord);
        }

        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Session> getTypicalSessions() {
        return new ArrayList<>(Arrays.asList(MATH_SESSION, ENGLISH_SESSION));
    }

    public static List<AttendanceRecord> getTypicalAttendanceRecords() {
        return new ArrayList<>(Arrays.asList(ALICE_ATTEND_MATH, ALICE_ATTEND_ENGLISH, BENSON_ATTEND_MATH,
                CARL_ATTEND_ENGLISH, DANIEL_NOT_ATTEND_MATH, DANIEL_NOT_ATTEND_ENGLISH, ELLE_NOT_ATTEND_MATH,
                FIONA_NOT_ATTEND_ENGLISH));
    }
}

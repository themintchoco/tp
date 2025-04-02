package tutorly.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.AddressBook;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_SESSION = "Sessions list contains duplicate session(s).";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE_RECORD =
            "Attendance records list contains duplicate attendance record(s).";
    public static final String MESSAGE_ILLEGAL_NEXT_PERSON_ID = "Next person ID is not valid.";
    public static final String MESSAGE_ILLEGAL_NEXT_SESSION_ID = "Next session ID is not valid.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedAttendanceRecord> attendanceRecords = new ArrayList<>();

    private final int nextPersonId;
    private final int nextSessionId;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons, sessions, and attendance records.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("sessions") List<JsonAdaptedSession> sessions,
            @JsonProperty("attendanceRecords") List<JsonAdaptedAttendanceRecord> attendanceRecords,
            @JsonProperty("nextPersonId") int nextPersonId, @JsonProperty("nextSessionId") int nextSessionId) {
        this.persons.addAll(persons);
        this.sessions.addAll(sessions);
        this.attendanceRecords.addAll(attendanceRecords);
        this.nextPersonId = nextPersonId;
        this.nextSessionId = nextSessionId;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        sessions.addAll(source.getSessionList().stream().map(JsonAdaptedSession::new).collect(Collectors.toList()));
        attendanceRecords.addAll(source.getAttendanceRecordsList().stream()
                .map(JsonAdaptedAttendanceRecord::new).collect(Collectors.toList()));
        nextPersonId = source.getNextPersonId();
        nextSessionId = source.getNextSessionId();
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook(nextPersonId, nextSessionId);

        if (nextPersonId <= 0) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_NEXT_PERSON_ID);
        }

        if (nextSessionId <= 0) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_NEXT_SESSION_ID);
        }

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            if (person.getId() >= nextPersonId) {
                throw new IllegalValueException(MESSAGE_ILLEGAL_NEXT_PERSON_ID);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedSession jsonAdaptedSession : sessions) {
            Session session = jsonAdaptedSession.toModelType();
            if (addressBook.hasSession(session)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SESSION);
            }
            if (session.getId() >= nextSessionId) {
                throw new IllegalValueException(MESSAGE_ILLEGAL_NEXT_SESSION_ID);
            }
            addressBook.addSession(session);
        }

        for (JsonAdaptedAttendanceRecord jsonAdaptedAttendanceRecord : attendanceRecords) {
            AttendanceRecord attendanceRecord = jsonAdaptedAttendanceRecord.toModelType();
            if (addressBook.hasAttendanceRecord(attendanceRecord)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ATTENDANCE_RECORD);
            }
            addressBook.addAttendanceRecord(attendanceRecord);
        }

        return addressBook;
    }
}

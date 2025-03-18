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

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE_RECORD =
            "Attendance records list contains duplicate attendance record(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedAttendanceRecord> attendanceRecords = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and attendance records.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
        @JsonProperty("attendanceRecords") List<JsonAdaptedAttendanceRecord> attendanceRecords) {
        this.persons.addAll(persons);
        this.attendanceRecords.addAll(attendanceRecords);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        attendanceRecords.addAll(source.getAttendanceRecordsList().stream()
                .map(JsonAdaptedAttendanceRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
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

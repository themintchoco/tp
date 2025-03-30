package tutorly.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;
import tutorly.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String memo;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") int id, @JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("memo") String memo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.memo = memo;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        id = source.getId();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        memo = source.getMemo().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (id <= 0) {
            throw new IllegalValueException(Person.MESSAGE_INVALID_ID);
        }

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (name == null || name.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Phone modelPhone;
        if (phone == null || phone.isEmpty()) {
            modelPhone = Phone.empty();
        } else if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        } else {
            modelPhone = new Phone(phone);
        }

        final Email modelEmail;
        if (email == null || email.isEmpty()) {
            modelEmail = Email.empty();
        } else if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        } else {
            modelEmail = new Email(email);
        }

        final Address modelAddress;
        if (address == null || address.isEmpty()) {
            modelAddress = Address.empty();
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        final Memo modelMemo;
        if (memo == null || memo.isEmpty()) {
            modelMemo = Memo.empty();
        } else if (!Memo.isValidMemo(memo)) {
            throw new IllegalValueException(Memo.MESSAGE_CONSTRAINTS);
        } else {
            modelMemo = new Memo(memo);
        }

        Person person = new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelMemo);
        person.setId(id);

        return person;
    }

}

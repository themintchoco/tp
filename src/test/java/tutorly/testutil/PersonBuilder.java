package tutorly.testutil;

import java.util.HashSet;
import java.util.Set;

import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;
import tutorly.model.tag.Tag;
import tutorly.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private int id;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Memo memo;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        id = 0;
        name = new Name(DEFAULT_NAME);
        phone = Phone.empty();
        email = Email.empty();
        address = Address.empty();
        tags = new HashSet<>();
        memo = Memo.empty();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        id = personToCopy.getId();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        memo = personToCopy.getMemo();
    }

    /**
     * Sets the {@code id} of the {@code Person} that we are building.
     */
    public PersonBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building with an empty address.
     */
    public PersonBuilder withEmptyAddress() {
        this.address = Address.empty();
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building with an empty phone.
     */
    public PersonBuilder withEmptyPhone() {
        this.phone = Phone.empty();
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building with an empty email.
     */
    public PersonBuilder withEmptyEmail() {
        this.email = Email.empty();
        return this;
    }

    /**
     * Sets the {@code Memo} of the {@code Person} that we are building.
     */
    public PersonBuilder withMemo(String memo) {
        this.memo = new Memo(memo);
        return this;
    }

    /**
     * Sets the {@code Memo} of the {@code Person} that we are building with an empty memo.
     */
    public PersonBuilder withEmptyMemo() {
        this.memo = Memo.empty();
        return this;
    }

    /**
     * Builds the person.
     */
    public Person build() {
        Person person = new Person(name, phone, email, address, tags, memo);
        if (id > 0) {
            person.setId(id);
        }
        return person;
    }

}

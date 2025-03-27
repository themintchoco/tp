package tutorly.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label memo;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}.
     */
    public PersonCard(Person person) {
        super(FXML);
        this.person = person;
        id.setText(person.getId() + ". ");
        name.setText(person.getName().fullName);

        if (person.getPhone() == Phone.empty()) {
            phone.setVisible(false);
            phone.setManaged(false);
        } else {
            phone.setGraphic(Icons.getTelephoneIcon());
            phone.setText(person.getPhone().value);
        }

        if (person.getAddress() == Address.empty()) {
            address.setVisible(false);
            address.setManaged(false);
        } else {
            address.setGraphic(Icons.getHouseIcon());
            address.setText(person.getAddress().value);
        }

        if (person.getEmail() == Email.empty()) {
            email.setVisible(false);
            email.setManaged(false);
        } else {
            email.setGraphic(Icons.getEmailIcon());
            email.setText(person.getEmail().value);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person.getMemo() == Memo.empty()) {
            memo.setVisible(false);
            memo.setManaged(false);
        } else {
            memo.setGraphic(Icons.getMemoIcon());
            memo.setText(person.getMemo().value);
        }
    }
}

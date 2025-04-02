package tutorly.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
    private FlowPane tags;
    @FXML
    private VBox container;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}.
     */
    public PersonCard(Person person, boolean isSelected) {
        super(FXML);
        this.person = person;
        id.setText(person.getId() + ". ");
        name.setText(person.getName().fullName);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person.getPhone() != Phone.empty()) {
            container.getChildren().add(
                    new IconLabel(Icons.getTelephoneIcon(), person.getPhone().value, isSelected).getRoot());
        }

        if (person.getAddress() != Address.empty()) {
            container.getChildren().add(
                    new IconLabel(Icons.getHouseIcon(), person.getAddress().value, isSelected).getRoot());
        }

        if (person.getEmail() != Email.empty()) {
            container.getChildren().add(
                    new IconLabel(Icons.getEmailIcon(), person.getEmail().value, isSelected).getRoot());
        }

        if (person.getMemo() != Memo.empty()) {
            container.getChildren().add(
                    new IconLabel(Icons.getMemoIcon(), person.getMemo().value, isSelected).getRoot());
        }
    }
}

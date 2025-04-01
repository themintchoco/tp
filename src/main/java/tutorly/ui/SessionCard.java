package tutorly.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tutorly.model.session.Session;

/**
 * An UI component that displays information of a {@code Session}.
 */
public class SessionCard extends UiPart<Region> {

    private static final String FXML = "SessionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Session session;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label subject;
    @FXML
    private Label date;

    /**
     * Creates a {@code SessionCard} with the given {@code Session}.
     */
    public SessionCard(Session session) {
        super(FXML);
        this.session = session;
        id.setText(session.getId() + ". ");
        subject.setText(session.getSubject().subjectName);

        date.setGraphic(Icons.getCalendarIcon());
        date.setText(session.getTimeslot().getStartTime() + " - " + session.getTimeslot().getEndTime());
    }
}

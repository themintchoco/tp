package tutorly.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import tutorly.model.session.Session;

/**
 * Panel containing the list of sessions.
 */
public class SessionListPanel extends ListPanel<Session> {

    public SessionListPanel(ObservableList<Session> sessionList) {
        super(sessionList);
    }

    @Override
    protected UiPart<Region> getItemGraphic(Session session) {
        return new SessionCard(session);
    };

}

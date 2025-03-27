package tutorly.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of items.
 */
public abstract class ListPanel<T> extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";

    @FXML
    private ListView<T> listView;

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList}.
     */
    public ListPanel(ObservableList<T> observableList) {
        super(FXML);
        listView.setItems(observableList);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Returns the graphic for the given item.
     */
    protected abstract UiPart<Region> getItemGraphic(T item);

    /**
     * Custom {@code ListCell} that displays the graphics of the item.
     */
    class ListViewCell extends ListCell<T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(getItemGraphic(item).getRoot());
            }
        }
    }

}

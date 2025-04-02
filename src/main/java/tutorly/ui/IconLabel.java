package tutorly.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A UI component that displays an icon and a label.
 */
public class IconLabel extends UiPart<Region> {

    private static final String FXML = "IconLabel.fxml";

    @FXML
    private ImageView imageView;
    @FXML
    private Label label;

    /**
     * Creates an {@code IconLabel} with the given {@code iconPath} and {@code text}.
     */
    public IconLabel(Image image, String text, boolean shouldWrap) {
        super(FXML);

        imageView.setImage(image);
        label.setText(text);
        label.setWrapText(shouldWrap);
    }

}

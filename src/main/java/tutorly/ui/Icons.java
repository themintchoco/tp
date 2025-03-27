package tutorly.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Utility class for getting icons displayed.
 */
public class Icons {

    /**
     * Returns a 16x16 ImageView of the icon at the specified path.
     *
     * @param iconPath The path to the icon image.
     */
    public static ImageView getIcon(String iconPath) {
        ImageView img = new ImageView(new Image(Icons.class.getResourceAsStream(iconPath)));
        img.setFitHeight(16);
        img.setFitWidth(16);

        return img;
    }

    public static ImageView getTelephoneIcon() {
        return getIcon("/images/telephone.png");
    }

    public static ImageView getHouseIcon() {
        return getIcon("/images/house.png");
    }

    public static ImageView getEmailIcon() {
        return getIcon("/images/email.png");
    }

    public static ImageView getMemoIcon() {
        return getIcon("/images/memo.png");
    }

    public static ImageView getCalendarIcon() {
        return getIcon("/images/calendar.png");
    }
}

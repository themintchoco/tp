package tutorly.ui;

import javafx.scene.image.Image;

/**
 * Utility class for getting icons displayed.
 */
public class Icons {

    private static final String TELEPHONE_PATH = "/images/telephone.png";
    private static final String HOUSE_PATH = "/images/house.png";
    private static final String EMAIL_PATH = "/images/email.png";
    private static final String MEMO_PATH = "/images/memo.png";
    private static final String CALENDAR_PATH = "/images/calendar.png";

    /**
     * Returns the Image of the icon at the specified path.
     *
     * @param iconPath The path to the icon image.
     */
    public static Image getIcon(String iconPath) {
        return new Image(iconPath);
    }

    public static Image getTelephoneIcon() {
        return getIcon(TELEPHONE_PATH);
    }

    public static Image getHouseIcon() {
        return getIcon(HOUSE_PATH);
    }

    public static Image getEmailIcon() {
        return getIcon(EMAIL_PATH);
    }

    public static Image getMemoIcon() {
        return getIcon(MEMO_PATH);
    }

    public static Image getCalendarIcon() {
        return getIcon(CALENDAR_PATH);
    }
}

package tutorly.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for students */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MEMO = new Prefix("m/");

    /* Prefix definitions for sessions */
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIMESLOT = new Prefix("t/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("sub/");
    public static final Prefix PREFIX_SESSION = new Prefix("ses/");
    public static final Prefix PREFIX_FEEDBACK = new Prefix("f/");
}

package tutorly.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import tutorly.commons.core.index.Index;
import tutorly.commons.exceptions.IllegalValueException;
import tutorly.commons.util.StringUtil;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Identity;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Phone;
import tutorly.model.session.Subject;
import tutorly.model.session.Timeslot;
import tutorly.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_IDENTITY = "Identity provided is not a valid ID or name.";
    public static final String MESSAGE_INVALID_ID = "ID is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATETIME = "Invalid datetime provided.";
    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Invalid date format. Please ensure it uses 'dd MMM yyyy' (e.g. '25 Dec 2025').";
    public static final String MESSAGE_INVALID_TIMESLOT_FORMAT =
            "Invalid timeslot format. Please ensure it uses 'dd MMM yyyy HH:mm-HH:mm' "
            + "or 'dd MMM yyyy HH:mm-dd MMM yyyy HH:mm' (e.g. '25 Dec 2025 10:00-25 Dec 2025 12:00').";
    public static final String MESSAGE_EMPTY_SUBJECT = "Subject cannot be empty.";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final String MESSAGE_INVALID_SUBJECT =
            "Subjects should only contain letters and spaces, and it should not be blank";


    /**
     * Parses {@code String identity} into an {@code Identity} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * Multiple intermediate spaces will be collapsed into one space.
     *
     * @throws ParseException if the specified identity is invalid (not non-zero unsigned integer or valid name).
     */
    public static Identity parseIdentity(String identity) throws ParseException {
        requireNonNull(identity);
        String trimmedIdentity = identity.trim().replaceAll("\\s+", " ");
        if (StringUtil.isNonZeroUnsignedInteger(trimmedIdentity)) {
            return new Identity(Integer.parseInt(trimmedIdentity));
        }
        if (Name.isValidName(trimmedIdentity)) {
            return new Identity(new Name(trimmedIdentity));
        }
        throw new ParseException(MESSAGE_INVALID_IDENTITY);
    }

    /**
     * Parses {@code String id} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified id is invalid (not non-zero unsigned integer).
     */
    public static int parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedId)) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }
        return Integer.parseInt(trimmedId);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     * Multiple intermediate spaces will be collapsed into one space.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim().replaceAll("\\s+", " ");
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     * Multiple intermediate spaces will be collapsed into one space.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim().replaceAll("\\s+", " ");
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String memo} into an {@code Memo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code memo} is invalid.
     */
    public static Memo parseMemo(String memo) throws ParseException {
        requireNonNull(memo);
        String trimmedMemo = memo.trim();
        if (!Memo.isValidMemo(trimmedMemo)) {
            throw new ParseException(Memo.MESSAGE_CONSTRAINTS);
        }
        return new Memo(trimmedMemo);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * The date format must be YYYY-MM-DD.
     *
     * @param dateStr The date string to parse.
     * @return The parsed LocalDate.
     * @throws ParseException if the date format is invalid.
     */
    public static LocalDate parseDate(String dateStr) throws ParseException {
        requireNonNull(dateStr);
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Parses a {@code String timeslot} into a {@code Timeslot}.
     * The timeslot format must be dd MMM yyyy HH:mm-HH:mm or dd MMM yyyy HH:mm-dd MMM yyyy HH:mm.
     *
     * @param timeslot The timeslot to parse.
     * @return The parsed Timeslot.
     * @throws ParseException if the date format is invalid.
     */
    public static Timeslot parseTimeslot(String timeslot) throws ParseException {
        requireNonNull(timeslot);

        // Split the timeslot into start and end times based on the first hyphen
        String[] tokens = timeslot.trim().split("-");
        if (tokens.length != 2) {
            throw new ParseException(MESSAGE_INVALID_TIMESLOT_FORMAT);
        }
        String startDateTimeStr = tokens[0].trim();
        String endDateTimeStr = tokens[1].trim();

        // Process start datetime
        String[] startTokens = startDateTimeStr.split("\\s+");
        if (startTokens.length != 4) {
            throw new ParseException(MESSAGE_INVALID_TIMESLOT_FORMAT);
        }
        String startDateStr = startTokens[0] + " " + startTokens[1] + " " + startTokens[2];
        String startTimeStr = startTokens[3];

        // Process end datetime
        String[] endTokens = endDateTimeStr.split("\\s+");
        String endDateStr;
        String endTimeStr;
        if (endTokens.length == 1) {
            endDateStr = startDateStr;
            endTimeStr = endTokens[0];
        } else if (endTokens.length == 4) {
            endDateStr = endTokens[0] + " " + endTokens[1] + " " + endTokens[2];
            endTimeStr = endTokens[3];
        } else {
            throw new ParseException(MESSAGE_INVALID_TIMESLOT_FORMAT);
        }

        LocalDate startDate;
        LocalDate endDate;
        LocalTime startTime;
        LocalTime endTime;
        try {
            startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
            startTime = LocalTime.parse(startTimeStr, TIME_FORMATTER);
            endTime = LocalTime.parse(endTimeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_TIMESLOT_FORMAT);
        }

        // Combine date and time into LocalDateTime objects
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        if (startDateTime.isAfter(endDateTime)) {
            throw new ParseException(Timeslot.MESSAGE_END_BEFORE_START_DATETIME);
        }

        return new Timeslot(startDateTime, endDateTime);
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws IllegalValueException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }
}

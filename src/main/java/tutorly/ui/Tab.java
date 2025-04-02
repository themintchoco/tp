package tutorly.ui;

import java.util.Optional;

import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * Represents the tabs.
 */
public class Tab {

    public static final int TAB_ID_STUDENT = 0;
    public static final int TAB_ID_SESSION = 1;

    private final int tabId;
    private final Optional<Person> targetPerson;
    private final Optional<Session> targetSession;
    private final Optional<AttendanceRecord> targetRecord;

    private Tab(int tabId, Person targetPerson, Session targetSession, AttendanceRecord targetRecord) {
        this.tabId = tabId;
        this.targetPerson = Optional.ofNullable(targetPerson);
        this.targetSession = Optional.ofNullable(targetSession);
        this.targetRecord = Optional.ofNullable(targetRecord);
    }

    public static Tab student() {
        return new Tab(TAB_ID_STUDENT, null, null, null);
    }

    public static Tab student(Person target) {
        return new Tab(TAB_ID_STUDENT, target, null, null);
    }

    public static Tab session() {
        return new Tab(TAB_ID_SESSION, null, null, null);
    }

    public static Tab session(Session target) {
        return new Tab(TAB_ID_SESSION, null, target, null);
    }

    public static Tab attendanceRecord(Session session, AttendanceRecord target) {
        return new Tab(TAB_ID_SESSION, null, session, target);
    }

    public int getTabId() {
        return tabId;
    }

    public Optional<Person> getTargetPerson() {
        return targetPerson;
    }

    public Optional<Session> getTargetSession() {
        return targetSession;
    }

    public Optional<AttendanceRecord> getTargetRecord() {
        return targetRecord;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tab)) {
            return false;
        }

        Tab otherTab = (Tab) other;
        return tabId == otherTab.tabId
                && targetPerson.equals(otherTab.targetPerson)
                && targetSession.equals(otherTab.targetSession)
                && targetRecord.equals(otherTab.targetRecord);
    }

}


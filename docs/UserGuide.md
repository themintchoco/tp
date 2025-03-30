---
layout: page
title: User Guide
---

Tutorly is a **desktop app designed to manage your student records efficiently**. It combines the speed and precision of typing commands with the convenience of a visual interface. If you prefer using your keyboard over clicking through menus, Tutorly allows you to complete student management tasks more quickly than traditional apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. **Install Java**: Ensure you have Java `17` or above installed in your Computer. You can download the correct JDK for your operating system [here](https://www.oracle.com/sg/java/technologies/downloads/).<br>
   **Mac users**: Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. **Download Tutorly**: Get the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T17-3/tp/releases/latest/download/tutorly.jar).

3. **Move the file**: Locate the downloaded `tutorly.jar` file and place it in a folder where you want to keep the app's data. This will be the app's _home folder_.

4. **Open a command terminal** - This is a tool that lets you run commands on your computer:
   - On **Windows**: Press `Win + R`, type `cmd`, and press **Enter**.
   - On **Mac**: Open **Terminal** from the Applications > Utilities folder.
   - On **Linux**: Open **Terminal** from your applications menu.

5. **Navigate to the folder** - In the terminal, type `cd ` (with a space after). Then, **click and hold** the _home folder_, **drag** it into the terminal window and **release** it. Doing this will automatically insert the full path of the folder. Press **Enter**.

6. **Run Tutorly** - Type `java -jar tutorly.jar` and press **Enter**. This will start the application. A GUI similar to the below should appear in a few seconds, containing some sample data.<br>
   ![Ui](images/Ui.png)

7. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `student list` : Lists all students.

   * `student add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to the app.

   * `student delete 3` : Deletes the student with the ID 3.

   * `session list` : List all sessions.

   * `clear` : Deletes all students and sessions.

   * `exit` : Exits the app.

8. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `student add n/NAME`, `NAME` is a parameter which can be used as `student add n/John Doe`.

* `STUDENT_IDENTIFIER` can either be the target student's ID, or their full name. Examples: `John Doe` or `2`.

* Parameters in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extra parameters for `general` commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### General Commands

#### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

#### Clearing all entries: `clear`

Clears all students and sessions from the app.

Format: `clear`

#### Exiting the program: `exit`

Closes the Tutorly app.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

### Viewing tabs

#### Viewing students tab: `student`

Shows the student tab in the main window.

Format: `student`

#### Viewing sessions tab: `session`

Shows the session tab in the main window.

Format: `session`

#### Viewing attendance for a session: `session ID`

Shows the attendance of students for a given session.

Format: `session SESSION_ID`

Examples:
* `session 1`
* `session 5`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The main window will automatically switch to the tab which shows the results of the command that has been executed.
</div>

--------------------------------------------------------------------------------------------------------------------

### Student Management: `student ACTION`

The following commands all begin with `student` followed by an action word.

#### Adding a student: `add`

Adds a student to the app.

Format: `student add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [m/MEMO] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of tags (including 0)
</div>

Examples:
* `student add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 m/loves Math`
* `student add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

#### Listing all students: `list`

Shows a list of all students.

Format: `student list`

#### Editing a student: `edit`

Edits an existing student.

Format: `student edit STUDENT_IDENTIFIER [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [m/MEMO] [t/TAG]…​`

* Edits the student with the specified `STUDENT_IDENTIFIER`. `STUDENT_IDENTIFIER` can be either the student's ID or full name.
* At least one of the optional parameters must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Examples:
*  `student edit John Doe p/91234567 e/johndoe@example.com` Edits the phone number and email address of John Doe to be `91234567` and `johndoe@example.com` respectively.
*  `student edit 2 n/Betsy Crower t/` Edits the name of the student with an ID of 2 to be `Betsy Crower` and clears all existing tags.

#### Searching for students: `search`

Finds students whose names or phone numbers contain any of the given keywords, or is enrolled to a specific session.

Format: `student search [ses/SESSION_ID] [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Incomplete words will still be matched e.g. `Han` will match `Hans`
* Students matching at least one keyword or are enrolled to the session will be returned.
  e.g. `n/Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `student search n/John p/9123 8765` returns `johnathan`, `John Doe` and other students with a phone number that contains `9123` or `8765`.
* `student search ses/3 n/alex david` returns `Alex Yeoh`, `David Li` and other students who attended session with the id 3.<br>
  ![result for 'search alex david'](images/searchAlexDavidResult.png)

#### Deleting a student: `delete`

Deletes the student with the specified `STUDENT_IDENTIFIER`. `STUDENT_IDENTIFIER` can be either the student's ID or full name.

Format: `student delete STUDENT_IDENTIFIER`

Examples:
* `student delete 2` deletes the student with the ID of 2.
* `student delete John Doe` deletes the student with the name `John Doe`.

#### Restoring a deleted student: `restore`

Undoes a deletion of a student.

Format: `student restore STUDENT_IDENTIFIER`

* Restores the student with the specified `STUDENT_IDENTIFIER`. `STUDENT_IDENTIFIER` can be either the student's ID or full name.
* This can only restore a student that has been deleted if the app has not been closed since deletion.

Examples:
* `student delete 1` followed by `student restore 1` before the app is closed will restore the deleted student.  

--------------------------------------------------------------------------------------------------------------------

### Session Management: `session ACTION`

The following commands all begin with `session` followed by an action word.

#### Adding a session: `add`

Adds a session to the app.

Format: `session add d/DATE sub/SUBJECT`

Examples:
* `session add d/2025-03-18 sub/Mathematics`

#### Listing all sessions: `list`

Shows a list of all sessions.

Format: `session list`

#### Editing a session: `edit`

Edits an existing session.

Format: `session edit SESSION_ID [d/DATE] [sub/SUBJECT]`
* Edits the session with the `SESSION_ID`.
* At least one of the optional parameters must be provided.
* Existing values will be updated to the input values.

Examples:
*  `session edit 3 d/2025-04-11` Edits the date of the session with the ID 3 to be on `11 April 2025`.
*  `session edit 2 d/2025-06-20 sub/Math` Edits the date and subject of the session with the ID 2 to be on `20 June 2025` with the subject `Math`.

#### Searching for sessions: `search`

Finds sessions on a particular date or on a subject which matches any of the given keywords.

Format: `session search [d/DATE] [sub/SUBJECT_KEYWORDS]`

* The keyword search is case-insensitive. e.g `math` will match `Math`
* The order of the keywords does not matter. e.g. `Math Eng` will match `Eng Math`
* Incomplete words will still be matched e.g. `Mat` will match `Math`
* Sessions that fall on the given date or have a subject that match at least one keyword will be returned.
  e.g. `sub/Mat Eng` will return sessions with subjects `Math`, `English`

Examples:
* `student search d/2025-05-22` returns sessions that fall on 22 May 2025.
* `student search sub/Math d/2025-06-11` returns sessions with subjects `Math`, `Mathematics` and sessions that fall on 11 June 2025.<br>
* `student search` will simply return all sessions.

#### Deleting a session: `delete`

Deletes the session with the given ID.

Format: `session delete SESSION_ID`

Examples:
* `session delete 2` deletes the session with the ID of 2.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Unlike student delete, there is no restore command for session delete in the current version of Tutorly, i.e. this command is irreversible.
A restore command for sessions will be added in future versions.
</div>

#### Enrolling a student to a session: `enrol`

Enrols a student to a session.

Format: `session enrol STUDENT_IDENTIFIER ses/SESSION_ID`

* Enrols the student with the specified `STUDENT_IDENTIFIER` to the session. `STUDENT_IDENTIFIER` can be either the student's ID or full name.
* The attendance for the student to the session upon enrolment is marked as absent by default.

Examples:
* `session enrol 2 ses/3` enrols a student with an ID of 2 to attend a session with an ID of 3.
* `session enrol John Doe ses/4` enrols a student with the name `John Doe` to attend a session with an ID of 4.

#### Unenrolling a student from a session: `unenrol`

Unenrols a student with the specified `STUDENT_IDENTIFIER` from a session. `STUDENT_IDENTIFIER` can be either the student's ID or full name.

Format: `session unenrol STUDENT_IDENTIFIER ses/SESSION_ID`

Examples:
* `session unenrol 2 ses/3` unenrols a student with an ID of 2 from a session with an ID of 3.
* `session unenrol John Doe ses/4` unenrols a student with the name `John Doe` from a session with an ID of 4.

#### Marking attendance as present: `mark`

Marks the attendance of a student for a session as present.

Format: `session mark STUDENT_IDENTIFIER ses/SESSION_ID`

* Marks the attendance for the student with the specified `STUDENT_IDENTIFIER` as present. `STUDENT_IDENTIFIER` can be either the student's ID or full name.

Examples:
* `session mark 2 ses/3` marks the attendance for the student with an ID of 2 for a session with an ID of 3 as present.
* `session mark John Doe ses/4` marks the attendance for a student with the name `John Doe` for a session with an ID of 4 as present.

#### Marking attendance as absent: `unmark`

Marks the attendance of a student for a session as absent.

Format: `session unmark STUDENT_IDENTIFIER ses/SESSION_ID`

* Marks the attendance for the student with the specified `STUDENT_IDENTIFIER` as absent. `STUDENT_IDENTIFIER` can be either the student's ID or full name.

Examples:
* `session unmark 2 ses/3` marks the attendance for the student with an ID of 2 for a session with an ID of 3 as absent.
* `session mark John Doe ses/4` marks the attendance for a student with the name `John Doe` for a session with an ID of 4 as absent.

--------------------------------------------------------------------------------------------------------------------

### Saving the data

Tutorly data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Tutorly data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Context | Action           | Format                                                                                        | Examples                                   |
|---------|------------------|-----------------------------------------------------------------------------------------------|--------------------------------------------|
| General | Help             | `help`                                                                                        | -                                          |
|         | Clear            | `clear`                                                                                       | -                                          |
|         | Exit             | `exit`                                                                                        | -                                          |
| Tab     | Show student tab | `student`                                                                                     | -                                          |
|         | Show session tab | `session`                                                                                     | -                                          |
|         | Show attendance  | `session ID`                                                                                  | `session 4`                                |
| Student | Add              | `student add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [m/MEMO] [t/TAG]…​`                | `student add n/John Doe p/98765432`        |
|         | List             | `student list`                                                                                | -                                          |
|         | Edit             | `student edit STUDENT_IDENTIFIER [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [m/MEMO] [t/TAG]…​` | `student edit 2 n/James Lee p/91234567`    |
|         | Search           | `student search [ses/SESSION_ID] [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS]`                        | `student search n/alex dav p/9123 8765`    |
|         | Delete           | `student delete STUDENT_IDENTIFIER`                                                           | `student delete 3`                         |
|         | Restore          | `student restore STUDENT_IDENTIFIER`                                                          | `student restore 3`                        |
| Session | Add              | `session add d/DATE sub/SUBJECT`                                                              | `session add d/2025-04-15 sub/Math`        |
|         | List             | `session list`                                                                                | -                                          |
|         | Edit             | `session edit SESSION_ID [d/DATE] [sub/SUBJECT_KEYWORDS]`                                     | `session edit 2 d/2025-04-11 sub/Math`     |
|         | Search           | `session search [d/DATE] [sub/SUBJECT_KEYWORDS]`                                              | `session search d/2025-04-15 sub/Math Eng` |
|         | Delete           | `session delete SESSION_ID`                                                                   | `session delete 1`                         |
|         | Enrol student    | `session enrol STUDENT_IDENTIFIER ses/SESSION_ID `                                            | `session enrol 4 ses/3 `                   |
|         | Unenrol student  | `session unenrol STUDENT_IDENTIFIER ses/SESSION_ID `                                          | `session unenrol 4 ses/3 `                 |
|         | Mark Present     | `session mark STUDENT_IDENTIFIER ses/SESSION_ID`                                              | `session mark John Doe ses/2`              |
|         | Mark Absent      | `session unmark STUDENT_IDENTIFIER ses/SESSION_ID`                                            | `session unmark 3 ses/2`                   |

--------------------------------------------------------------------------------------------------------------------

## Glossary

| Term               | Definition                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Command Terminal   | A tool on a computer where you type instructions to make the computer perform tasks.                                                                                          |
| JAR                | Java Archive file: A package that bundles a Java program and all its necessary parts into a single file, making it easier to share and run.                                   |
| JDK                | Java Development Kit: A software package that provides everything needed to create and run Java programs.                                                                     |
| Operating System   | An operating system is the main software that manages a computer’s hardware and allows you to run applications. Some examples include `Windows`, `Mac` and `Linux`.           |
| Parameters         | These are placeholders in a command that users replace with specific information to customize the command's action. They are usually prefixed with letters like `n/` or `p/`. |
| STUDENT_IDENTIFIER | A parameter used to identify a student. It can either be the student's ID, or their full name.                                                                                |

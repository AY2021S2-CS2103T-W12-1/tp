package seedu.address.logic.commands.medical;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medical.MedicalRecord;
import seedu.address.model.medical.Section;
import seedu.address.model.person.Patient;
import seedu.address.testutil.PersonBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ViewMedicalRecordCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        Patient newAlice = new PersonBuilder(ALICE).build();
        Patient newBenson = new PersonBuilder(BENSON).build();
        Patient newCarl = new PersonBuilder(CARL).build();
        Patient newDaniel = new PersonBuilder(DANIEL).build();
        Patient newElle = new PersonBuilder(ELLE).build();
        Patient newFiona = new PersonBuilder(FIONA).build();
        Patient newGeorge = new PersonBuilder(GEORGE).build();
        Patient newHoon = new PersonBuilder(HOON).build();
        Patient newIda = new PersonBuilder(IDA).build();
        model.addPerson(newAlice);
        model.addPerson(newBenson);
        model.addPerson(newCarl);
        model.addPerson(newDaniel);
        model.addPerson(newElle);
        model.addPerson(newFiona);
        model.addPerson(newGeorge);
        model.addPerson(newHoon);
        model.addPerson(newIda);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidMedicalRecordIndexUnfilteredList_failure() {
        Patient patientToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LocalDateTime dateTime = LocalDateTime.of(2021, 12, 12, 18, 00);
        patientToView.addMedicalRecord(new MedicalRecord(dateTime, null));

        model.selectPatient(patientToView);
        Index outOfBoundIndex = Index.fromOneBased(patientToView.getRecords().size() + 1);
        ViewMedicalRecordCommand viewMedicalRecordCommand = new ViewMedicalRecordCommand(outOfBoundIndex);

        assertCommandFailure(viewMedicalRecordCommand, model, Messages.MESSAGE_INVALID_MEDICAL_RECORD_INDEX);
    }
    @Test
    public void execute_validMedicalRecordIndexFilteredList_success() {
        Patient patientToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LocalDateTime dateTime = LocalDateTime.of(2021, 12, 12, 18, 00);
        Section section = new Section("test");
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        patientToView.addMedicalRecord(new MedicalRecord(dateTime, sections));

        model.selectPatient(patientToView);
        ViewMedicalRecordCommand viewMedicalRecordCommand = new ViewMedicalRecordCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(ViewMedicalRecordCommand.MESSAGE_SUCCESS,
                patientToView.getName().fullName);
        assertCommandSuccess(viewMedicalRecordCommand, model, expectedMessage, expectedModel);
    }


}


package de.praxis.view;

import de.praxis.model.Patient;
import java.sql.SQLException;
import java.util.List;

import de.praxis.service.PatientPdfService;
import de.praxis.service.PatientService;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;


public class PatientView extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Patient Verwaltung");
        Font.loadFont(
                getClass().getResourceAsStream(
                        "/de/praxis/fonts/Oswald-Regular.ttf"
                ),
                16
        );

        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_CENTER);
        root.setHgap(20);
        root.setVgap(20);
        root.setPadding(new Insets(15));

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(70);
        col2.setPercentWidth(30);
        root.getColumnConstraints().addAll(col1, col2);

        // Erste Zeile (Header)
        HBox patientverwaltung = new HBox(30);
        patientverwaltung.setPadding(new Insets(20));
        Image imagePatientVerwaltung = new Image(getClass().getResource("/de/praxis/images/DashBoarddIcon.png").toExternalForm());
        ImageView imageViewPatientVerwaltung = new ImageView(imagePatientVerwaltung);
        imageViewPatientVerwaltung.setFitWidth(50);
        imageViewPatientVerwaltung.setFitHeight(50);
        imageViewPatientVerwaltung.setPreserveRatio(true);

        Label patientVerwaltungLabel = new Label("Patient-Verwaltung");
        patientVerwaltungLabel.setStyle("-fx-text-fill: white;");
        patientverwaltung.getChildren().addAll(imageViewPatientVerwaltung, patientVerwaltungLabel);

        // Zweite Zeile (TextField mit TableView)
        VBox tableviewBox = new VBox(10);
        TableView<Patient> patientTable = new TableView<>();
        patientTable.setMaxWidth(Double.MAX_VALUE);
        patientTable.setPrefHeight(500);
        TableColumn<Patient, Number> idColumn = new TableColumn<>("ID");
        TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Patient, Number> alterColumn = new TableColumn<>("Alter");
        TableColumn<Patient, String> geschlechtColumn = new TableColumn<>("Geschlecht");
        TableColumn<Patient, String> abteilungColumn = new TableColumn<>("Abteilung");
        TableColumn<Patient, String> telefonColumn = new TableColumn<>("Telefon");
        TableColumn<Patient, String> emailColumn = new TableColumn<>("E-Mail");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        alterColumn.setCellValueFactory(new PropertyValueFactory<>("patient_alter"));
        geschlechtColumn.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));
        abteilungColumn.setCellValueFactory(new PropertyValueFactory<>("abteilung"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefonnummer"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        patientTable.getColumns().addAll(idColumn,nameColumn,alterColumn,geschlechtColumn,
                abteilungColumn,telefonColumn,emailColumn);

        PatientService patientService=new PatientService();
//        List<Patient> patients=patientService.getPatienten();
//        patientTable.getItems().addAll(patients);
        ObservableList<Patient> patientdata = FXCollections.observableArrayList(patientService.getPatienten());
        FilteredList<Patient> filteredList=new FilteredList<>(patientdata,patient -> true);
        patientTable.setItems(filteredList);


        //Suchen TExtField
        TextField serachTextField = new TextField();
        serachTextField.setPromptText("Patient suchen...");
        serachTextField.setMaxWidth(Double.MAX_VALUE);
        serachTextField.setPrefWidth(420);

        serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(patient -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String search = newValue.toLowerCase();

                return patient.getName().toLowerCase().contains(search)
                        || patient.getGeschlecht().toLowerCase().contains(search)
                        || patient.getAbteilung().toLowerCase().contains(search)
                        || patient.getTelefonnummer().toLowerCase().contains(search)
                        || patient.getEmail().toLowerCase().contains(search);
            });
        });


        tableviewBox.getChildren().addAll(serachTextField, patientTable);

        //buttons (Add, Update, Delete)
        VBox patientenBtn = new VBox(10);
        patientenBtn.getStyleClass().add("patienten-Btn");
        Button reportBtn = new Button("⎙ Patienten PDF\n");
        Button aktionsbtn = new Button("Patient Aktions \uD83D\uDD3D\u200B");
        patientenBtn.getChildren().addAll(reportBtn, aktionsbtn);
        reportBtn.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Patientenbericht speichern");

            fileChooser.setInitialFileName("Patientenbericht.pdf");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF Dateien (*.pdf)",
                            "*.pdf"
                    )
            );

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {

                try {

                    List<Patient> patients = patientService.getPatienten();

                    PatientPdfService.createPatientReport(
                            patients,
                            file.getAbsolutePath()
                    );

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erfolgreich");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Der Patientenbericht wurde erfolgreich erstellt."
                    );
                    alert.showAndWait();

                } catch (SQLException e) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Patientendaten konnten nicht geladen werden."
                    );
                    alert.showAndWait();

                }
            }
        });

        // Form for adding/updating patients
        VBox addPatientForm = new VBox(10);
        TextField nametxt = new TextField();
        ComboBox<Integer> patient_altertxt = new ComboBox<>();
        TextField geschlechttxt = new TextField();
        ComboBox<String> abteilungtxt = new ComboBox<>();
        TextField telefonnummer=new TextField();
        TextField email=new TextField();

        HBox patientActionsbtn = new HBox(5);
        Button patientAddbtn = new Button("Hinzufügen");
        Button patientUpdateBtn = new Button("Änderung");
        patientTable.setStyle("-fx-font-size: 16px;");
        patientActionsbtn.getChildren().addAll(patientAddbtn, patientUpdateBtn);
        Patient[] selectedPatient = new Patient[1];
        patientTable.setOnMouseClicked(mouseEvent -> {
            Patient selectedPateint = patientTable.getSelectionModel().getSelectedItem();
            if(selectedPateint !=null ){
                selectedPatient[0]=selectedPateint;
                nametxt.setText(selectedPateint.getName());
                patient_altertxt.setValue(selectedPateint.getPatient_alter());
                geschlechttxt.setText(selectedPateint.getGeschlecht());
                abteilungtxt.setValue(selectedPateint.getAbteilung());
                telefonnummer.setText(selectedPateint.getTelefonnummer());
                email.setText(selectedPateint.getEmail());
//                System.out.println("Patient_Id"+selectedPateint.getPatient_id());
                addPatientForm.setVisible(true);


            }
        });

//        PatientService patientService=new PatientService();
        patientAddbtn.setOnAction(event -> {
            if(nametxt.getText().isEmpty() || patient_altertxt.getValue()==null
            || geschlechttxt.getText().isEmpty() ||  abteilungtxt.getValue()==null
                    || telefonnummer.getText().isEmpty()|| email.getText().isEmpty()) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehlende Angaben");
                alert.setHeaderText(null);
                alert.setContentText("Bitte alle Felder ausfüllen");
                alert.showAndWait();
                return;
            }
            Patient patient=new Patient(
                    0,
                    nametxt.getText(),
                    patient_altertxt.getValue(),
                    geschlechttxt.getText(),
                    abteilungtxt.getValue(),
                    telefonnummer.getText(),
                    email.getText()
            );

        try {
            patientService.patientHinzufuegen(patient);
            loadPatients(patientdata, patientService);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Patient wurde erfolgreich hinzugefügt.");
            alert.showAndWait();
        }catch (SQLException ausnahme){
            ausnahme.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Patient konnte nicht gespeichert werden.");
            alert.showAndWait();
        }
        });
        patientUpdateBtn.setOnAction(event -> {
            if(selectedPatient[0] == null){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Bitte zuerst einen Patienten auswählen.");
                alert.showAndWait();
                return;
            }
            if(nametxt.getText().isEmpty() || patient_altertxt.getValue()==null
                    || geschlechttxt.getText().isEmpty() ||  abteilungtxt.getValue()==null
                    || telefonnummer.getText().isEmpty()|| email.getText().isEmpty()) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehlende Angaben");
                alert.setHeaderText(null);
                alert.setContentText("Bitte alle Felder ausfüllen");
                alert.showAndWait();
                return;
            }
            Patient patient=selectedPatient[0];
            patient.setName(nametxt.getText());
            patient.setPatient_alter(patient_altertxt.getValue());
            patient.setGeschlecht(geschlechttxt.getText());
            patient.setAbteilung(abteilungtxt.getValue());
            patient.setTelefonnummer(telefonnummer.getText());
            patient.setEmail(email.getText());

            try {
                patientService.updatePatient(patient);
                loadPatients(patientdata, patientService);
                patientTable.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Patient wurde erfolgreich geändert.");
            alert.showAndWait();


        });
        nametxt.setPromptText("der Name");
        patient_altertxt.setPromptText("Alter Auswahlen");
        patient_altertxt.setStyle("-fx-font-size: 16px;");
        for (int i = 1; i <100 ; i++) {
            patient_altertxt.getItems().add(i);
        }
        geschlechttxt.setPromptText("Geschlecht");
        telefonnummer.setPromptText("Geben Sie bitte Telefonnummer ein");
        email.setPromptText("Geben Sie bitte E-mail ein");
        abteilungtxt.getItems().addAll(
                "Allgemeinmedizin",
                "Innere Medizin",
                "Kardiologie",
                "Dermatologie",
                "Orthopädie",
                "Neurologie",
                "Gynäkologie",
                "Urologie",
                "HNO",
                "Augenheilkunde",
                "Zahnmedizin",
                "Pädiatrie",
                "Radiologie",
                "Chirurgie"
        );
        abteilungtxt.setPromptText("Spezialiserung auswählen");
        abteilungtxt.setStyle("-fx-font-size: 16px;");
//        patient_artztxt.setPromptText("Der Arzt von Patient");

        addPatientForm.getChildren().addAll(
                nametxt,
                patient_altertxt,
                geschlechttxt,
                abteilungtxt,
                telefonnummer,
                email,
//                patient_artztxt,
                patientActionsbtn
        );
        VBox deleteBereich=new VBox(5);
        Label deleteLabel=new Label("Wählen Sie den Datensatz aus, den Sie löschen möchten.");
        deleteLabel.setStyle("-fx-font-size: 12px;");
        Button deleteBtn =new Button("Patient Löschen");
        deleteBereich.getChildren().addAll(deleteBtn,deleteLabel);
        deleteBtn.setOnAction(event -> {
            try {
                patientService.deletePatient(selectedPatient[0].getPatient_id());
                loadPatients(patientdata, patientService);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erfolgreich");
                alert.setHeaderText(null);
                alert.setContentText("Patient: "+selectedPatient[0].getName()+" wurde erfolgreich gelöscht.");
                alert.showAndWait();
                patientTable.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });




        VBox rightSideContainer = new VBox(15);
        rightSideContainer.getChildren().addAll(patientenBtn, addPatientForm);
        rightSideContainer.setMaxWidth(Double.MAX_VALUE);
        addPatientForm.setVisible(false);
//        aktionsbtn.setOnAction(event -> addPatientForm.setVisible(true));
        aktionsbtn.setOnAction(event ->
                addPatientForm.setVisible(!addPatientForm.isVisible())
        );


        root.add(patientverwaltung, 0, 0);
        GridPane.setColumnSpan(patientverwaltung, 2);

        root.add(tableviewBox, 0, 1);
        root.add(rightSideContainer, 1, 1);
        root.add(deleteBereich,1,2);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void loadPatients(ObservableList<Patient> patientData, PatientService patientService) {
        try {

            patientData.setAll(patientService.getPatienten());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
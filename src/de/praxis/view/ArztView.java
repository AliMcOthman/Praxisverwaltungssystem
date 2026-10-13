package de.praxis.view;

import de.praxis.model.Arzt;
import de.praxis.model.Patient;
import de.praxis.model.Termin;
import de.praxis.service.ArztPdfService;
import de.praxis.service.ArztService;
import de.praxis.service.PatientPdfService;
import de.praxis.service.PatientService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class ArztView extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Arzt Verwaltung");
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

        // Erste Zeile
        HBox arztverwaltung = new HBox(30);
        arztverwaltung.setPadding(new Insets(20));
        Image imagearztVerwaltung = new Image(getClass().getResource("/de/praxis/images/DashBoarddIcon.png").toExternalForm());
        ImageView imageViewarztVerwaltung = new ImageView(imagearztVerwaltung);
        imageViewarztVerwaltung.setFitWidth(50);
        imageViewarztVerwaltung.setFitHeight(50);
        imageViewarztVerwaltung.setPreserveRatio(true);

        Label arztVerwaltungLabel = new Label("Arzt-Verwaltung");
        arztVerwaltungLabel.setStyle("-fx-text-fill: white;");
        arztverwaltung.getChildren().addAll(imageViewarztVerwaltung, arztVerwaltungLabel);

        // Zweite Zeile (TextField mit TableView)
        VBox tableviewBox = new VBox(10);
        TableView<Arzt> arztTable = new TableView<>();
        arztTable.setMaxWidth(Double.MAX_VALUE);
        arztTable.setPrefHeight(500);
        arztTable.setStyle("-fx-font-size: 16px;");
        TableColumn<Arzt, Number> idColumn = new TableColumn<>("ID");
        TableColumn<Arzt, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Arzt, String> SpezialisierungColumn = new TableColumn<>("Spezialisierung");
        TableColumn<Arzt, String> telefonColumn = new TableColumn<>("Telefon");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("arzt_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpezialisierungColumn.setCellValueFactory(new PropertyValueFactory<>("spezialisierung"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefon"));


        arztTable.getColumns().addAll(idColumn,nameColumn,SpezialisierungColumn,telefonColumn);

        ArztService arztService=new ArztService();
//        List<Patient> patients=patientService.getPatienten();
//        patientTable.getItems().addAll(patients);
        ObservableList<Arzt> arztdata = FXCollections.observableArrayList(arztService.getListAerzte());
        FilteredList<Arzt> filteredList=new FilteredList<>(arztdata, arzt -> true);
        arztTable.setItems(filteredList);


        TextField serachTextField = new TextField();
        serachTextField.setPromptText("Arzt suchen...");
        serachTextField.setMaxWidth(Double.MAX_VALUE);
        serachTextField.setPrefWidth(420);
        serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(arzt -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String search = newValue.toLowerCase();

                return arzt.getName().toLowerCase().contains(search)
                        || arzt.getSpezialisierung().toLowerCase().contains(search)
                        || arzt.getTelefon().toLowerCase().contains(search);
            });
        });

        tableviewBox.getChildren().addAll(serachTextField, arztTable);

        //buttons (Add, Update, Delete)
        VBox arztBtn = new VBox(10);
        arztBtn.getStyleClass().add("patienten-Btn");
        Button addBtn = new Button("⎙  Ärzte PDF\n");
        Button aktionsbtn = new Button("Arzt Aktions \uD83D\uDD3D\u200B");
        arztBtn.getChildren().addAll(addBtn, aktionsbtn);
        addBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ärztebericht speichern");

            fileChooser.setInitialFileName("Ärztebericht.pdf");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF Dateien (*.pdf)",
                            "*.pdf"
                    )
            );

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {

                try {

                    List<Arzt> arzts = arztService.getListAerzte();

                    ArztPdfService.createArztReport(
                            arzts,
                            file.getAbsolutePath()
                    );

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erfolgreich");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Der Ärztebericht wurde erfolgreich erstellt."
                    );
                    alert.showAndWait();

                } catch (SQLException e) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Ärztedaten konnten nicht geladen werden."
                    );
                    alert.showAndWait();

                }
            }
        });

        // Form for adding/updating arzt
        VBox addarztForm = new VBox(10);
        TextField nametxt = new TextField();
        ComboBox<String> abteilungtxt = new ComboBox<>();
        TextField telefonnummer=new TextField();


        HBox arztActionsbtn = new HBox(5);
        Button arztAddbtn = new Button("Hinzufügen");
        Button arztUpdateBtn = new Button("Änderung");
        arztActionsbtn.getChildren().addAll(arztAddbtn, arztUpdateBtn);

        Arzt[] selectedArzt = new Arzt[1];
        arztTable.setOnMouseClicked(mouseEvent -> {
            Arzt selectedArzt1 = arztTable.getSelectionModel().getSelectedItem();
            if(selectedArzt1 !=null ){
                selectedArzt[0]=selectedArzt1;
                nametxt.setText(selectedArzt1.getName());
                abteilungtxt.setValue(selectedArzt1.getSpezialisierung());
                telefonnummer.setText(selectedArzt1.getTelefon());
                addarztForm.setVisible(true);


            }
        });
        arztAddbtn.setOnAction(event -> {

            if(nametxt.getText().isEmpty() || abteilungtxt.getValue()==null
                    || telefonnummer.getText().isEmpty()) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehlende Angaben");
                alert.setHeaderText(null);
                alert.setContentText("Bitte alle Felder ausfüllen");
                alert.showAndWait();
                return;
            }
            Arzt arzt=new Arzt(
                    0,
                    nametxt.getText(),
                    abteilungtxt.getValue(),
                    telefonnummer.getText()
            );

            try {
                arztService.arztHinzufuegen(arzt);
                loadArzt(arztdata, arztService);
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
                alert.setContentText("Arzt konnte nicht gespeichert werden.");
                alert.showAndWait();
            }
        });
        arztUpdateBtn.setOnAction(event -> {
            if(selectedArzt[0] == null){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Bitte zuerst einen Arzt auswählen.");
                alert.showAndWait();
                return;
            }
            if(nametxt.getText().isEmpty() || abteilungtxt.getValue()==null
                    || telefonnummer.getText().isEmpty()) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehlende Angaben");
                alert.setHeaderText(null);
                alert.setContentText("Bitte alle Felder ausfüllen");
                alert.showAndWait();
                return;
            }
            Arzt arzt=selectedArzt[0];
            arzt.setName(nametxt.getText());
            arzt.setSpezialisierung(abteilungtxt.getValue());
            arzt.setTelefonnummer(telefonnummer.getText());


            try {
                arztService.updatearzt(arzt);
                loadArzt(arztdata, arztService);
                arztTable.refresh();
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
        telefonnummer.setPromptText("Geben Sie bitte Telefonnummer ein");
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
        abteilungtxt.setValue("Spezialiserung auswählen");
        abteilungtxt.setStyle("-fx-font-size: 16px;");

        addarztForm.getChildren().addAll(
                nametxt,
                abteilungtxt,
                telefonnummer,
                arztActionsbtn
        );
        VBox deleteBereich=new VBox(5);
        Label deleteLabel=new Label("Wählen Sie den Datensatz aus, den Sie löschen möchten.");
        deleteLabel.setStyle("-fx-font-size: 12px;");
        Button deleteBtn =new Button("Arzt Löschen");
        deleteBereich.getChildren().addAll(deleteBtn,deleteLabel);
        deleteBtn.setOnAction(event -> {
            try {
                arztService.deleteArzt(selectedArzt[0].getArzt_id());
                loadArzt(arztdata, arztService);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erfolgreich");
                alert.setHeaderText(null);
                alert.setContentText("Arzt : "+selectedArzt[0].getName()+" wurde erfolgreich gelöscht.");
                alert.showAndWait();
                arztTable.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });



        VBox rightSideContainer = new VBox(15);
        rightSideContainer.getChildren().addAll(arztBtn, addarztForm);
        rightSideContainer.setMaxWidth(Double.MAX_VALUE);
        addarztForm.setVisible(false);
//        aktionsbtn.setOnAction(event -> addPatientForm.setVisible(true));
        aktionsbtn.setOnAction(event ->
                addarztForm.setVisible(!addarztForm.isVisible())
        );


        root.add(arztverwaltung, 0, 0);
        GridPane.setColumnSpan(arztverwaltung, 2);

        root.add(tableviewBox, 0, 1);
        root.add(rightSideContainer, 1, 1);
        root.add(deleteBereich,1,2);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void loadArzt(ObservableList<Arzt> arztData, ArztService arztService) {
        try {

            arztData.setAll(arztService.getListAerzte());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
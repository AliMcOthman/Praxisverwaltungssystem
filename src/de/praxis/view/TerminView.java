package de.praxis.view;

import de.praxis.model.Arzt;
import de.praxis.model.Patient;
import de.praxis.model.Termin;
import de.praxis.service.ArztService;
import de.praxis.service.PatientService;
import de.praxis.service.TerminService;
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TerminView extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Termin Verwaltung");
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
        HBox terminverwaltung = new HBox(30);
        terminverwaltung.setPadding(new Insets(20));
        Image imageterminVerwaltung = new Image(getClass().getResource("/de/praxis/images/DashBoarddIcon.png").toExternalForm());
        ImageView imageViewterminVerwaltung = new ImageView(imageterminVerwaltung);
        imageViewterminVerwaltung.setFitWidth(50);
        imageViewterminVerwaltung.setFitHeight(50);
        imageViewterminVerwaltung.setPreserveRatio(true);

        Label terminVerwaltungLabel = new Label("Termin-Verwaltung");
        terminVerwaltungLabel.setStyle("-fx-text-fill: white;");
        terminverwaltung.getChildren().addAll(imageViewterminVerwaltung, terminVerwaltungLabel);

        // Zweite Zeile (TextField mit TableView)
        VBox tableviewBox = new VBox(10);
        TableView<Termin> terminTable = new TableView<>();
        terminTable.setMaxWidth(Double.MAX_VALUE);
        terminTable.setPrefHeight(500);
        terminTable.setStyle("-fx-font-size: 16px;");
        TableColumn<Termin, Number> idColumn = new TableColumn<>("ID");
        TableColumn<Termin, String> namePatientColumn = new TableColumn<>("Patient Name");
        TableColumn<Termin, String> nameArztColum = new TableColumn<>("Arzt Name");
        TableColumn<Termin, LocalDate> datumColumn = new TableColumn<>("Datum");
        TableColumn<Termin, LocalTime> uhrZeitColumn = new TableColumn<>("Zeit");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("termin_id"));
        namePatientColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        nameArztColum.setCellValueFactory(new PropertyValueFactory<>("arztName"));
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        uhrZeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));

        terminTable.getColumns().addAll(idColumn,namePatientColumn,nameArztColum,datumColumn,uhrZeitColumn);
        TerminService terminService=new TerminService();
//        List<Patient> patients=patientService.getPatienten();
//        terminTable.getItems().addAll();
        ObservableList<Termin> terminData = FXCollections.observableArrayList(terminService.getListTermine());
        FilteredList<Termin> filteredList=new FilteredList<>(terminData, termin -> true);
        terminTable.setItems(filteredList);

        TextField serachTextField = new TextField();
        serachTextField.setPromptText("Termin suchen...");
        serachTextField.setMaxWidth(Double.MAX_VALUE);
        serachTextField.setPrefWidth(420);

        tableviewBox.getChildren().addAll(serachTextField, terminTable);

        //buttons (Add, Update, Delete)
        VBox terminBtn = new VBox(10);
        terminBtn.getStyleClass().add("patienten-Btn");
        Button addBtn = new Button("⎙ Termine PDF\n");
        Button aktionsbtn = new Button("Termin Aktions \uD83D\uDD3D\u200B");
        terminBtn.getChildren().addAll(addBtn, aktionsbtn);

        // Form for adding/updating termin
        PatientService patientService=new PatientService();
        ArztService arztService=new ArztService();
        VBox addterminForm = new VBox(10);
        ComboBox<String> patientNametxt = new ComboBox<>();
        patientNametxt.setStyle("-fx-font-size: 16px;");
        ComboBox<String> arztNametxt = new ComboBox<>();
        arztNametxt.setStyle("-fx-font-size: 16px;");
        patientNametxt.getItems().addAll(patientService.getPatientenNamen());
        arztNametxt.getItems().addAll(arztService.getAerzteNamen());

        DatePicker datePicker= new DatePicker();
        ComboBox<Integer> stuBox=new ComboBox<>();
        ComboBox<Integer> minBox=new ComboBox<>();
        for (int i = 8; i <= 20 ; i++) {
            stuBox.getItems().add(i);
        }
        for (int i = 0; i < 60 ; i++) {
            minBox.getItems().add(i);
        }
        HBox terminStunde=new HBox(5);
        Label terminStundelabel=new Label("Uhr");
        Label terminMinutelabel=new Label("Minute");
        stuBox.setPromptText("Stunde");
        minBox.setPromptText("Minute");
        terminStunde.getChildren().addAll(stuBox,terminStundelabel,minBox,terminMinutelabel);



        HBox terminActionsbtn = new HBox(5);
        Button terminAddbtn = new Button("Vereinbaren");
        Button terminUpdateBtn = new Button("Änderung");
        terminActionsbtn.getChildren().addAll(terminAddbtn, terminUpdateBtn);
        terminAddbtn.setOnAction(event -> {
//            LocalDateTime terminDatum=LocalDateTime.of(datePicker.getValue(), LocalTime.of(stuBox.getValue(),minBox.getValue()));
            terminAddbtn.setOnAction(e -> {

                if (patientNametxt.getValue() == null ||
                        arztNametxt.getValue() == null ||
                        datePicker.getValue() == null ||
                        stuBox.getValue() == null ||
                        minBox.getValue() == null) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Fehlende Daten");
                    alert.setHeaderText(null);
                    alert.setContentText("Bitte alle Daten eingeben.");
                    alert.showAndWait();

                    return;
                }

                LocalDateTime terminDatum =
                        LocalDateTime.of(
                                datePicker.getValue(),
                                LocalTime.of(
                                        stuBox.getValue(),
                                        minBox.getValue()
                                )
                        );

                System.out.println(terminDatum);
            });
        });

        patientNametxt.setPromptText("Name von Patient");
        arztNametxt.setPromptText("Name von Arzt");


        addterminForm.getChildren().addAll(
                patientNametxt,
                arztNametxt,
                datePicker,
                terminStunde,
                terminActionsbtn
        );
        VBox deleteBereich=new VBox(5);
        Label deleteLabel=new Label("Wählen Sie den Datensatz aus, den Sie löschen möchten.");
        deleteLabel.setStyle("-fx-font-size: 12px;");
        Button deleteBtn =new Button("Termin Löschen");
        deleteBereich.getChildren().addAll(deleteBtn,deleteLabel);




        VBox rightSideContainer = new VBox(15);
        rightSideContainer.getChildren().addAll(terminBtn, addterminForm);
        rightSideContainer.setMaxWidth(Double.MAX_VALUE);
        addterminForm.setVisible(false);
//        aktionsbtn.setOnAction(event -> addPatientForm.setVisible(true));
        aktionsbtn.setOnAction(event ->
                addterminForm.setVisible(!addterminForm.isVisible())
        );


        root.add(terminverwaltung, 0, 0);
        GridPane.setColumnSpan(terminverwaltung, 2);

        root.add(tableviewBox, 0, 1);
        root.add(rightSideContainer, 1, 1);
        root.add(deleteBereich,1,2);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


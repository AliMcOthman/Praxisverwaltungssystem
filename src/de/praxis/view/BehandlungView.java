package de.praxis.view;

import de.praxis.model.Behandlung;
import de.praxis.model.Termin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class BehandlungView extends Application {
    public void start(Stage primaryStage){
        primaryStage.setTitle("Behandlung Verwaltung");
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
        HBox behandlungverwaltung = new HBox(30);
        behandlungverwaltung.setPadding(new Insets(20));
        Image imagebehandlungVerwaltung = new Image(getClass().getResource("/de/praxis/images/DashBoarddIcon.png").toExternalForm());
        ImageView imageViewbehandlungVerwaltung = new ImageView(imagebehandlungVerwaltung);
        imageViewbehandlungVerwaltung.setFitWidth(50);
        imageViewbehandlungVerwaltung.setFitHeight(50);
        imageViewbehandlungVerwaltung.setPreserveRatio(true);

        Label behandlungVerwaltungLabel = new Label("Behandlung-Verwaltung");
        behandlungVerwaltungLabel.setStyle("-fx-text-fill: white;");
        behandlungverwaltung.getChildren().addAll(imageViewbehandlungVerwaltung, behandlungVerwaltungLabel);

        // Zweite Zeile (TextField mit TableView)
        VBox tableviewBox = new VBox(10);
        TableView<Behandlung> behandlungTable = new TableView<>();
        behandlungTable.setMaxWidth(Double.MAX_VALUE);
        behandlungTable.setPrefHeight(500);

        TextField serachTextField = new TextField();
        serachTextField.setPromptText("Behandlung suchen...");
        serachTextField.setMaxWidth(Double.MAX_VALUE);
        serachTextField.setPrefWidth(420);

        tableviewBox.getChildren().addAll(serachTextField, behandlungTable);

        //buttons (Add, Update, Delete)
        VBox behandlungBtn = new VBox(10);
        behandlungBtn.getStyleClass().add("patienten-Btn");
        Button addBtn = new Button("◀\u200B Behandlung anzeigen\n");
        Button aktionsbtn = new Button("\uD83D\uDD3D\u200Bbehandlung Aktions ");
        behandlungBtn.getChildren().addAll(addBtn, aktionsbtn);

        // Form for adding/updating termin
        VBox addbehandlungForm = new VBox(10);
        TextField behandlungPatientNametxt= new TextField();
        behandlungPatientNametxt.setPromptText("Name von Patient..");
        TextField behandlungArztNametxt = new TextField();
        behandlungArztNametxt.setPromptText("Name von Arzt..");

        DatePicker datePicker= new DatePicker();
        ComboBox<Integer> stuBox=new ComboBox<>();
        ComboBox<Integer> minBox=new ComboBox<>();
        for (int i = 8; i <= 20 ; i++) {
            stuBox.getItems().add(i);
        }
        for (int i = 0; i < 60 ; i++) {
            minBox.getItems().add(i);
        }
        HBox behandlungStunde=new HBox(5);
        Label behandlungStundelabel=new Label("Uhr");
        Label behandlungMinutelabel=new Label("Minute");
        stuBox.setPromptText("Stunde");
        minBox.setPromptText("Minute");
        behandlungStunde.getChildren().addAll(stuBox,behandlungStundelabel,minBox,behandlungMinutelabel);

        TextArea beschreibung = new TextArea();
        beschreibung.setPromptText("Beschreibung eingeben...");
        beschreibung.setPrefRowCount(5);
        beschreibung.setWrapText(true);
        beschreibung.setStyle("-fx-font-size: 14px;");

        HBox behandlungActionsbtn = new HBox(5);
        Button behandlungAddbtn = new Button("Hinzufügen");
        Button behandlungUpdateBtn = new Button("Änderung");
        behandlungActionsbtn.getChildren().addAll(behandlungAddbtn, behandlungUpdateBtn);
        behandlungAddbtn.setOnAction(event -> {
            LocalDateTime behandlungDatum=LocalDateTime.of(datePicker.getValue(), LocalTime.of(stuBox.getValue(),minBox.getValue()));
        });


        addbehandlungForm.getChildren().addAll(
                behandlungPatientNametxt,
                behandlungArztNametxt,
                datePicker,
                behandlungStunde,
                beschreibung,
                behandlungActionsbtn
        );
        VBox deleteBereich=new VBox(5);
        Label deleteLabel=new Label("Wählen Sie den Datensatz aus, den Sie löschen möchten.");
        deleteLabel.setStyle("-fx-font-size: 12px;");
        Button deleteBtn =new Button("Behandlung Löschen");
        deleteBereich.getChildren().addAll(deleteBtn,deleteLabel);




        VBox rightSideContainer = new VBox(15);
        rightSideContainer.getChildren().addAll(behandlungBtn, addbehandlungForm);
        rightSideContainer.setMaxWidth(Double.MAX_VALUE);
        addbehandlungForm.setVisible(false);
//        aktionsbtn.setOnAction(event -> addPatientForm.setVisible(true));
        aktionsbtn.setOnAction(event ->
                addbehandlungForm.setVisible(!addbehandlungForm.isVisible())
        );


        root.add(behandlungverwaltung, 0, 0);
        GridPane.setColumnSpan(behandlungverwaltung, 2);

        root.add(tableviewBox, 0, 1);
        root.add(rightSideContainer, 1, 1);
        root.add(deleteBereich,1,2);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

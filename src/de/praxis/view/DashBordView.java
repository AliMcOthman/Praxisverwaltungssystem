package de.praxis.view;

import de.praxis.service.ArztService;
import de.praxis.service.BehandlungService;
import de.praxis.service.PatientService;
import de.praxis.service.TerminService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class DashBordView {

    public void show(Stage primaryStage) throws SQLException {

        primaryStage.setTitle("DASH-BOARD");
        Font.loadFont(
                getClass().getResourceAsStream(
                        "/de/praxis/fonts/Oswald-Regular.ttf"
                ),
                16
        );


        HBox mainLayout = new HBox(20);

        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setPadding(new Insets(20));




        VBox dashButtons = new VBox(20);
        dashButtons.setAlignment(Pos.TOP_CENTER);
        Button dashButton = new Button("Dashboard");
        Button dashPatientButton = new Button("Patienten");
        Button dashArztButton = new Button("Ärzte");
        Button dashTerminButton = new Button("Termine");
        Button dashBehandlungButton = new Button("Behandlung");
        Button dashAbmeldenButton = new Button("Abmelden");

        dashButtons.getStyleClass().add("dash-buttons");

        dashButtons.getChildren().addAll(
                dashButton,
                dashPatientButton,
                dashArztButton,
                dashTerminButton,
                dashBehandlungButton,
                dashAbmeldenButton
        );

        Label dashBoaredLabel=new Label("DashBoard");
        dashBoaredLabel.setStyle("-fx-text-fill: white;");
        Image imageDashLogo = new Image(getClass().getResource("/de/praxis/images/DashBoarddIcon.png").toExternalForm());
        ImageView imageViewDashBoard=new ImageView(imageDashLogo);
        VBox dashboardIcon=new VBox(20);
        dashboardIcon.setAlignment(Pos.CENTER);
        dashboardIcon.getChildren().addAll(dashBoaredLabel,imageViewDashBoard);




        GridPane cardsLayout = new GridPane();

        cardsLayout.setHgap(10);
        cardsLayout.setVgap(10);

        cardsLayout.setMaxWidth(Double.MAX_VALUE);
        cardsLayout.setMaxHeight(Double.MAX_VALUE);
        cardsLayout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);




        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();

        col1.setPercentWidth(20);
        col2.setPercentWidth(20);
        col3.setPercentWidth(20);
        col4.setPercentWidth(20);
        col5.setPercentWidth(20);

        cardsLayout.getColumnConstraints().addAll(col1, col2, col3, col4,col5);




        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();

        row1.setPercentHeight(30);
        row2.setPercentHeight(70);

        cardsLayout.getRowConstraints().addAll(row1, row2);



        PatientService patientService=new PatientService();
        String patientAnzahl= String.valueOf(patientService.getAllPateintenAnzahl());

        VBox dashViewInnen1 = createCard("/de/praxis/images/patientIcon.png","Pateinten",patientAnzahl);

        ArztService arztService=new ArztService();
        String arztAnzahl= String.valueOf(arztService.getAerzte());

        VBox dashViewInnen2 = createCard("/de/praxis/images/arzt.png", "Ärzte",arztAnzahl);


        TerminService terminService=new TerminService();
        String terminAnzahl= String.valueOf(terminService.getAlleTermine());
        VBox dashViewInnen3 = createCard("/de/praxis/images/termin.png", "Termin",terminAnzahl);


        BehandlungService behandlungService=new BehandlungService();
        String behandlungAnzahl= String.valueOf(behandlungService.getBehandlungen());
        VBox dashViewInnen4 = createCard("/de/praxis/images/behandlung.png","Behandlung", behandlungAnzahl);




        HBox dashViewInnen5 = createCardUnten("/de/praxis/images/pie-chart.png","129");




        HBox dashViewInnen6 = createCardUnten("/de/praxis/images/browser.png","Behandlung");

        cardsLayout.add(dashboardIcon,0,0);
        cardsLayout.add(dashViewInnen1, 1, 0);
        cardsLayout.add(dashViewInnen2, 2, 0);
        cardsLayout.add(dashViewInnen3, 3, 0);
        cardsLayout.add(dashViewInnen4, 4, 0);


        cardsLayout.add(dashViewInnen5, 0, 1, 3, 1);
        cardsLayout.add(dashViewInnen6,  3, 1, 2, 1);




        GridPane.setHgrow(dashboardIcon,Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen1,Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen2, Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen3, Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen4, Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen5, Priority.ALWAYS);

        GridPane.setHgrow(dashViewInnen6, Priority.ALWAYS);


        GridPane.setVgrow(dashboardIcon,Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen1, Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen2, Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen3, Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen4, Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen5, Priority.ALWAYS);

        GridPane.setVgrow(dashViewInnen6, Priority.ALWAYS);




        HBox.setHgrow(cardsLayout, Priority.ALWAYS);

        mainLayout.getChildren().addAll(dashButtons, cardsLayout);




        Scene scene = new Scene(mainLayout, 1000, 600);


        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());



        primaryStage.setScene(scene);
        primaryStage.show();

        // Buttons Action
        dashAbmeldenButton.setOnAction(event -> {
            Stage dashboardStage = (Stage) dashAbmeldenButton.getScene().getWindow();
            dashboardStage.close();

            Stage loginViewStage = new Stage();
            new LoginView().start(loginViewStage);
        });
        dashButton.setOnAction(event -> {
            Stage thisStage = (Stage) dashButton.getScene().getWindow();
            thisStage.show();
        });
        dashPatientButton.setOnAction(event -> {
            Stage patientStage=new Stage();
            PatientView patientView=new PatientView();
            try {
                patientView.start(patientStage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
        dashArztButton.setOnAction(event -> {
            Stage arztStage=new Stage();
            ArztView arztView=new ArztView();
            try {
                arztView.start(arztStage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        dashTerminButton.setOnAction(event -> {
            Stage terminStage=new Stage();
            TerminView terminView=new TerminView();
            try {
                terminView.start(terminStage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        dashBehandlungButton.setOnAction(event -> {
            Stage behandlungStage=new Stage();
            BehandlungView behandlungView=new BehandlungView();
            behandlungView.start(behandlungStage);


        });
    }



    // Methode zum Erstellen einer Karte

    private VBox createCard(String imagePath,String name, String number) {

        VBox card = new VBox(10);

        card.setAlignment(Pos.CENTER);

        card.setMaxWidth(Double.MAX_VALUE);
        card.setMaxHeight(Double.MAX_VALUE);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(3);

        card.setEffect(shadow);

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        Label anzahllbl = new Label(number);
        Label namelbl=new Label(name);

        card.getStyleClass().add("view-Box");

        card.getChildren().addAll(imageView, namelbl,anzahllbl);

        return card;
    }
    private HBox createCardUnten(String imagePath , String number) {

        HBox card = new HBox(10);

        card.setAlignment(Pos.CENTER);

        card.setMaxWidth(Double.MAX_VALUE);
        card.setMaxHeight(Double.MAX_VALUE);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(3);

        card.setEffect(shadow);

        Image image = new Image(getClass().getResource(imagePath).toExternalForm()
        );

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(150);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        Label anzahllbl = new Label(number);

        card.getStyleClass().add("view-Box");

        card.getChildren().addAll(imageView,anzahllbl);

        return card;
    }
}
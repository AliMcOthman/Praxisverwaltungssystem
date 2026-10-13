package de.praxis.view;

import de.praxis.service.LoginService;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
//--module-path "C:\Users\alfa\Desktop\Bibliotheken\openjfx-21.0.12_windows-x64_bin-sdk\javafx-sdk-21.0.12\lib" --add-modules javafx.controls,javafx.fxml

public class LoginView extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Krankenhaus von Reutlingen");
        Font.loadFont(
                getClass().getResourceAsStream(
                        "/de/praxis/fonts/Oswald-Regular.ttf"
                ),
                16
        );
        Image img = new Image(getClass().getResource("/de/praxis/images/LoginBild.png").toExternalForm());

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(20);
        shadow.setOffsetX(2);
        shadow.setOffsetY(3);
        shadow.setSpread(0.1);

        imageView.setEffect(shadow);

//        StackPane usernamePane = new StackPane();

        TextField benutzertxt = new TextField();
        benutzertxt.setPromptText("Benutzername");




        benutzertxt.getStyleClass().add("login-textfield");
        benutzertxt.setPrefWidth(250);
        benutzertxt.setMaxWidth(250);
        benutzertxt.setPrefHeight(35);


        PasswordField passworttxt = new PasswordField();
        passworttxt.setPromptText("Passwort");
        passworttxt.setPrefWidth(250);
        passworttxt.setMaxWidth(250);
        passworttxt.setPrefHeight(35);

        Button loginbtn = new Button("Login");
        Button abbrechenbtn = new Button("Abbrechen");

        HBox loginBox = new HBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.getChildren().addAll(loginbtn, abbrechenbtn);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                imageView,
                benutzertxt,
                passworttxt,
                loginBox
        );
        loginbtn.setDefaultButton(true);
        loginbtn.setOnAction(event -> {
            String benutzername = benutzertxt.getText();
            String passwort = passworttxt.getText();
            boolean erfolg = LoginService.loginTest(benutzername, passwort);
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setHeaderText("Falsche Anmeldung");
            alert.setContentText("Falscher Benutzername oder falsches Passwort");

            if (erfolg) {

                Stage loginStage = (Stage) loginbtn.getScene().getWindow();
                loginStage.close();

                Stage dashboardStage = new Stage();
                try {
                    new DashBordView().show(dashboardStage);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else {
                alert.showAndWait();
            }
        });
        abbrechenbtn.setOnAction(event -> {
            benutzertxt.clear();
            passworttxt.clear();
        });

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/de/praxis/css/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ModeratorView extends Application {

    /**
     * Font sizes for moderator screen.
     */
    private DoubleProperty subTitleFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty tabFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty percentageFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty textBoxFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty normalFontSize = new SimpleDoubleProperty(10);

    /**
     * Creates the moderator screen scene and loads it on the primary stage.
     * @param primaryStage primary stage of the app
     * @throws IOException if FXMLLoader fails to load the url
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load file
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/moderatorRoom.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        // Create new scene with root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add options to dropdown buttons
        ChoiceBox<String> answers = (ChoiceBox) root.lookup("#answerAmount");
        ChoiceBox<String> correctAnswer = (ChoiceBox) root.lookup("#correctAnswer");

        for (int i=1; i<11; i++)
            answers.getItems().add(String.valueOf(i));

        for(char letter = 'A'; letter <='J'; letter++ )
            correctAnswer.getItems().add(String.valueOf(letter));

        // Bind font sizes to screen size
        subTitleFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(85));


        tabFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        percentageFontSize.bind(Bindings.min(45,
                scene.widthProperty().add(scene.heightProperty()).divide(45)));

        buttonFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(120)));

        textBoxFontSize.bind(Bindings.min(18,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        normalFontSize.bind(Bindings.min(18,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".subTitleText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    subTitleFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".tab-label")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    tabFontSize.asString(), ";"));
        }


        for (Node node : root.lookupAll(".normalText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    normalFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".buttonText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    buttonFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".textBox")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    textBoxFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".percentage")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    percentageFontSize.asString(), ";"));
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}

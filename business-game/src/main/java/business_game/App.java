package business_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import game_engine.utils.Loader;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("App"), 640, 480);
        stage.setTitle("Fish Ate My Money?!");
        stage.getIcons().add(Loader.loadImage("Icon"));
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private static ArrayList<EventInterface> stop_event = new ArrayList<EventInterface>();

    public static void addStopEvent(EventInterface event) {
        stop_event.add(event);
    }

    @Override
    public void stop() {
        for (EventInterface event : stop_event)
            event.onEvent();
    }

}
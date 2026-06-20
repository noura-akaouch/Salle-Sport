import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MainView.fxml")
            );

            Scene scene = new Scene(loader.load());

            // CSS
            scene.getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
            );

            primaryStage.setTitle("Gestion Salle de Sport");
            primaryStage.setScene(scene);

            primaryStage.setWidth(1200);
            primaryStage.setHeight(700);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import controllers.mediaController;
import presenter.displayPresenter;


/**
 * Main Class to launch application
 * @author John Falcon
 */
public class Launcher extends Application{

    public void start(Stage primaryStage) throws Exception {

        final StackPane root = new StackPane();
        final StackPane rootSecondary = new StackPane();

        /* Set Controller window */
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        primaryStage.setTitle("Mirador interactivo Puebla - Controller");

        Scene scene = new Scene(root, width, height);

        mediaController mediaCtrlr = new mediaController();
        final MediaView theView = mediaCtrlr.loadVideo("file:///Users/johm_tdc/Puebla/Intro.mp4", root, true);
        root.getChildren().add(theView);

        root.widthProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                theView.setFitWidth(root.getWidth());
            }
        });

        root.heightProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                theView.setFitHeight(root.getHeight());
            }
        });

        //Rectangle2D secondaryScreenBounds = Screen.getPrimary().getVisualBounds();
        ObservableList<Screen> screens = Screen.getScreens();
        System.out.println(screens);
        Stage secondStage = new Stage();
        Scene sceneViewport = new Scene(rootSecondary, width, height);
        secondStage.setScene(sceneViewport);
        secondStage.setTitle("Mirador interactivo Puebla - Viewport");
        secondStage.show();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){

        launch(args);
    }

}

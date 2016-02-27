import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import controllers.mediaController;
import controllers.contentDistribution;
import presenter.displayPresenter;


/**
 * Main Class to launch application
 * @author John Falcon
 */
public class Launcher extends Application{

    public void start(Stage primaryStage) throws Exception {

        final StackPane root = new StackPane();
        final StackPane rootSecondary = new StackPane();
        int width = 800;
        int height = 600;

        /* Initialize content delivery */
        contentDistribution elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);
        elContent.connectDB();

        /* Initialize media manager */
        mediaController mediaCtrlr = new mediaController();

        /* Initialize presenter and displays */
        displayPresenter presenter = new displayPresenter();

            presenter.detectDisplays();
            Screen myPrimaryScreen = presenter.getPrimaryScreen();
            Screen mySecondaryScreen = presenter.getSecondaryScreen();

            /* Set Controller window dimensions */
            Rectangle2D primaryScreenBounds = myPrimaryScreen.getVisualBounds();

                primaryStage.initStyle(StageStyle.TRANSPARENT);
                primaryStage.setTitle("Mirador interactivo Puebla - Viewport");
                primaryStage.setX(primaryScreenBounds.getMinX());
                primaryStage.setY(primaryScreenBounds.getMinY());
                primaryStage.setWidth(primaryScreenBounds.getWidth());
                primaryStage.setHeight(primaryScreenBounds.getHeight());

            /* Set Controller window dimensions */
            Rectangle2D secondaryScreenBounds = myPrimaryScreen.getVisualBounds();
                Scene sceneSurface = new Scene(root, width, height);
                Stage secondStageTurbineBlade = new Stage();
                Scene sceneViewport = new Scene(rootSecondary, width, height);
                secondStageTurbineBlade.setScene(sceneViewport);
                secondStageTurbineBlade.initStyle(StageStyle.TRANSPARENT);
                secondStageTurbineBlade.setTitle("Mirador interactivo Puebla - Controller Surface");
                secondStageTurbineBlade.setX(secondaryScreenBounds.getMinX());
                secondStageTurbineBlade.setY(secondaryScreenBounds.getMinY());
                secondStageTurbineBlade.setWidth(secondaryScreenBounds.getWidth());
                secondStageTurbineBlade.setHeight(secondaryScreenBounds.getHeight());

        /* Add elements to Controller surface */



        /* Add elements to Viewport screen */
        final MediaView theView = mediaCtrlr.loadVideo( "file:///Users/johm_tdc/Puebla/Intro.mp4", root, true, true );
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


        secondStageTurbineBlade.show();

        primaryStage.setScene(sceneSurface);
        primaryStage.show();
    }

    public static void main(String[] args){

        launch(args);
    }

}

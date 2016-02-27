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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Main Class to launch application
 * @author John Falcon
 */
public class Launcher extends Application implements ActionListener{

    public void start(Stage primaryStage) throws Exception {

        final StackPane stackPrimary = new StackPane();
        final StackPane stackSecondary = new StackPane();
        int width = 800;
        int height = 600;

        /* Initialize content delivery */
        contentDistribution elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);
        elContent.connectDB();

        /* Initialize media manager */
        mediaController mediaCtrlr = new mediaController();

        /* Initialize presenter and detect displays */
        displayPresenter presenter = new displayPresenter();

            presenter.detectDisplays();
            Screen myPrimaryScreen = presenter.getPrimaryScreen();
            Screen mySecondaryScreen = presenter.getSecondaryScreen();

            /* Set Viewport window dimensions */
            Rectangle2D primaryScreenBounds = myPrimaryScreen.getVisualBounds();

                Scene sceneViewport = new Scene(stackPrimary, width, height);
                primaryStage.setScene(sceneViewport);

                // primaryStage.initStyle(StageStyle.TRANSPARENT);
                primaryStage.setTitle("Mirador interactivo Puebla - Viewport");
                primaryStage.setX(primaryScreenBounds.getMinX());
                primaryStage.setY(primaryScreenBounds.getMinY());
                primaryStage.setWidth(primaryScreenBounds.getWidth());
                primaryStage.setHeight(primaryScreenBounds.getHeight());

            /* Set Controller window dimensions */
            Rectangle2D secondaryScreenBounds = mySecondaryScreen.getVisualBounds();
            Stage secondStageTurbineBlade = new Stage();

                Scene sceneSurface = new Scene(stackSecondary, width, height);
                secondStageTurbineBlade.setScene(sceneSurface);

                // secondStageTurbineBlade.initStyle(StageStyle.TRANSPARENT);
                secondStageTurbineBlade.setTitle("Mirador interactivo Puebla - Controller Surface");
                secondStageTurbineBlade.setX(secondaryScreenBounds.getMinX());
                secondStageTurbineBlade.setY(secondaryScreenBounds.getMinY());
                secondStageTurbineBlade.setWidth(secondaryScreenBounds.getWidth());
                secondStageTurbineBlade.setHeight(secondaryScreenBounds.getHeight());

        /* Add elements to Controller surface */
        final MediaView theCompassView = mediaCtrlr.loadVideo( "file:///Users/johm_tdc/Puebla/loop-brujula.mp4", stackSecondary, true, true );
        stackSecondary.getChildren().add(theCompassView);

        stackSecondary.widthProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                theCompassView.setFitWidth(stackSecondary.getWidth());
            }
        });

        stackSecondary.heightProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                theCompassView.setFitHeight(stackSecondary.getHeight());
            }
        });

        /* Add elements to Viewport screen */
        final MediaView theViewportView = mediaCtrlr.loadVideo( "file:///Users/johm_tdc/Puebla/loop-transparente.mp4", stackPrimary, true, true );
        stackPrimary.getChildren().add(theViewportView);

        stackPrimary.widthProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    theViewportView.setFitWidth(stackPrimary.getWidth());
                }
            });

        stackPrimary.heightProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    theViewportView.setFitHeight(stackPrimary.getHeight());
                }
            });
        presenter.insertMenu("main", null);
        primaryStage.show();
        secondStageTurbineBlade.show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(e.toString());
    }

    public static void main(String[] args){

        launch(args);
    }

}

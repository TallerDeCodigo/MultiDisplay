import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import controllers.mediaController;
import controllers.contentDistribution;
import model.Place;
import presenter.displayPresenter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Main Class to launch application
 * @author John Falcon
 */
public class Launcher extends Application implements ActionListener{

    final StackPane stackPrimary = new StackPane();
    final StackPane stackSecondary = new StackPane();
    private contentDistribution elContent = null;
    int width = 800;
    int height = 600;
    final String resources = "file:///Users/johm_tdc/Puebla/";

    public void start(Stage primaryStage) throws Exception {

        /* Initialize content delivery */
        elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);

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
            final Stage secondStageTurbineBlade = new Stage();

                // secondStageTurbineBlade.initStyle(StageStyle.TRANSPARENT);
                secondStageTurbineBlade.setTitle("Mirador interactivo Puebla - Controller Surface");
                secondStageTurbineBlade.setX(secondaryScreenBounds.getMinX());
                secondStageTurbineBlade.setY(secondaryScreenBounds.getMinY());
                secondStageTurbineBlade.setWidth(secondaryScreenBounds.getWidth());
                secondStageTurbineBlade.setHeight(secondaryScreenBounds.getHeight());

        /* Add elements to Viewport screen */
        final MediaView theViewportView = mediaCtrlr.loadVideo( resources+"loop-transparente.mp4", stackPrimary, true, true );
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

        /* Add elements to Controller surface */
        final MediaView theCompassView = mediaCtrlr.loadVideo( resources+"loop-brujula.mp4", stackSecondary, true, true );

        stackSecondary.getChildren().add(theCompassView);
        Button myButton = new Button();
        myButton.setStyle("-fx-background-color:cyan");
        stackSecondary.getChildren().add(myButton);
        Scene sceneSurface = new Scene(stackSecondary, width, height);
        secondStageTurbineBlade.setScene(sceneSurface);

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

        /* try {
            BorderPane boxes = addBoxes();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        primaryStage.show();
        secondStageTurbineBlade.show();
    }


    public BorderPane addBoxes() throws Exception{
        BorderPane border = new BorderPane();
        VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));

        ArrayList<Place> elements;
        elements = elContent.fetchMainMenu();
        //Image imageDecline = new Image(getClass().getResourceAsStream(resources+"buttons/bg_submenu.png"));
        //System.out.println(imageDecline);
        /*Button button5 = new Button();
        button5.setGraphic(new ImageView(imageDecline));*/
        /*for (Place element : elements) {

            Button myButton = new Button(element.getName());
            vbox.getChildren().addAl(myButton);
            //myButton.setStyle("-fx-background-image: url('"+resources+"buttons/bg_submenu.png')");
            //VBox.setMargin(myButton, new Insets(10, 10, 10, 10));
        }*/
        Button myButton = new Button("My Button");
        vbox.getChildren().add(myButton);

        border.setLeft(vbox);
        return border;
    }

    /**
     * Initialize events in the stage
     * @see ActionListener
     */
    private void initEvents(){

       /* Image imageDecline = new Image(getClass().getResourceAsStream("not.png"));
        Button button5 = new Button();
        button5.setGraphic(new ImageView(imageDecline));*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(e.toString());
    }

    public static void main(String[] args){

        launch(args);
    }

}

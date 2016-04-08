import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import controllers.mediaController;
import controllers.contentDistribution;
import javafx.stage.StageStyle;
import model.Place;
import presenter.displayPresenter;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Main Class to launch application
 * @author John Falcon
 */
public class Launcher extends Application{

    final StackPane stackPrimary = new StackPane();
    final StackPane stackSecondary = new StackPane();
    private contentDistribution elContent = null;
    int width  = 800;
    int height = 600;
    final String resources = "file:///C://Puebla/";
    //final String resources = "file:///Users/johm_tdc/Puebla/";


    public void start(final Stage primaryStage) throws Exception {

        /* Initialize content delivery class */
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
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Mirador interactivo Puebla - Viewport");
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        /* Set Controller window dimensions */
        Rectangle2D secondaryScreenBounds = mySecondaryScreen.getVisualBounds();
        final Stage secondStageTurbineBlade = new Stage();

        secondStageTurbineBlade.initStyle(StageStyle.TRANSPARENT);
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


        final Button initButton = new Button("TOCA LA PANTALLA PARA INICIAR");
        initButton.setMinWidth(1280);
        initButton.setMinHeight(720);
        initButton.setStyle("-fx-background-color: transparent; -fx-font-size: 36px; -fx-text-fill: #0b1247; -fx-text-align: center; -fx-font-weight: 900; -fx-padding: 780 0 0 0");
        initButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BorderPane boxes = null;
                try {
                    boxes = addBoxes(primaryStage);
                    stackSecondary.getChildren().remove(initButton);
                    stackSecondary.getChildren().add(boxes);
                    Scene sceneSurface = new Scene(stackSecondary, width, height);
                    secondStageTurbineBlade.setScene(sceneSurface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stackSecondary.getChildren().addAll(theCompassView, initButton);
        Scene sceneSurface = new Scene(stackSecondary, width, height);
        secondStageTurbineBlade.setScene(sceneSurface);

        primaryStage.show();
        secondStageTurbineBlade.show();
    }

    public BorderPane addBoxes(final Stage primaryStage) throws Exception{
        final BorderPane border = new BorderPane();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        final ArrayList<Button> Thaibuttons = new ArrayList<>();
        final ArrayList<Button> Thosebuttons = new ArrayList<>();

        ArrayList<Place> elements;
        elements = elContent.fetchMainMenu();
        Integer inset = 200;
        Integer index = 0;
        for (final Place element : elements) {
            final Button myButton = new Button();
            myButton.setStyle(  "-fx-background-image: url('"+resources+"resources/bg_"+element.getId().toString()+".png');" +
                                " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent; ");
            myButton.setPrefSize(360, 110);
            myButton.setId(element.getId().toString());
            Thaibuttons.add(index, myButton);
            index++;
            /* Set action on main menu buttons */
            myButton.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){

                    for(int i = 0; i < Thaibuttons.size(); i++){
                        Button myButtonHere = new Button();
                        myButtonHere = Thaibuttons.get(i);
                        myButtonHere.setStyle( "-fx-background-image: url('"+resources+"resources/bg_"+myButtonHere.getId()+".png');" +
                                " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent; ");
                    }

                    myButton.setStyle(  "-fx-background-image: url('"+resources+"resources/bg_"+element.getId().toString()+"_hover.png');" +
                            " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent; ");
                    ArrayList<Place> myResults = new ArrayList();
                    final displayPresenter presenter = new displayPresenter();
                    Integer subinset    = 220;
                    Integer subpadding  = 30;
                    try {
                        VBox vbox = new VBox();
                        vbox.setPadding(new Insets(20));
                        myResults = presenter.insertMenu("submenu", element.getId());
                        Integer index = 0;

                        Scene newScene = presenter.showStby(null);
                        if(Integer.parseInt(myButton.getId()) == 1 || Integer.parseInt(myButton.getId()) == 6)
                            newScene = presenter.showStby("estado");

                        primaryStage.setScene(newScene);

                        for (final Place place : myResults) {
                            final Button mySubButton = new Button();
                            mySubButton.setWrapText(true);
                            mySubButton.setText(place.getName());
                            mySubButton.setStyle("-fx-background-image: url('"+resources+"resources/bg_submenu.png');" +
                                                " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent;" +
                                                " -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-text-alignment: center; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
                            if(Integer.parseInt(myButton.getId()) == 5)
                                mySubButton.setStyle("-fx-background-image: url('"+resources+"resources/bg_submenu.png');" +
                                                    " -fx-background-size: cover; -fx-background-repeat: no-repeat; -fx-background-color: transparent;" +
                                                    " -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-text-alignment: center; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
                            mySubButton.setPrefSize(360, 80);
                            //mySubButton.setPadding(new Insets(2,0,2,0));
                            Thosebuttons.add(mySubButton);
                            /* Set action on submenu buttons */
                            mySubButton.setOnAction( new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    for(int i = 0; i < Thosebuttons.size(); i++){
                                        Button myButtonHere = new Button();
                                        myButtonHere = Thosebuttons.get(i);
                                        myButtonHere.setStyle("-fx-background-image: url('"+resources+"resources/bg_submenu.png');" +
                                                " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent;" +
                                                " -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-text-alignment: center; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
                                    }
                                    mySubButton.setStyle("-fx-background-image: url('"+resources+"resources/bg_submenu_hover.png');" +
                                            " -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-color: transparent;" +
                                            " -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-text-alignment: center; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
                                    try {
                                        Scene newScene = presenter.showDetail(place.getId());
                                        primaryStage.setScene(newScene);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            vbox.setAlignment(Pos.CENTER);
                            vbox.setMargin(mySubButton, new Insets(0, 0, 2, subinset));
                            vbox.getChildren().add(mySubButton);
                            index++;
                            if(index < (myResults.size()/2))
                                subinset = subinset+25;
                            if(index >= (myResults.size()/2))
                                subinset = subinset-25;
                            if(index > myResults.size())
                                subpadding = subpadding-10;
                        }
                        border.setRight(vbox);
                        border.setMargin(vbox, new Insets(0, 200, 0, 0));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            vbox.getChildren().add(myButton);
            VBox.setMargin(myButton, new Insets(5, 0, 0, inset));
            if(index <= 4)
                inset = inset-50;
            if(index > 4)
                inset = inset+50;
        }
        vbox.setAlignment(Pos.CENTER);
        border.setLeft(vbox);
        border.setMargin(vbox, new Insets(0,0,0,120));
        return border;
    }

    /**
     * Initialize events in the stage
     * @see ActionListener
     */
    private void initEvents(){

    }

    public static void main(String[] args){

        launch(args);
    }

}

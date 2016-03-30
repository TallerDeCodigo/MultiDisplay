package presenter;
import controllers.mediaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import controllers.contentDistribution;
import javafx.stage.StageStyle;
import model.Place;

/**
 * Presenter to manage multiple display application
 * @author John Falcon
 *
 */
public class displayPresenter {

    int width   = 800;
    int height  = 600;

    private Screen primaryScreen;
    private Screen secondaryScreen;
    //final String resources = "file:///C://Puebla/";
    final String resources = "file:///Users/johm_tdc/Puebla/";

    public displayPresenter(){

    }

    /**
     * Call stage to specific display
     * @param String displayName
     * @param Stage stage
     * @param Screen targetScreen
     * @return void
     */
    public static void callStageToDisplay( final String displayName, final Stage stage, final Screen targetScreen ){


    }

    /**
     * Detect the number and dimensions of displays connected to the device
     * @return void
     */
    public void detectDisplays(){
        final Screen primaryScreen = Screen.getPrimary();
        final List<Screen> allScreens = Screen.getScreens();
        // Screen secondaryScreen;
        this.primaryScreen = primaryScreen;
        if (allScreens.size() <= 1) {
            System.out.println("Only one screen detected, using the same output");
            this.primaryScreen      = primaryScreen;
            this.secondaryScreen    = primaryScreen;
        } else {
            System.out.println("Both screens working");
            if (allScreens.get(0).equals(primaryScreen)) {
                this.secondaryScreen = allScreens.get(1);
            } else {
                this.secondaryScreen = allScreens.get(0);
            }
        }
    }

    /**
     * Insert menu items into stage
     * @return void
     */
    public ArrayList insertMenu( String menuName, Integer group ) throws SQLException {

        contentDistribution elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);
        ArrayList menuItems = new ArrayList();
        if(Objects.equals(menuName, "main")){
            menuItems = elContent.fetchMainMenu();
        }
        if(menuName.equals("submenu")){
            menuItems = elContent.fetchSubmenu(group);
        }
        return menuItems;
    }

    /**
     * Show stand by panoramic
     * @return Scene
     */
    public Scene showStby(){
        Integer width = 800;
        Integer height = 600;
        BorderPane borderKane = new BorderPane();
        borderKane.setStyle("-fx-background-image: url('"+resources+"resources/panorama.jpg'); -fx-background-repeat: stretch;");
        return new Scene(borderKane, width, height) ;
    }

    /**
     * Show stand by panoramic
     * @return Scene
     */
    public Scene showInit(){
        Integer width = 800;
        Integer height = 600;
        BorderPane borderKane = new BorderPane();
        borderKane.setStyle("-fx-background-image: url('"+resources+"resources/panorama.jpg'); -fx-background-repeat: stretch;");
        return new Scene(borderKane, width, height) ;
    }

    /**
     * Fetch detailed information about a place
     * Returns new Scene
     * @return Scene
     */
    public Scene showDetail(Integer place_id) throws SQLException {
        Integer width = 800;
        Integer height = 600;
        BorderPane borderKane = new BorderPane();
        contentDistribution elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);
        Place pageContent = elContent.fetchDetail(place_id);
        mediaController mediaDude = new mediaController();
        //MediaView theView = mediaDude.loadVideo(resources+"content/"+pageContent.getGroup()+"/"+pageContent.getId()+"/video/", null, true, true);


        TextArea textContent = new TextArea();
        textContent.setText(pageContent.getDescription());
        textContent.setPrefColumnCount(60);
        textContent.setMinHeight(700);
        textContent.setWrapText(true);
        textContent.setEditable(false);
        textContent.setStyle("-fx-background-color: #f4f4f4;" +
                " -fx-font-size: 22px;  -fx-text-align: left; -fx-font-weight: 500; -fx-text-origin: top;");

        VBox textBox = new VBox();
        textBox.setPadding(new Insets(-80, 80, 0, 0));
        textBox.setPrefWidth(620);

        textBox.setAlignment(Pos.CENTER_RIGHT);
        textBox.getChildren().add(textContent);

        Label titleLabel  = new Label(pageContent.getName());
        titleLabel.setStyle("-fx-background-color: transparent;" +
                "-fx-font-size: 45px; -fx-text-fill: #004987; -fx-text-align: center; -fx-font-weight: 700; -fx-padding: 0 0 20 30");

        HBox titleBox = new HBox();
        titleBox.setPadding(new Insets(100, 0, 100, 200));
        titleBox.setAlignment(Pos.TOP_LEFT);
        titleBox.getChildren().add(titleLabel);

        Label latlongLabel  = new Label(pageContent.getLatlong());
        latlongLabel.setStyle("-fx-background-color: transparent;" +
                "-fx-font-size: 28px; -fx-text-fill: #004987; -fx-text-align: center; -fx-font-weight: 700;");

        HBox latlongBox = new HBox();
        latlongBox.setPadding(new Insets(-40, 0, 450, 325));
        latlongBox.setAlignment(Pos.BOTTOM_LEFT);
        latlongBox.getChildren().add(latlongLabel);


        borderKane.setRight(textBox);
        borderKane.setTop(titleBox);
        borderKane.setBottom(latlongBox);
        //borderKane.setCenter();

        //borderKane.setMargin(textContent, new Insets(120,200,0,0));
        if(!pageContent.getVideo().equals("")){
            MediaView theView = mediaDude.loadVideo(resources+"content/"+pageContent.getVideo(), null, true, true);
            theView.setViewport(new Rectangle2D(0,0,1080,608));
            borderKane.setCenter(theView);
            borderKane.setMargin(theView, new Insets(-150,0,18,200));
        }else{
            ImageView theView = mediaDude.loadImage(resources+"content/"+pageContent.getImage());
            //theView.setStyle("-fx-opacity: 0.6;");
            theView.setViewport(new Rectangle2D(0,0,1080,608));
            borderKane.setMargin(theView, new Insets(-170,0,0,200));
            borderKane.setCenter(theView);
        }

        borderKane.setMargin(textContent, new Insets(120, 200 ,0 ,0 ));
        borderKane.setStyle("-fx-background-image: url('"+resources+"resources/bg_detail.png'); -fx-background-repeat: stretch;");
        Scene myScene = new Scene(borderKane, width, height);
        return myScene;
    }


    public Screen getPrimaryScreen(){
        return this.primaryScreen;
    }

    public Screen getSecondaryScreen(){
        return this.secondaryScreen;
    }

}

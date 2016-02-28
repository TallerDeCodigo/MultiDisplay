package presenter;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import controllers.contentDistribution;

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
    public void insertMenu( String menuName, Integer group ) throws SQLException {

        contentDistribution elContent = new contentDistribution("jdbc:mysql://localhost:3306/puebla_interactive", null, null);

        if(Objects.equals(menuName, "main")){
            ArrayList menuItems = elContent.fetchMainMenu();
            System.out.println(menuItems.toString());
            for (int i = 0; i < menuItems.size(); i++) {
                System.out.println(menuItems.get(i));
            }
        }

    }

    public Screen getPrimaryScreen(){
        return this.primaryScreen;
    }

    public Screen getSecondaryScreen(){
        return this.secondaryScreen;
    }

}

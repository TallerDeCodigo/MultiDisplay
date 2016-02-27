package presenter;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

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

    public Screen getPrimaryScreen(){
        return this.primaryScreen;
    }

    public Screen getSecondaryScreen(){
        return this.secondaryScreen;
    }

}

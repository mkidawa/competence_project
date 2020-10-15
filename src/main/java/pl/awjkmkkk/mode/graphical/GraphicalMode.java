package pl.awjkmkkk.mode.graphical;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.awjkmkkk.view.fxml.StageController;
import pl.awjkmkkk.view.fxml.core.WindowDimensions;

import static pl.awjkmkkk.view.constant.ViewConstants.MAIN_PANEL_HEIGHT;
import static pl.awjkmkkk.view.constant.ViewConstants.MAIN_PANEL_WIDTH;
import static pl.awjkmkkk.view.constant.ViewConstants.PATH_CSS_DARK_STYLING;
import static pl.awjkmkkk.view.constant.ViewConstants.PATH_MAIN_PANEL;
import static pl.awjkmkkk.view.constant.ViewConstants.TITLE_MAIN_PANEL;

public class GraphicalMode extends Application {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void start(final Stage stage) throws Exception {
        StageController.buildStage(stage, PATH_MAIN_PANEL, TITLE_MAIN_PANEL,
                new WindowDimensions(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT), PATH_CSS_DARK_STYLING);
        StageController.getApplicationStage().setResizable(false);
    }

    public void main() {
        launch();
    }
}

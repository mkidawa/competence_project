package pl.awjkmkkk.view.fxml;

import pl.awjkmkkk.view.fxml.core.FxmlStageSetup;
import pl.awjkmkkk.view.fxml.core.WindowDimensions;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController extends FxmlStageSetup {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    private StageController() {
    }

    public static Stage getApplicationStage() {
        return FxmlStageSetup.getApplicationStage();
    }

    public static WindowDimensions getWindowDimensions() {
        return FxmlStageSetup.getWindowDimensions();
    }

    public static String getGlobalCssStyling() {
        return FxmlStageSetup.getGlobalCssStyling();
    }

    public static void setGlobalCssStyling(String globalCssStyling) {
        FxmlStageSetup.setGlobalCssStyling(globalCssStyling);
    }

    public static void buildStage(Stage stage, String filePath, String title,
                                  WindowDimensions dimensions, String cssFilePath) {
        try {
            FxmlStageSetup.buildStage(stage, filePath, title, dimensions, cssFilePath);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Building Error",
                    "Stage cannot be properly built", Alert.AlertType.ERROR);
        }
    }

    public static void loadStage(String filePath, String title) {
        try {
            FxmlStageSetup.loadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Loading Error",
                    "Stage cannot be properly loaded", Alert.AlertType.ERROR);
        }
    }

    public static void reloadStage(String filePath, String title) {
        try {
            FxmlStageSetup.reloadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Reloading Error",
                    "Stage cannot be properly reloaded", Alert.AlertType.ERROR);
        }
    }
}

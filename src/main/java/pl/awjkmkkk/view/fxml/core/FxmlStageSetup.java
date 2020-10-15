package pl.awjkmkkk.view.fxml.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlStageSetup {

    /*------------------------ FIELDS REGION ------------------------*/
    private static Stage applicationStage;
    private static WindowDimensions windowDimensions;
    private static String globalCssStyling;

    /*------------------------ METHODS REGION ------------------------*/
    protected FxmlStageSetup() {
    }

    protected static Stage getApplicationStage() {
        return applicationStage;
    }

    protected static void setApplicationStage(Stage applicationStage) {
        FxmlStageSetup.applicationStage = applicationStage;
    }

    protected static WindowDimensions getWindowDimensions() {
        return windowDimensions;
    }

    protected static void setWindowDimensions(WindowDimensions windowDimensions) {
        FxmlStageSetup.windowDimensions = windowDimensions;
    }

    protected static String getGlobalCssStyling() {
        return globalCssStyling;
    }

    protected static void setGlobalCssStyling(String globalCssStyling) {
        FxmlStageSetup.globalCssStyling = globalCssStyling;
    }

    /**
     * Method load pl.awjkmkkk.view.fxml file.
     */
    private static Parent loadFxml(String fxml) throws IOException {
        return new FXMLLoader(FxmlStageSetup.class.getResource(fxml)).load();
    }

    /**
     * Method prepare Stage by setting all required parameters.
     */
    private static void prepareStage(String filePath, String title,
                                     WindowDimensions dimensions) throws IOException {
        Scene scene = new Scene(loadFxml(filePath));
        if (globalCssStyling != null) {
            scene.getStylesheets().add(globalCssStyling);
        }

        applicationStage.setScene(scene);
        applicationStage.setTitle(title);
        applicationStage.setWidth(dimensions.getWidth());
        applicationStage.setHeight(dimensions.getHeight());
        applicationStage.show();
    }

    /**
     * Method load stage from scratch and set `applicationStage` - use on startup of application
     * stage is passed from start method from pl.awjkmkkk.Main class.
     */
    protected static void buildStage(Stage stage, String filePath,
                                     String title, WindowDimensions dimensions,
                                     String cssFilePath) throws IOException {
        setApplicationStage(stage);
        setWindowDimensions(dimensions);
        setGlobalCssStyling(cssFilePath);
        prepareStage(filePath, title, windowDimensions);
    }

    /**
     * Method load new stage and set `applicationStage` to a new one but leave the previous one
     * open.
     */
    protected static void loadStage(String filePath, String title) throws IOException {
        setApplicationStage(new Stage());
        prepareStage(filePath, title, windowDimensions);
    }

    /**
     * Method close previous stage and load new stage and set `applicationStage` to a new one.
     */
    protected static void reloadStage(String filePath, String title) throws IOException {
        applicationStage.close();
        loadStage(filePath, title);
    }
}

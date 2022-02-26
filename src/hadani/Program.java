package hadani;

import static hadani.Cesta.IKONA;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Program extends Application
{    
    private int[] s = new int[41];
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(Cesta.HlavníOkno);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image(IKONA));
        stage.setResizable(false);
        stage.show();
    }
}

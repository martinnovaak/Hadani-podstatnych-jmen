package ovladac;

import hadani.Cesta;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;

public class OvladačMain 
{   
    @FXML
    private ColorPicker barvaTlačítek;
    
    private boolean vlastníbarva = false;

    public void spustit(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(Cesta.HraOkno);
        Parent root = loader.load();
        
        if(vlastníbarva == true)
        {
            OvladačHry oh = loader.getController();
            oh.nastavBarvy(barvaTlačítek.getValue());
        }
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        
        String css = Cesta.CSS.toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public void výsledky(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(Cesta.VýsledkyOkno);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void slovník(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(Cesta.SlovníkOkno);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void vlastníBarva(ActionEvent event)
    {
        CheckBox checkbox = (CheckBox)event.getSource();
        vlastníbarva = checkbox.isSelected() == true;
    }
}

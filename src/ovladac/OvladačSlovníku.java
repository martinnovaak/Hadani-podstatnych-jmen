package ovladac;

import hadani.Cesta;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OvladačSlovníku implements Initializable
{
    @FXML
    private ListView listview;
    @FXML
    private TextField textfield;

    private ObservableList<String> list;
    private OvladačDatabáze ovladačDatabáze;
    
    private void načtiSlovník()
    {
        list = ovladačDatabáze.slovník();
        listview.setItems(list);
        //listview.scrollTo(1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ovladačDatabáze = new OvladačDatabáze();
        načtiSlovník();
    }
    
    public void zobrazZprávu(String hlavička, String text, boolean m)
    {
        
        Alert alert;
        if (m == true)
            alert = new Alert(AlertType.INFORMATION);
        else
            alert = new Alert(AlertType.ERROR);
        alert.setTitle("Dialog slovníku");
        alert.setHeaderText(hlavička);
        alert.setContentText(text);

        alert.showAndWait();
    }
    
    public void najdi() throws SQLException
    {
        String hlavička = "Hledání";
        String slovo = textfield.getText();
        if ("".equals(slovo))
            zobrazZprávu(hlavička,"Slovo nesmí být prázdné", false);
        boolean existuje = ovladačDatabáze.najdiSlovo(slovo);
        if (existuje == true)
        {
            zobrazZprávu(hlavička, "Hledané slovo: " + slovo + " se ve slovníku nachází.", true);
        }
        else
        {
            zobrazZprávu(hlavička, "Hledané slovo: " + slovo + " se ve slovníku nenachází.", false);
        }
    }
    
    public void přidej() throws SQLException
    {
        String hlavička = "Přidávání";
        String slovo = textfield.getText();
        if ("".equals(slovo))
            zobrazZprávu(hlavička, "Slovo nesmí být prázdné", false);
        boolean přidáno = ovladačDatabáze.přidejSlovoDoSlovníku(slovo);
        if (přidáno == true)
        {
            zobrazZprávu(hlavička, "Slovo: " + slovo + " bylo úspěšně přidáno do slovníku.", true);
            načtiSlovník();
        }
        else
        {
            zobrazZprávu(hlavička, "Hledané slovo: " + slovo + " nebylo přidáno do slovníku, protože se již v něm nachází.", false);
        }
    }
    
    public void smaž() throws SQLException 
    {
        String hlavička = "Mazání";
        String slovo = textfield.getText();
        if ("".equals(slovo))
            zobrazZprávu(hlavička, "Slovo nesmí být prázdné", false);
        boolean smazáno = ovladačDatabáze.smažSlovoZeSlovníku(slovo);
        if (smazáno == true)
        {
            zobrazZprávu(hlavička, "Slovo: " + slovo + " bylo úspěšně smazáno ze slovníku.", true);
            načtiSlovník();
        }
        else
        {
            zobrazZprávu(hlavička, "Hledané slovo: " + slovo + " nebylo smazáno ze slovníku, protože se v něm nenachází.", false);
        }
    }
    
    public void zpět(ActionEvent event) throws IOException, SQLException
    {
        ovladačDatabáze.odpojse();
        
        Parent root = FXMLLoader.load(Cesta.HlavníOkno);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

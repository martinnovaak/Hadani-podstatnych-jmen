package ovladac;

import hadani.Cesta;
import hadani.Hráč;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OvladačVýsledků implements Initializable
{
    @FXML
    private TableView<Hráč> tableview;
    @FXML
    private TableColumn<Hráč, String> tablecolumnJmeno;
    @FXML
    private TableColumn<Hráč, Integer> tablecolumnSkore;
    @FXML
    private Label labelSkore;
    @FXML
    private TextField textfield;
    @FXML 
    private Button buttonUloz;
    
    private ObservableList<Hráč> list;
    private OvladačDatabáze ovladačDatabáze;
    
    private void načtiTabulku()
    {
        list = ovladačDatabáze.výsledky();
        tableview.setItems(list);  
    }
    
    public void ulož() throws SQLException
    {
        String jméno =  textfield.getText();
        int skóre = parseInt(labelSkore.getText());
        if (!"".equals(jméno))
        {
            ovladačDatabáze.přidejHráčeDoVýsledků(jméno, skóre);
            načtiTabulku();
            nastavTlačítka(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ovladačDatabáze = new OvladačDatabáze();
        
        tablecolumnJmeno.setCellValueFactory(new PropertyValueFactory<>("jméno"));
        tablecolumnSkore.setCellValueFactory(new PropertyValueFactory<>("skóre"));    
        
        načtiTabulku(); 
        nastavTlačítka(true);
    }
    
    private void nastavTlačítka(boolean zablokuj)
    {
        if (zablokuj == true)
        {
            buttonUloz.setDisable(true);
            textfield.setDisable(true);
        }
        else
        {
            buttonUloz.setDisable(false);
            textfield.setDisable(false);
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
    
    public void nastavVýsledek(int výsledek)
    {
        labelSkore.setText(Integer.toString(výsledek));
        nastavTlačítka(false);
    }
}

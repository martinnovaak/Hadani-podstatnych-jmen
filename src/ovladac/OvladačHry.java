package ovladac;

import hadani.Cesta;
import hadani.Slovo;
import hadani.Řešič;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OvladačHry implements Initializable
{
    @FXML
    private Button btn1;
    @FXML
    private Button btn2; 
    @FXML
    private Button btn3; 
    @FXML
    private Button btn4; 
    @FXML
    private Button btn5;
    @FXML
    private Button btn6; 
    @FXML
    private Button btn7; 
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn10; 
    @FXML
    private TextField textfield;
    @FXML
    private ListView listview;
    @FXML
    private TableView<Slovo> tableview;
    @FXML
    private TableColumn<Slovo, String> sloupecSlova;
    @FXML
    private TableColumn<Slovo, Integer> sloupecBody;
    @FXML
    private Label labelbody;
    @FXML
    private MenuItem menuitemkonec;
    
    private int body = 0;
    private OvladačDatabáze ovladačDatabáze;
    private ArrayList<Button> list;
    private ArrayList<Button> listblok;
    private ObservableList<Slovo> spravnylist;
    private ObservableList<String> spatnylist;
    //private final String abeceda = "aábcčdďeéěfghiíjklmnňoópqrřsštťuúůvwxyýzž";
    private String písmena;
    private final String častésamohlásky = "aeiou";
    private final String častésouhlásky = "bcdhjklmnprstvz";
    private final String zbylésamohlásky = "áéěíóúůyý";
    private final String zbylésouhlásky = "čďfgňqřšťwxž";

    private OvladačSlov os;
    
    private void vylosuj()
    {
        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            if(i < 3)
                list.get(i).setText(String.valueOf(častésamohlásky.charAt(random.nextInt(častésamohlásky.length()))));
            else if (i == 3)
                list.get(i).setText(String.valueOf(zbylésamohlásky.charAt(random.nextInt(zbylésamohlásky.length()))));
            else if (i > 3 && i < 9)
                list.get(i).setText(String.valueOf(častésouhlásky.charAt(random.nextInt(častésouhlásky.length()))));
            else
                list.get(i).setText(String.valueOf(zbylésouhlásky.charAt(random.nextInt(zbylésouhlásky.length()))));
            if(i != 0)
                písmena += list.get(i).getText();
            else
                písmena = list.get(i).getText();
        }
    }
    
    private void vyčisti()
    {
        while(!textfield.getText().equals("")) smaž(null);
    }
    
    public void vygenerujZnovu() throws IOException
    {
        vylosuj();
        vyčisti();
        spravnylist.clear();
        spatnylist.clear();
        tableview.setItems(spravnylist);
        listview.getItems().clear();
        labelbody.setText("0");
        body = 0;
        
        if (os != null)
        {
            ObservableList<String> možnéSlova = spočtiMožnosti();
            os.nastav(možnéSlova, možnéSlova.size());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ovladačDatabáze = new OvladačDatabáze();
        
        spravnylist = FXCollections.observableArrayList();
        spatnylist = FXCollections.observableArrayList();
        
        sloupecSlova.setCellValueFactory(new PropertyValueFactory<>("slovo"));
        sloupecBody.setCellValueFactory(new PropertyValueFactory<>("body"));
        
        
        list = new ArrayList<>();
        listblok = new ArrayList<>();
        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        list.add(btn4);
        list.add(btn5);
        list.add(btn6);
        list.add(btn7);
        list.add(btn8);
        list.add(btn9);
        list.add(btn10);
        
        vylosuj();
                
           
    }
    
    public void zmáčknuto(ActionEvent event)
    {
        Button tlačítko = (Button) event.getSource();
        textfield.setText(textfield.getText()+tlačítko.getText());
        listblok.add(tlačítko);
        tlačítko.setDisable(true);
    }
    
    public void smaž(ActionEvent event)
    {
        if (!listblok.isEmpty())
        {
            Button tlačítko = (Button) listblok.remove(listblok.size()-1);
            tlačítko.setDisable(false);
            String text = textfield.getText();
            StringBuilder buffer = new StringBuilder(text);
            buffer.deleteCharAt(text.length() - 1);
            text = buffer.toString();
            textfield.setText(text);
        }
    }
    
    public boolean spatneObsahuje(String slovo)
    {
        //ObservableList<String> spatnylist = listview.getItems();
        for(int i = 0; i < spatnylist.size(); i++)
        {
            if (spatnylist.get(i).equals(slovo))
                return true;
        }
        return false;
    }
    
    public boolean spravneObsahuje(String slovo)
    {
        //ObservableList<Slovo> spravnylist = tableview.getItems();
        for(int i = 0; i < spravnylist.size(); i++)
        {
            if (spravnylist.get(i).getSlovo().equals(slovo))
                return true;
        }
        return false;
    }
    
    public void enter(ActionEvent event) throws SQLException
    {
        String slovo = textfield.getText();
        boolean nalezeno = ovladačDatabáze.najdiSlovo(slovo);
        if (nalezeno == true)
        {
            if (spravneObsahuje(slovo) == false)
            {
                int hodnota = slovo.length() * 2;
                body += hodnota;
                Slovo text = new Slovo(slovo, hodnota);
                spravnylist.add(text);
                //tableview.getItems().add(text);
                tableview.setItems(spravnylist);
                labelbody.setText(Integer.toString(body));
            }
        }
        else
        {
            if (spatneObsahuje(slovo) == false)
            {
                spatnylist.add(slovo);
                listview.getItems().add(slovo);
            }
        }
        vyčisti();
    }
    
    public void konec(ActionEvent event) throws IOException, SQLException
    {
        ovladačDatabáze.odpojse();
        
        FXMLLoader loader = new FXMLLoader(Cesta.VýsledkyOkno);
        Parent root = loader.load();
        
        OvladačVýsledků ov = loader.getController();
        ov.nastavVýsledek(body);
        
        
        Stage stage = (Stage)menuitemkonec.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();        
    }
    
    private static String toHexString(Color color) 
    {
        int r = ((int) Math.round(color.getRed()     * 255)) << 24;
        int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
        int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
      }
    
    public void nastavBarvy(Color barva)
    {
        for (int i = 0; i < 10 ; i++)
            list.get(i).setStyle("-fx-background-color: " + toHexString(barva) +"; ");
    }
    
    public void možnosti(ActionEvent event) throws IOException
    {
        //
        //System.out.println(písmena);
        ObservableList<String> možnéSlova = spočtiMožnosti();
        int count = možnéSlova.size();
        
        FXMLLoader loader = new FXMLLoader(Cesta.MožnostiOkno);
        Parent root = loader.load();
        
        os = loader.getController();
        os.nastav(možnéSlova, count);
        
        Stage stage = new Stage();
        stage.setTitle("Možnosti");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    private ObservableList<String> spočtiMožnosti()
    {
        Řešič řešič = new Řešič(písmena);
        ObservableList<String> slovaVDatabázi = ovladačDatabáze.slovník();
        ObservableList<String> možnéSlova = FXCollections.observableArrayList();
        for (int i = 0; i < slovaVDatabázi.size(); i++)
            if(řešič.porovnej(slovaVDatabázi.get(i)) == true)
            {
                //System.out.println(slovaVDatabázi.get(i));
                možnéSlova.add(slovaVDatabázi.get(i));
            }
        // 
        return možnéSlova;
    }
}

package hadani;

import databaze.Databáze;
import java.io.InputStream;
import java.net.URL;
import okno.Okno;

public class Cesta 
{
    public final static URL HlavníOkno = Okno.class.getResource("main.fxml");
    public final static URL HraOkno = Okno.class.getResource("game.fxml");
    public final static URL VýsledkyOkno = Okno.class.getResource("results.fxml");
    public final static URL SlovníkOkno = Okno.class.getResource("dictionary.fxml");
    public final static URL MožnostiOkno = Okno.class.getResource("words.fxml");
    
    public final static URL CSS = Okno.class.getResource("tlacitka.css");
    
    public final static InputStream IKONA = Okno.class.getResourceAsStream("ikona.jpg");
    public final static String Databáze = "jdbc:sqlite:" + Databáze.class.getResource("slovnik.db").getPath();
}

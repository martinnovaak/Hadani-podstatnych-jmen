package ovladac;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class OvladačSlov 
{
    @FXML
    private ListView listview;
    @FXML
    private Label label;
    
    public void nastav(ObservableList<String> list, int početSlov)
    {
        this.listview.setItems(list);
        label.setText(Integer.toString(početSlov));
    }
}

package ovladac;

import hadani.Cesta;
import hadani.Hráč;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OvladačDatabáze
{
    private static Connection con = null;
    
    public OvladačDatabáze()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(Cesta.Databáze);
        }
        catch(Exception e)
        {
            con = null;
        }
    }
    
    public void odpojse() throws SQLException
    {
        if (con == null) return;
        con.close();
    }
    
    public boolean najdiSlovo(String slovo) throws SQLException
    {
        if (con == null)
            return false;
        String find = "SELECT * FROM SLOVNIK WHERE slovo = '" + slovo + "'";
        Statement state = con.createStatement();
        ResultSet out = state.executeQuery(find);
        return out.next();
    }
    
    public boolean přidejSlovoDoSlovníku(String slovo) throws SQLException
    {
        if (con == null)
            return false;
        //Pokud už ve slovníku je nic nepřidávej
        if (najdiSlovo(slovo) == true) return false;
            
        //Jinak ho přidej do slovníku
        String text = "INSERT INTO slovnik VALUES ('" + slovo + "');";
        Statement state = con.createStatement();
        state.execute(text);
        return true;
    }
    
    public boolean smažSlovoZeSlovníku(String slovo) throws SQLException
    {
        if (con == null)
            return false;
        //Pokud ve slovníku není nemůže být smazáno
        if (najdiSlovo(slovo) == false) return false;
        
        String text = "DELETE FROM slovnik WHERE slovo='" + slovo +"'";
        Statement state = con.createStatement();
        state.execute(text);
        return true;
    }
    
    public void přidejHráčeDoVýsledků(String jméno, int skóre) throws SQLException
    {
        if (con == null)
            return ;
        //Jména se mohou opakovat
        String text = "INSERT INTO vysledky VALUES ('" + jméno + "', '" + skóre + "');";
        Statement state = con.createStatement();
        state.execute(text);
    }
    
    public ObservableList<String> slovník()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        if (con == null)
            return list;
        String text = "SELECT * FROM slovnik";
        try
        {
            Statement statement = con.createStatement();
            ResultSet out = statement.executeQuery(text);
            while(out.next())
            {
                list.add(out.getString(1));
            }
        }
        catch(Exception e)
        {
        }
        return list;
    }
    
    public ObservableList<Hráč> výsledky() 
    {
        ObservableList<Hráč> list = FXCollections.observableArrayList();
        if (con == null)
            return list;
        String text = "SELECT * FROM vysledky ORDER BY skore DESC";
        try
        {
            Statement statement = con.createStatement();
            ResultSet out = statement.executeQuery(text);
            while(out.next())
            {
                list.add(new Hráč(out.getString(1),Integer.parseInt(out.getString(2))));
            }
        }
        catch(Exception e)
        {
        }
        return list;
    }
}

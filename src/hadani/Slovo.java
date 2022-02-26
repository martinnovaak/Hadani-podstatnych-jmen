package hadani;

public class Slovo 
{
    private String slovo;
    private int body;

    public Slovo(String slovo, int body) 
    {
        this.slovo = slovo;
        this.body = body;
    }

    public String getSlovo() 
    {
        return slovo;
    }

    public void setSlovo(String slovo) 
    {
        this.slovo = slovo;
    }

    public int getBody() 
    {
        return body;
    }

    public void setBody(int body) 
    {
        this.body = body;
    }
}

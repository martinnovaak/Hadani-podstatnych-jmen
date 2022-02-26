package hadani;

public class Hráč {
    private String jméno;
    private int skóre;

    public Hráč(String jméno, int skóre) 
    {
        this.jméno = jméno;
        this.skóre = skóre;
    }

    public String getJméno() 
    {
        return jméno;
    }

    public void setJméno(String jméno) 
    {
        this.jméno = jméno;
    }

    public int getSkóre() 
    {
        return skóre;
    }

    public void setSkóre(int skóre) 
    {
        this.skóre = skóre;
    }
}

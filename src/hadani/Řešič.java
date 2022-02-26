package hadani;

import java.util.Arrays;

public class Řešič 
{
    private final int[] slovo = new int[41];
    private int[] dbslovo = new int[41];
    
    private void setWord(int[] pole, String word)
    {
        for (int i = 0; i < word.length(); i++)
        {
            if("a".codePointAt(0) <= word.codePointAt(i) && word.codePointAt(i) <= "z".codePointAt(0))
            {
                pole[word.codePointAt(i) - 97]++;          
            }
            else if(word.charAt(i) == 'á')
            {
                pole[26]++;
            }
            else if(word.charAt(i) == 'č')
            {
                pole[27]++;
            }
            else if(word.charAt(i) == 'ď')
            {
                pole[28]++;
            }
            else if(word.charAt(i) == 'é')
            {
                pole[29]++;
            }
            else if(word.charAt(i) == 'ě')
            {
                pole[30]++;
            }
            else if(word.charAt(i) == 'í')
            {
                pole[31]++;
            }
            else if(word.charAt(i) == 'ň')
            {
                pole[32]++;
            }
            else if(word.charAt(i) == 'ó')
            {
                pole[33]++;
            }
            else if(word.charAt(i) == 'ř')
            {
                pole[34]++;
            }
            else if(word.charAt(i) == 'š')
            {
                pole[35]++;
            }
            else if(word.charAt(i) == 'ť')
            {
                pole[36]++;
            }
            else if(word.charAt(i) == 'ú')
            {
                pole[37]++;
            }
            else if(word.charAt(i) == 'ů')
            {
                pole[38]++;
            }
            else if(word.charAt(i) == 'ý')
            {
                pole[39]++;
            }
            else if(word.charAt(i) == 'ž')
            {
                pole[40]++;
            }
        }
    }
    
    public Řešič(String word)
    {
        setWord(slovo, word);
    }
    
    public void countletters(String word)
    {
        dbslovo = new int[41];
        //"aábcčdďeéěfghiíjklmnňoópqrřsštťuúůvwxyýzž";
        setWord(dbslovo, word);
    }
    
    public boolean porovnej(String word)
    {
        countletters(word);
        if(Arrays.equals(slovo, dbslovo) == true)
            return true;
        for (int i = 0; i < 41; i++)
        {
            if (slovo[i] < dbslovo[i])
                return false;
        }
        return true;
    }
}

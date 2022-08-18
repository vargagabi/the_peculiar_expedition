package Targyak;

import java.util.ArrayList;

/**
 * Kincset megvalosito osztaly.
 * Targyak gyerekosztalya.
 */
public class Kincs extends Targyak {

    public  ArrayList<String> kincsnevek;


    public Kincs(String nev,int ertek){

        super(nev,1,ertek);


    }

    /**
     * Feltolti a kincsnevek listat
     */
    public Kincs(){
        super();
        kincsnevek = new ArrayList<>();
        kincsnevek.add("Aranyszobor");
        kincsnevek.add("Viszga megoldasok");
        kincsnevek.add("Gyemant szem");
        kincsnevek.add("Osi konyv");
        kincsnevek.add("Lada arany");
        kincsnevek.add("Gyemant koponya");
        kincsnevek.add("Rejtejes vaza");
        kincsnevek.add("Rubint");
        kincsnevek.add("Ezust kard");
    }
    public String toString(){
        return super.toString() + " Ez egy ertekes kincs, amit eladhatsz penzert vagy muzeumnak adomanyozhatok hirnevert.";
    }

    /**
     * Kivalaszt egy random nevet a kincsnevek listabol, majd eltavolitja azt
     * Ha mar nincs tobb nev akkor arany lesz a kivalasztott nev mint default
     * @return a kivalasztott nevet visszaadja
     */
    public  String generateName(){
        if (kincsnevek.size() == 0) return "arany";
        int random = (int)((Math.random()*100)% kincsnevek.size());
        String nev = kincsnevek.get(random);
        kincsnevek.remove(random);
        return nev;
    }

}

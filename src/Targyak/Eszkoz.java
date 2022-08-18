package Targyak;

/**
 * Eszkozok osztalya.
 * Targyak gyerekosztalya.
 */
public class Eszkoz extends Targyak {



    public Eszkoz(String nev,int darabszam, int ertek){
        super(nev,darabszam,ertek);
    }
    public Eszkoz(){
        super();
    }

    public String toString(){
        return super.toString() +" Minden eszkoznek meg van a maga haszna.";
    }
}

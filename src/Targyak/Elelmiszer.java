package Targyak;

/**
 * Elelmiszer adatait tarolja el, ami az energiaertek.
 * Targyak gyerekosztalya
 */
public class Elelmiszer extends Targyak {
    private final int energiaErtek;

    //getterek
    public int getEnergiaErtek() {
        return energiaErtek;
    }

    public Elelmiszer(String nev, int darabszam, int ertek, int energiaErtek){
        super(nev,darabszam,ertek);
        this.energiaErtek=energiaErtek;
    }

    public Elelmiszer(){
        super();
        this.energiaErtek = 0;
    }

    public String toString(){
        return super.toString() + " Energiaerteke " + energiaErtek;
    }

}

package Targyak;

/**
 * Targyakat/itemeket megvalsokito osztaly. Ebbol szarmaznak a kulonbozo targyak, magat az osztalyt nem
 * peldanyositjuk, csak az inventory listajaban hasznaljuk, hogy kulonbozo tipusu targyakat is el lehessen
 * tarolni.
 */
public class Targyak {
    private String nev;
    private int ertek;
    private int darabszam;
    //protected int id;

    public Targyak(String nev, int darabszam, int ertek){
        this.nev = nev;
        this.ertek = ertek;
        this.darabszam = darabszam;
    }

    public Targyak(){
        this.nev ="";
        this.ertek = 0;
        this.darabszam =0;
    }

    public String getNev() {
        return nev;
    }
    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getErtek() {
        return ertek;
    }
    public void setErtek(int ertek) {
        this.ertek = ertek;
    }

    public int getDarabszam() {
        return darabszam;
    }
    public void setDarabszam(int darabszam) {
        if (this.darabszam + darabszam < 0) this.darabszam=0;
        else this.darabszam+=darabszam;
    }
    public void makeDarabszam(int darabszam){
        this.darabszam = darabszam;
    }
    public void darabszamCsokkentes(int mennyivel){
        if (darabszam-mennyivel >= 0){
            this.darabszam-=mennyivel;
        }
    }
    public String toString(){
        return nev+"-bol " + darabszam + " van nalad, erteke " + ertek + " darabonkent.";
    }
}

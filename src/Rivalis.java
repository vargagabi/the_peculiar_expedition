package com.example.thepeculiarexpedition;

import java.util.ArrayList;
import java.util.List;
/**
 * A rivalisokat megvaloskito osztaly. Itt taroljuk egy adott rivalis nevet es innen sorsolunk ki egy random
 * nevet maganak a rivalisnak.
 */
public class Rivalis {
    private String nev;
    private int hirnev;
    /**
     * A rivalisok lehetseges nevei
     */
    private static List<String> nevek = new ArrayList<>();

    public Rivalis(String nev){
        this.nev = nev;
        this.hirnev = 0;
    }


    //getterek
    public int getHirnev(){
        return hirnev;
    }
    public String getNev() {
        return nev;
    }

    /**
     * Noveljuk a hirnevet a parameterben kapott mennyiseggel
     * @param mennyivel ennyivel kell a hirnevet novelni
     * @see Main
     */
    public void hirnevNovel(int mennyivel){
        this.hirnev+=mennyivel;
    }

    /**
     * Kivalaszt egy randem nevet a rivalisnak
     * @return a kivalasztott nevet adja vissza
     * @see Main
     */
    public static String randomNev(){
        if (nevek.size() == 0) return "Bob";
        int random = (int)((Math.random()*100)% nevek.size());
        String nev = nevek.get(random);
        nevek.remove(random);
        return nev;
    }

    /**
     * feltolti a nevek listajat random nevekkel
     * @see Main
     */
    public static void nevFeltoltes(){
        nevek.add("Keencut Dogard");
        nevek.add("Riversteel Leenan");
        nevek.add("Lightningtrapper Thoran");
        nevek.add("Embercleaver Cen");
        nevek.add("Wyvernward Beorthferum");
        nevek.add("Snowbinder Evercas");
        nevek.add("Boisgevau Leofuguthconda");
        nevek.add("Alirel Eliacaro");
        nevek.add("Kheirdun Buhrod");
        nevek.add("Bhihner Dhossen");
        nevek.add("Dindad Softsword");
        nevek.add("Nee Havenore");
        nevek.add("Gabol Kodz");
        nevek.add("Vel Duknask");
        nevek.add("Vograr Icegazer");
        nevek.add("Eth Mildfallow");
        nevek.add("Ren-Dihkeh Fichuldrahk");
        nevek.add("Tud-Vi Rankhahr");
        nevek.add("Brenresdas Chyatralzelmya");
        nevek.add("Shooldour Zinyalba");
        nevek.add("Thiey Shiang");
        nevek.add("Muy Pue");

    }

}

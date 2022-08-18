package Jatekos;

import java.util.Scanner;

/**
 * Csapattarsakat megvalolito class. Itt taroljuk az egyes csapattarsak adatait
 * mint pl.: nev, serult-e vagy fuggo-e
 */
public  class Csapattars {
    /**
     * Jatekos.Csapattars neve
     */
    private String megnevezes;
    /**
     * Whiskeyfuggo-e, ha nem -1 az erteke
     */
    private int whiskeyFuggo;
    /**
     * Kabitoszerfuggo-e, ha nem -1 az erteke
     */
    private int kabitoszerFuggo;
    /**
     * serult-e a csapattag
     */
    private boolean serult;

    //Getterek
    public String getMegnevezes() {
        return megnevezes;
    }
    public boolean isSerult() {
        return serult;
    }
    public int getKabitoszerFuggo() {
        return kabitoszerFuggo;
    }
    public int getWhiskeyFuggo() {
        return whiskeyFuggo;
    }

    //Setterek
    public void setSerult(boolean serult) {
        this.serult = serult;
    }
    /**
     * Fuggove teszi a csapattarsat
     * @see #setKabitoszerFuggo(int)
     * @param szam ha 0 akkor fuggo lesz a csapattars, ha 1 akkor egyel noveli a szamlalot
     */
    public void setWhiskeyFuggo(int szam) {
        if (szam == 0) this.whiskeyFuggo =0;
        else if (whiskeyFuggo != -1 && szam == 1) whiskeyFuggo++;

    }
    /**
     * Fuggove teszi a csapattarsat
     * @param szam ha 0 akkor foggo lesz a csapattars, ha 1 akkor egyel noveli a szamlalot
     * @see #setWhiskeyFuggo(int)
     */
    public void setKabitoszerFuggo(int szam) {
        if (szam == 0) kabitoszerFuggo = 0;
        else if (kabitoszerFuggo != -1 && szam ==1)kabitoszerFuggo++;

    }

    public Csapattars(String megnevezes){
        this.megnevezes = megnevezes;
        whiskeyFuggo = -1;
        kabitoszerFuggo = -1;
        serult = false;
    }

    /**
     * Jatekos.Csapattars felvetel
     * @param felfedezo a felfedezo osztalyhoz igy ferunk hozze
     * @param hely hol vagyunk jelenleg, ha faluban akkor falusi csapattarsakat
     *             ajanl amugy meg a kuldetest elejen levoket
     */
    public static void csapattarsFelvetel(Felfedezo felfedezo, String hely){
        if (felfedezo.getCsapattarsakSzama() == 3) {
            System.out.println("Nem lehet tobb, mint 3 csapattarsad!");
            return;

        }
        String kezdoSzoveg="Valassz egy csapattarsat: \n" +
                            "1: Kereskedo: mindent kicsivel olcsobban vehetsz es dragabban adhatsz el\n"
                            +"2: Katona: Whiskey +20% energia\n"+
                            "3: Szamar: +2 inventory slot\n";


        String faluSzoveg="Valaszthatsz egy csapattarsat: \n"+
                            "1: Felderito: +1 latokor\n"+
                            "2: Saman: kabitoszer +20% energia\n"+
                            "3: Bolcs: +3 alapviszony az uj terkepen\n";

        String nev1 = "";
        String nev2 = "";
        String nev3 = "";
        if (hely.equals("falu")){
            nev1 = "felderito";
            nev2 = "saman";
            nev3 = "bolcs";
            System.out.println(faluSzoveg);
        }
        else if (hely.equals("kezdes")){
            nev1 = "kereskedo";
            nev2 = "katona";
            nev3 = "szamar";
            System.out.println(kezdoSzoveg);
        }
        int input = input();
        switch (input){
            case 1: felfedezo.csapattarsHozzaad(new Csapattars(nev1));
                break;
            case 2: felfedezo.csapattarsHozzaad(new Csapattars(nev2));
                break;
            case 3: felfedezo.csapattarsHozzaad(new Csapattars(nev3));
        }

    }

    /**
     * Szam beolvasasa 1 es 3 kozott
     * @see #csapattarsFelvetel(Felfedezo, String)
     * @return a beolvasott szamot
     */
    public static int input(){
        Scanner sc = new Scanner(System.in);
        int input =0;
        while(true){
            try{
                input = sc.nextInt();
            }
            catch (Exception e){
                System.out.println("Nem szamat adtal meg");
                continue;
            }
            if (input <=0 || input >3) {
                System.out.println("Nem megfelelo szam");
                continue;
            }
            return input;
        }
    }

}

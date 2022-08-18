package Jatekos;

import Targyak.Elelmiszer;

/**
 * A felfedezot megvalosito osztaly. Itt taroljuk a felfedezo adatait, a csapattarsait stb...
 */
public class Felfedezo {
    private String nev;
    private double energia;
    private int arany;
    private int hirnev;
    private static double mozgasKoltseg;
    private Csapattars[] csapattagok;
    private int csapattarsakSzama;
    private int viszony;
    private int sikeresKuldetesek;
    /**
     * Az utolso elfogyasztott etel, a fuggosegek kezeleseben segit
     */
    private String utolsoEtel;
    /**
     * A jatekos fuggosegi szintjo (hany napja nem ivott whikeyt)
     * -1 ha nem fuggo amugy meg 0 vagy nagyobb
     */
    private int whiskeyFuggo;
    /**
     * A jatekos fuggosegi szintjo (hany napja nem hasznalt kabitoszert)
     * -1 ha nem fuggo amugy meg 0 vagy nagyobb
     */
    private int kabitoszerFuggo;
    /**
     * Mennyivel tobb targy van az inventoryban pl.: ha nincs szamar es 9 slot foglalt akkor az erteke 0.2
     */
    private  double inventoryPlusz;
    /**
     * Eladasoknal es vasarlasnal a bonusz ha van kereskedo csapattars
     */
    private double kereskedoBonusz;
    /**
     * A whiskey +20% energat ad ha van csapattars
     */
    private double katonaBonusz;
    /**
     * +1 latokor ha van felderito csapattars
     */
    private int felderitoBonusz;
    /**
     * A kabitoszer +20% energia ha ven saman csapattars
     */
    private double samanBonusz;
    /**
     * Ha van szamar akkor a maximum inventory slotok kettovel no
     */
    private boolean vanSzamar;


    //Getterek
    public double getEnergia() {
        return energia;
    }
    public int getArany() {
        return arany;
    }
    public int getHirnev() {
        return hirnev;
    }
    public int getViszony(){return viszony;}
    /**
     * Mozgaskoltseget adjo vissz
     * @return alap mozgaskoltseg + mennyivel tobb slot van tele a megengedettnel
     */
    public double getMozgasKoltseg() {
        return mozgasKoltseg+inventoryPlusz;
    }
    public int getSikeresKuldetesek() {
        return sikeresKuldetesek;
    }
    public int getCsapattarsakSzama() {
        return csapattarsakSzama;
    }
    public Csapattars[] getCsapattagok() {
        return csapattagok;
    }
    public double getKereskedoBonusz() {
        return kereskedoBonusz;
    }
    public int  getFelderitoBonusz(){return felderitoBonusz;}
    //Setterek

    public void setViszony(int viszony){
        if(viszony>0)this.viszony=viszony;
        else this.viszony =0;
    }

    /**
     * Beallitja mennyi az item tobblet a megengedettnel ez eszerint szamolja mennyivel tobb a mozgaskoltseg
     * @see #getMozgasKoltseg() 
     * @param inventoryPlusz osszesen hany slot foglalt, ebbol ki lehet szamolni mennyit kell a mozgaskoltseghez adni
     */
    public void setInventoryPlusz(int inventoryPlusz) {
        if (vanSzamar){
            if (inventoryPlusz> 10) this.inventoryPlusz=(inventoryPlusz-10)*0.2;
            else this.inventoryPlusz=0;
        }
        else if (inventoryPlusz>8) this.inventoryPlusz=(inventoryPlusz-8)*0.2;
        else this.inventoryPlusz=0;
    }

    /**
     * Hozzaad vagy kivon az energiabol
     * @param energia mennyit kell kivonni (pl.: -20) vagy hozzaadni (pl.: 20) az energiahoz
     */
    public void setEnergia(double energia) {
        if (energia>0){
            if (this.energia+energia <=100) this.energia+=energia;
            else this.energia = 100;
        }
        else if (energia<0){
            if (this.energia+energia >=0) this.energia+=energia;
            else this.energia = 0;
        }
    }

    /**
     * A whiskey fuggoseget noveli
     * @param szam ha 0 akkor fuggove tesz, ha 1 akkor 1 el noveli a fuggoseget
     */
    public void setWhiskeyFuggo(int szam){
        if (szam == 0) this.whiskeyFuggo =0;
        else if (whiskeyFuggo != -1 && szam == 1) whiskeyFuggo++;
    }
    /**
     * A kakitoszer fuggoseget noveli
     * @param szam ha 0 akkor fuggove tesz, ha 1 akkor 1 el noveli a fuggoseget
     */
    public void setKabitoszerFuggo(int szam){
        if (szam == 0) kabitoszerFuggo = 0;
        else if (szam == 1 && kabitoszerFuggo != -1)kabitoszerFuggo++;

    }
    public void setHirnev(int hirnev){
        this.hirnev = hirnev;
    }
    public void setSikeresKuldetesek(int sikeresKuldetesek){
        this.sikeresKuldetesek = sikeresKuldetesek;
    }
    public void setArany(int arany){this.arany = arany;}

    /**
     * Az osztaly konstruktora
     * @param nev a felfedezo neve, ami nem lenyeges jelenleg
     */
    public Felfedezo(String nev){
        if (nev != null) this.nev = nev;
        else System.err.println("hiba: nincs nev");
        energia = 100;
        arany = 0;
        hirnev = 0;
        mozgasKoltseg = 1;
        viszony = 3;
        csapattarsakSzama = 0;
        sikeresKuldetesek=0;
        csapattagok = new Csapattars[3];
        utolsoEtel = "semmi";
        whiskeyFuggo = kabitoszerFuggo = -1;
        kereskedoBonusz = 0;
        felderitoBonusz = 0;
    }

    /**
     * Jatekos.Felfedezo stringe asakitasa, toString felulirasa
     * @return felfedezo adataai Stringkent
     */
    public String toString(){
        StringBuilder szoveg = new StringBuilder();
        for (Csapattars cs : csapattagok){
            if (cs == null) continue;
            szoveg.append(cs.getMegnevezes()).append(", ");
        }
        return this.nev + " allapota: " +
                "\nEnergiaszint: " + String.format("%.2f",energia)+
                "\nArany; " + arany+
                "\nHirnev: " + hirnev+
                "\nViszony: " + viszony+
                "\nMozgaskoltseg: " + String.format("%.2f", getMozgasKoltseg())+
                "\nKabitoszer hiany: " +((kabitoszerFuggo == -1)?"Nem fuggo": kabitoszerFuggo +" lepese nem kapott")+
                "\nAlkoholista: " + ((whiskeyFuggo==-1)?"Nem alkoholista":whiskeyFuggo+ " legese nem ivott")+
                "\nCsapattagok: " +szoveg;

    }

    /**
     * Jatekos.Felfedezo energiajat noveljuk az elfogyasztott etel energiajaval
     * Ha whiskeyt vagy kabitoszert fogyasztott utoljara es megint azt fogyasztja akkor
     * meghivjuk a fuggoveTesz metodust.
     * Ha mar fuggo akkor meghivjuk a fuggosegetCsillapit metodust
     * @param etel az adott etel aminek az energiaszintjevelj noveljuk az energiat
     */
    public void eszik(Elelmiszer etel){
        if (etel.getNev().equals("whiskey")){
            setEnergia(etel.getEnergiaErtek()*(int)(20*katonaBonusz));
            if (utolsoEtel.equals(etel.getNev())){
                fuggoveTesz("whiskey");
            }
            fuggosegetCsillapit();

        }
        else if (etel.getNev().equals("kabitoszer")) {
            setEnergia(etel.getEnergiaErtek()*(int)(20*samanBonusz));
            if (utolsoEtel.equals(etel.getNev())){
                fuggoveTesz("Kabitoszer");
            }
            fuggosegetCsillapit();
        }
        else setEnergia(etel.getEnergiaErtek());
        System.out.println("Energia: +" +etel.getEnergiaErtek());
        System.out.println("Jelenlegi energia: " + String.format("%.2f", energia));
        utolsoEtel = etel.getNev();

    }
    /**
     * A metodus megnezi hogy 0-e az energia, ha igen akkor, ha vanak csapattarsak azok
     * 8%-os esellyel elhagyjak a csapatot.
     * Ha nincs csapattars akkor a felfedezo 8%-es esellyel veszit.
     * @return true ha a jatekos veszitett, amugy false
     */
    public boolean nincsEnergia(){
        if (energia == 0.0){
            if (csapattarsakSzama !=0){
                for (Csapattars cs : csapattagok){
                    if (cs == null) continue;
                    int random = (int)(Math.random()*100);
                    if (random <=8){
                        System.out.println(random+"---------------");
                        csapattarsKivesz(cs);
                        System.out.println(cs.getMegnevezes() + " elhagyta a csapatot");
                    }
                }
            }
            else{
                if ((int)(Math.random()*100) <=8){

                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 15%-os esellyel fuggove tesz valakit a csapatbol
     * @see #eszik(Elelmiszer) 
     * @param mitol whiskey vagy kabitoszer miatt lehet valaki fuggo
     */
    public void fuggoveTesz(String mitol){
            int random = (int)(Math.random()*100);
            //lesz e valaki fuggo
            if (random <=15){
                //ki lesz fuggo
                int csapatSzam = 1+csapattarsakSzama;
                random = (int)(Math.random()*100%csapatSzam);
                switch (random){
                    //felfedezo lesz fuggo
                    case 0:
                        if (mitol.equals("kabitoszer") && kabitoszerFuggo == -1) {
                            setKabitoszerFuggo(0);
                            System.out.println("Kabitoszerfuggo lettel");
                        }
                        else if(whiskeyFuggo == -1){
                            setWhiskeyFuggo(0);
                            System.out.println("Alkoholista lettel");
                        }
                        return;
                        //elso csapattars lesz fuggo es igy tovabb
                    case 1:
                        if (mitol.equals("kabitoszer") && csapattagok[0].getKabitoszerFuggo()==-1){
                            csapattagok[0].setKabitoszerFuggo(0);
                            System.out.println(csapattagok[0].getMegnevezes() + " fuggo lett");
                        }
                        else if(csapattagok[0].getWhiskeyFuggo()==-1) {
                            csapattagok[0].setWhiskeyFuggo(0);
                            System.out.println(csapattagok[0].getMegnevezes() + " alkoholista lett");
                        }

                        break;
                    case 2:
                        if (mitol.equals("kabitoszer") && csapattagok[1].getKabitoszerFuggo()==-1){
                            csapattagok[1].setKabitoszerFuggo(0);
                            System.out.println(csapattagok[1].getMegnevezes() + " fuggo lett");
                        }
                        else if(csapattagok[1].getWhiskeyFuggo()==-1){
                            csapattagok[1].setWhiskeyFuggo(0);
                            System.out.println(csapattagok[1].getMegnevezes() + " alkoholista lett");
                        }

                        break;
                    case 3:
                        if (mitol.equals("kabitoszer")&& csapattagok[2].getKabitoszerFuggo()==-1){
                            csapattagok[2].setKabitoszerFuggo(0);
                            System.out.println(csapattagok[2].getMegnevezes() + " fuggo lett");
                        }
                        else if(csapattagok[2].getWhiskeyFuggo()==-1){
                            csapattagok[2].setWhiskeyFuggo(0);
                            System.out.println(csapattagok[2].getMegnevezes() + " alkoholista lett");
                        }

                        break;
                }
            }



    }
    /**
     * Ha valaki fuggo akkor a 30 napos countert -1 ra allitjuk a setWhiskeyfuggo metudossal vagy a kabitoszeressel
     * Ha a whiskeyFuggo erteke -1 az azt jelenti hogy nem fuggo ez esetben nem csinal semmit
     * @see #eszik(Elelmiszer) 
     */
    public void fuggosegetCsillapit(){
        if (whiskeyFuggo != -1) setWhiskeyFuggo(-1);
        if (kabitoszerFuggo != -1) setKabitoszerFuggo(-1);
        if (csapattarsakSzama == 0) return;
        for (Csapattars cs : csapattagok){
            if (cs == null) continue;
            if (cs.getWhiskeyFuggo() != -1) cs.setWhiskeyFuggo(0);
            if (cs.getKabitoszerFuggo() != -1) cs.setKabitoszerFuggo(0);
        }
    }
    /**
     * Ha az utolso 30 lepeseben nem lett whiskey vagy kabitoszer fogyasztva akkor ha a felfedezo
     * egyedul van akkor 10%-os esellyel elhagyja a csapatot
     * Ha van csapattars akkor mindegyik 10%-os esellyel elhagyja a csapatot
     * @return true ha a jatekos veszitett false ha nem
     */
    public boolean fuggosegNagyobbMint30(){
        if (csapattarsakSzama == 0){
            if (whiskeyFuggo > 30 ||  kabitoszerFuggo > 30) {
                int random = (int) (Math.random() * 100);
                if (random <= 10) {
                    return true;
                }
            }
        }
        else{
            for (Csapattars cs : csapattagok){
                if (cs == null) continue;
                if (cs.getKabitoszerFuggo() > 30 || cs.getWhiskeyFuggo() > 30){
                    int random  = (int)(Math.random()*100);
                    if (random <= 10){
                        csapattarsKivesz(cs);
                        return false;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Jatekos.Csapattars felvetele
     * @param cs a csapattars akit felveszunk
     */
    public void csapattarsHozzaad(Csapattars cs){
        for (int i = 0; i < csapattagok.length; i++){
            if (csapattagok[i] == null){
                csapattagok[i] = cs;
                arany-=150;
                csapattarsakSzama++;
                mozgasKoltseg+=0.15;
                if (cs.getMegnevezes().equals("kereskedo")) kereskedoBonusz = 0.15;
                else if (cs.getMegnevezes().equals("katona")) katonaBonusz = 1.2;
                else if (cs.getMegnevezes().equals("szamar"))   vanSzamar=true;
                else if (cs.getMegnevezes().equals("felderito")) felderitoBonusz = 1;
                else if (cs.getMegnevezes().equals("saman")) samanBonusz = 1.2;
                else if (cs.getMegnevezes().equals("bolcs")) viszony+=3;
                return;
            }
        }
    }
    /**
     * Jatekos.Csapattars kivetele a csapatbol
     * @param cs kit veszunk ki
     */
    public void csapattarsKivesz(Csapattars cs){
        if (cs.getMegnevezes().equals("kereskedo")) kereskedoBonusz = 0;
        else if (cs.getMegnevezes().equals("katona")) katonaBonusz = 1;
        else if (cs.getMegnevezes().equals("szamar"))    vanSzamar=false;
        else if (cs.getMegnevezes().equals("felderito")) felderitoBonusz = 0;
        else if (cs.getMegnevezes().equals("saman")) samanBonusz = 1;
        else if (cs.getMegnevezes().equals("bolcs")) viszony-=3;
        mozgasKoltseg-=0.15;
        csapattarsakSzama--;
        for (int i = 0; i < csapattagok.length;i++){
            if (csapattagok[i] !=null){
                if (csapattagok[i].getMegnevezes().equals(cs.getMegnevezes())){
                    csapattagok[i]=null;
                    return;
                }

            }
        }
    }
    /**
     * Egy csapattars leserul
     * @param index hanyadik csapattars serul le
     * @return ki serult le
     */
    public String csapattarsLeserul(int index){
        csapattagok[index].setSerult(true);
        return csapattagok[index].getMegnevezes();
    }
    /**
     * Ha van serult csapattars legesenkent 5% esellyel elhagyja a csapatot
     */
    public void vanSerultCsapattars() {
        for (Csapattars cs : csapattagok){
            if (cs != null){
                if (cs.isSerult()){
                    int random = (int)(Math.random()*100);
                    if (random <= 5)
                    {
                        System.out.println(cs.getMegnevezes() + " elhagyta a csapatot mert leserult");
                        csapattarsKivesz(cs);

                    }

                }
            }
        }

    }
    /**
     * Minden csapattag meggyogyul
     */
    public void csapattarsGyogyitas(){
        for (Csapattars cs :csapattagok){
            if (cs != null){
                if (cs.isSerult()){
                    cs.setSerult(false);
                }
            }
        }
    }

}

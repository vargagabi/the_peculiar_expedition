import Jatekos.Csapattars;
import Jatekos.Felfedezo;
import Palya.Palya;
import Targyak.Elelmiszer;
import Targyak.Kincs;
import Targyak.Targyak;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A program fo osztalya. Ez koti ossze a tobbi osztalyt egymassal. A jatek  maga itt folyik.
 */
public class Main {
    public static Felfedezo felfedezo;
    //public static Hajo hajo;
    public static Inventory felfedezoInventory;
    public static Inventory hajoInventory;
    /**
     * Egy ures kincs osztaly, innen erjuk el a kincsek generalasahoz a kincsneveket, statikusan is meglehetne oldani.
     */
    public static Kincs kincsnevek = new Kincs();
    //public static Jatekos.Csapattars[] csapattagok;
    public static Palya palya;
    /**
     * true ha dzsungelben vagyunk es igy dupla annyi energia a kovetkezo lepes
     */
    public static boolean dzsungele = false;
    /**
     * Hany lepesen keresztul lesz meg vulkankitores, -1 ha nincs vulkankitores
     */
    public static int atokSzamlaloVulkan;
    /**
     * Hol tort ki a terkepen a vulkan
     */
    public static int[] vulkanPoziciok;
    /**
     * Hany lepesen keresztul lesz meg gejzirKitores, -1 ha nincs gejzirkitores
     */
    public static int atokSzamlaloGejzir;
    /**
     * Hol tort ki a gejzir
     */
    public static int[] gejzirPoziciok;
    /**
     * 0, ha nincs meg a piramis, 1 megvan a piramis es kaland folytatodik,
     * 2, ha  megvan a piramis de vege a kalandnak
     */
    public static int kalandVege;
    /**
     * Rivalisok listaja
     */
    public static List<Rivalis> rivalisok = new ArrayList<>();

    //constants
    private static final int ATOKCHANCE = 80; //80
    private static final int VULKANCHANCE = 35; //35
    private static final int KATASZTROFACHANCE = 65; //65
    private static final int ENERGIAVESZITESCHANCE = 70; //70
    private static final int CSAPATTARSLESERULCHANCE = 20; //70
    private static final int CSAPATTARSKILEPCHANCE = 10; //10
    private static final int CSAPATTARSATNEMAJANLCHANCE = 80; //20% hogy ajanlanak igy 80% hogy nem



    /**
     * A standard inputrol beolvasas
     * @param valaszok egy String tomb amiben a lehetseges valaszokat megadjuk
     * @return ha szerepel az input a valaszok-ban akkor visszaadjuk az inputot
     */
    public static String inputOlvasas(String[] valaszok){
        if (valaszok == null) return null;
        Scanner sc = new Scanner(System.in);
        while(true){
            String input = sc.nextLine().toLowerCase();
            for (int i  =0; i < valaszok.length; i++){
                if (valaszok[i].equals(input)){
                    return input;
                }
            }
            System.out.println("hibas input");
        }
    }
    /**
     * Random valoszinuseg szamitas
     * @param kisebbMint ennel kissebbnek kell lennie a valoszinusegnek hogy igaz legyen
     * @return bekovetkezett e a valoszinuseg
     */
    public static boolean randomChance(int kisebbMint){
        return (int)(Math.random()*100)<=kisebbMint;
    }
    /**
     * Random szam egy intervallumon belul
     * @param min legkisebb szam ami lehet
     * @param max legnagyobb szam ami lehet
     * @return egy random szam az intervallumon belul
     */
    public static int randomNumber(int min, int max){
        return (int)(((Math.random()*1000)%(max - min + 1)) + min);
    }

    public static void main(String[] args) {
        felfedezo = new Felfedezo("Bob");
        felfedezoInventory = new Inventory();
        hajoInventory = new Inventory();
        atokSzamlaloVulkan = -1;
        atokSzamlaloGejzir = -1;
        kalandVege = 0;
        //rivalisok kisorsolasa min 3 max 9 rivalis
        int rivalisokSzama = randomNumber(3,9);
        Rivalis.nevFeltoltes();
        for (int i = 0; i < rivalisokSzama; i++ ){
            rivalisok.add(new Rivalis(Rivalis.randomNev()));
        }


        //maga a jatek itt folyik le
        kulso:
        while (true) {
            kalandVege = 0;
            felfedezo.setArany(felfedezo.getArany()+250);
            System.out.println("Jatek kozben a 'help' paranccsal megtudhatod milyen parancsok vannak.");
            palya = new Palya();
            vasarlas("");
            csapattarsVasarlas("kezdes");
            felfedezo.csapattarsGyogyitas();


            //adott kuldetes vagy palyan a mozgas, jatek
            while (true) {
                kulonlegesTerulete(palya.helyzet(felfedezo.getFelderitoBonusz()));
                if(kalandVege == 1) break;
                else if(kalandVege ==2) break kulso;

                //input, mozgas stb
                while (true) {
                    System.out.print("Input: ");
                    String input = inputOlvasas(new String[]{"help","a", "s", "d", "w", "i", "status", "x", "eat","h"});

                    if (input.equals("a") || input.equals("w") || input.equals("s") || input.equals("d")) {
                        if (palya.lepes(input)) {
                            if (felfedezo.nincsEnergia()) {
                                System.out.println("Elfogyott az energiad ezert meghaltal.\nSajnalom, de veszitettel.");
                                break kulso;
                            }
                            felfedezo.setEnergia(-felfedezo.getMozgasKoltseg());
                            if(felfedezo.fuggosegNagyobbMint30()){
                                System.out.println("Meghaltal mert nem kaptal a fuggosegedet csillapito anyagbol mar " +
                                        "30 napja");
                                break kulso;
                            };
                            felfedezo.vanSerultCsapattars();
                            if (atokSzamlaloVulkan >= 0){
                                atokSzamlaloVulkan = palya.vulkanKitores(atokSzamlaloVulkan, vulkanPoziciok);
                            }
                            if (atokSzamlaloGejzir >0){
                                atokSzamlaloGejzir = palya.gejzirKitores(atokSzamlaloGejzir, gejzirPoziciok);
                            }
                            if (dzsungele) {
                                felfedezo.setEnergia(-felfedezo.getMozgasKoltseg());
                                dzsungele = false;
                            }
                            break;
                        }
                    }
                    else if (input.toLowerCase().equals("eat")) eszik();
                    else if (input.toLowerCase().equals("i")) felfedezoInventory.inventoryKiir(null);
                    else if (input.toLowerCase().equals("x")) return;
                    else if (input.toLowerCase().equals("status")) System.out.println(felfedezo.toString());
                    else if (input.equals("h") && palya.holVagyok() =='H') hajoInterface();
                    else if (input.equals("help")) help();
                }

            }

        }
        if (felfedezo.getEnergia() !=0 || !felfedezo.fuggosegNagyobbMint30())
        kiNyert(false);
        else kiNyert(true);

    }

    /**
     * Eszkozok vasarlasakoz, tobbszor egymas utan is vasarolhatunk
     * @param hely hol vagyunk amikor vasarolunk, vagy faluban es akkor a falusi termekeket lehet vasarolni
     *             vagy a kuldetes elejen
     */
    public static void vasarlas(String hely){
        System.out.println("Szeretnel-e vasarolni? (y/n)");
        if (inputOlvasas(new String[]{"y", "n"}).equals("y")) {
            Bolt bolt = new Bolt(hely,felfedezo);
            bolt.vasarlas(felfedezo, felfedezoInventory);

            while(true){
                System.out.println("Szeretnel-e meg vasarolni? (y/n)");
                if (inputOlvasas(new String[]{"y","n"}).equals("y")){
                    bolt.kinalatKiir();
                    bolt.vasarlas(felfedezo,felfedezoInventory);
                }
                else break;
            }
            System.out.println("Jelenlegi ennyi aranyad maradt: " + felfedezo.getArany());
        }
        felfedezo.setInventoryPlusz(felfedezoInventory.getSlotok());
    }
    /**
     * Csapattarsak vasarlasa, ha faluban vagyunk akkor 20% esellyel ajanlanak csak csapattarsat, ha van legalabb
     * 2 viszony
     * @param hely jelenleg hol vagyunk, ha faluban akkor a falusi csapattarsaka ajanlja, amugy meg a kaland
     *             elejen levoket
     */
    public static void csapattarsVasarlas(String hely){
        if (hely.equals("falu") && (felfedezo.getViszony()<2 || randomChance(CSAPATTARSATNEMAJANLCHANCE))) return;
            System.out.println("Szeretnel egy csapattarsat? 150 aranyert vehetsz egy segitot. (y/n)");
            if (inputOlvasas(new String[]{"y", "n"}).equals("y")) {
                if (felfedezo.getArany() >= 150) {
                    Csapattars.csapattarsFelvetel(felfedezo, hely);
                } else System.out.println("Ninc eleg aranyad");
            }

    }

    /**
     * Ha a parancssorba az 'eat' szot irjuk lehetosegunk van enni.
     * Kiirja az inventoryban levo eteleket majd ezek kozul valaszthatunk mit akarunk enni
     */
    public static void eszik(){
        if (!felfedezoInventory.vanTipus(Elelmiszer.class)) {
            System.out.println("Nincs nalad etel");
            return;
        }
        felfedezoInventory.inventoryKiir(new Elelmiszer());
        int index = 0;
        do{
            System.out.println("Valaszd ki mit szeretnel enni");
            Scanner sc = new Scanner(System.in);
            index = 0;
            try{
                index  = sc.nextInt();
            }
            catch (Exception e){
                System.out.println("Hibas input");
            }
            if (index >= felfedezoInventory.getSize() || index < 1){
                System.out.println("Itt nem etel van");

            }
            else if (felfedezoInventory.getTipus(index-1).equals(Elelmiszer.class)){
                Elelmiszer etel = felfedezoInventory.eszik(index);
                felfedezo.eszik(etel);
            }
            else System.out.println("Ez nem etel!");


            System.out.println("Szeretnel meg enni? (y/n)");
        }
        while(inputOlvasas(new String[]{"y","n"}).equals("y"));


    }

    /**
     * Megnezi hogy az odott terulet ahol vagyunk kulonleges-e vagyis kell e valaminek tortennie
     * @param terulet az adott terulet ahol vagyunk
     */
    public static void kulonlegesTerulete(char terulet){
        switch (terulet){
            case 'F':
            case 'f':
                csapattarsVasarlas("falu");
                vasarlas("falu");
                break;
            case 'B':
                felfedezoInventory.inventorybaTesz(new Kincs(kincsnevek.generateName(), randomNumber(200,1000)));
                katasztrofa(felfedezo);
                System.out.println("Gratulalok, szerestel egy kincset!");
                break;
            case 'H':
                felfedezo.setEnergia(100);
                hajoInterface();
                break;
            case 'D':
                dzsungel();
                break;
            case 'o':
            case 'O':
                felfedezoInventory.inventorybaTesz(new Kincs(kincsnevek.generateName(), randomNumber(200,1000)));
                atok();

                System.out.println("Gratulalok, szerestel egy kincset!");
                break;
            case 'P':
                System.out.println("Gratulalok, szerestel egy kincset!");
                felfedezoInventory.inventorybaTesz(new Kincs(kincsnevek.generateName(), randomNumber(200,1000)));
                piramis();


                break;
        }

    }

    /**
     * Akkor hivodik meg ha a piramisnal vagyunk, lehetosegunk van ujabb
     * palyara menni vagy be is fejezhetjuk a kalandot.
     * Emellett valaszthatunk hogy eladjuk a kincseket vagy a muzeumnak adomanyozzuk oket
     */
    public static void piramis(){
        System.out.println("Gratulalok, megtalaltad az arany piramist!");
        System.out.println("Szeretned folytatni a kalandot? (y/n)");
        if(inputOlvasas(new String[]{"y","n"}).equals("y")){
            kalandVege = 1;
            felfedezo.setSikeresKuldetesek(felfedezo.getSikeresKuldetesek()+1);
        }
        else{
            felfedezo.setHirnev(felfedezo.getHirnev()+1000);
            kalandVege = 2;
            System.out.println("Megszerzett hirnev: " + felfedezo.getHirnev());
            System.out.println("Megszerzett arany: " + felfedezo.getArany());
        }
        kincsEladas();
        rivalisokHirnevNoveles();
        rivalisokAdat();
        System.out.println("Sajat hirnev: " + felfedezo.getHirnev());
    }
    /**
     * Ha egy oltarra lepunk 80% esellyel meg leszunk atkozva, ha meg nem vagyunk megatkozva.
     * Emellet a viszony csokken 2-vel.
     * 65%-os esellyel vulkankitores, amugy meg gejzirkitores
     */
    public static void atok(){
        felfedezo.setViszony(felfedezo.getViszony()-2);
        if (randomChance(ATOKCHANCE)){
            //vulkankitores
            if (randomChance(VULKANCHANCE) && atokSzamlaloVulkan==-1 && atokSzamlaloGejzir==-1){
                System.out.println("Meg lettel atkozva: \nKitort egy vulkan");
                atokSzamlaloVulkan=4;
                vulkanPoziciok = palya.holVan('M', null);
            }
            //gejzin
            else if(atokSzamlaloGejzir==-1 && atokSzamlaloVulkan==-1){
                System.out.println("Meg lettel atkozva: \nKitort egy gejzir");
                atokSzamlaloGejzir=4;
                gejzirPoziciok = palya.gejzirKitoresHol();
            }
        }
    }
    /**
     * Ha dzsungelben vagyun egy bozotvagot elhasznalunk ha van es a terulet sima fuves lesz.
     * Ha nincs bozotvago akkor a kovetkezo lepes dupla annyi energiaba kerul.
     */
    public static void dzsungel(){
        int index = felfedezoInventory.inventorybanVane("bozotvago");
        if (index != -1){
            felfedezoInventory.inventorybolKivesz(index,1);
            System.out.println("Dzsungelbe kerultel, egy bozotvago elhasznalva");
            palya.dzsungelbolFuves();
        }
        else{
            System.out.println("Egy dzsungelben vagy bozotvago nelkul, kovetkezo lepesed dupla annyi energiaba kerul.");
            dzsungele = true;
        }
    }
    public static void katasztrofa(Felfedezo felfedezo){
        int index = felfedezoInventory.inventorybanVane("faklya");
        if (index != -1){
            System.out.println("Akarsz faklyat hasznalni? (y/n)");
            if (inputOlvasas(new String[]{"y","n"}).equals("y")){
                felfedezoInventory.inventorybolKivesz(index,1);
                System.out.println("Egy faklya elhasznalva.");
                return;
            }
        }


        if (felfedezo.getCsapattarsakSzama() == 0) return;
        int csapatSzam = 0;
        if (randomChance(KATASZTROFACHANCE)){
            System.out.println("Katasztrtofa kovetkezett be");
            if (randomChance(ENERGIAVESZITESCHANCE)){
                System.out.println("45 energiat veszitettel!");
                felfedezo.setEnergia(-45);
            }
            if (randomChance(CSAPATTARSLESERULCHANCE)){
                csapatSzam = felfedezo.getCsapattarsakSzama();
                //random = (int)(Math.random()*100%csapatSzam);
                System.out.println(felfedezo.csapattarsLeserul(randomNumber(0,csapatSzam)) + " leserult!");;
                }
            }
            if (randomChance(CSAPATTARSKILEPCHANCE)){
                //random = (int)(Math.random()*100%csapatSzam);
                felfedezo.csapattarsKivesz(felfedezo.getCsapattagok()[randomNumber(0,csapatSzam)]);
                System.out.println("Egy csapattag elhagyta a csapatot");
            }
        }

    /**
     * A hajohoz kapcsolodo muveletek.
     * Hajoba rakas, kivetel, inventory megtekintese
     */
    public static void hajoInterface(){
        hajobaTesz();
        System.out.println("Szeretned megnezni a hajo inventoryjat? (y/n)");
        if (inputOlvasas(new String[]{"y","n"}).equals("y")){
            hajoInventory.inventoryKiir(null);
        }
        hajobolKivesz();
        felfedezo.setInventoryPlusz(felfedezoInventory.getSlotok());

    }
    /**
     * Hajo inventoryjaba targyak betetele
     * ha hibasan adtuk meg az adatokat hibat dob
     */
    public static void hajobaTesz(){
        System.out.println("Szeretnel a hajoba tenni valamit? (y/n)");
        while(inputOlvasas(new String[]{"y","n"}).equals("y")){
            System.out.println("Ahhoz, hogy kivegyel valamit add meg a targy indexet es a mennyiseget szokozzel elvalasztva.\n" +
                    "pl.: 2 5");
            felfedezoInventory.inventoryKiir(null);
            int index = 0;
            int mennyiseg = 0;
            try{
                Scanner sc = new Scanner(System.in);
                String[] adatok = sc.nextLine().split(" ");
                index = Integer.parseInt(adatok[0]);
                mennyiseg = Integer.parseInt(adatok[1]);
            }
            catch(Exception e){
                System.out.println("Hibasan adtad meg az adatokat");
            }
            Targyak kivettItem = felfedezoInventory.inventorybolKivesz(index,mennyiseg);
            if (kivettItem != null)
                hajoInventory.inventorybaTesz(kivettItem);
            System.out.println("Szeretnel meg valamit eltenni? (y/n)");
            //Jatekos.Felfedezo.setMozgasKoltseg((felfedezoInventory.getSlotok()> felfedezoInventory.getSlotHatar())?-0.2:0);
        }
    }
    /**
     * Targyakat vehetunk ki a hajo inventoryjabol
     */
    public static void hajobolKivesz(){
        System.out.println("Szeretnel a hajobol kivenni valamit? (y/n)");
        while(inputOlvasas(new String[]{"y","n"}).equals("y")){
            hajoInventory.inventoryKiir(null);
            int index = 0;
            int mennyiseg = 0;
            try{
                Scanner sc = new Scanner(System.in);
                String[] adatok = sc.nextLine().split(" ");
                index = Integer.parseInt(adatok[0]);
                mennyiseg = Integer.parseInt(adatok[1]);
            }
            catch(Exception e){
                System.out.println("Hibasan adtad meg az adatokat");
            }
            Targyak kivettItem = hajoInventory.inventorybolKivesz(index,mennyiseg);
            if (kivettItem != null)
                felfedezoInventory.inventorybaTesz(kivettItem);
            System.out.println("Szeretnel meg valamit kivenni? (y/n)");
        }
    }

    /**
     * Minden kaland vegen egy random mennyiseggel noveljuk a rivalisok hirnevet
     */
    public static void rivalisokHirnevNoveles(){
        for (Rivalis r : rivalisok){
            r.hirnevNovel(randomNumber(500,1500));
        }
    }
    /**
     * A kaland vegevel kiirjuk a rivalisok neveit es hirnevuket
     */
    public static void rivalisokAdat(){
        for (Rivalis r : rivalisok){
            System.out.println(String.format("%-25s" ,r.getNev()) + " hirneve: " + r.getHirnev());
        }
    }

    /**
     * Minden kaland vegen eladhatunk kincseket vagy a muzeumnak adhatjuk oket
     */
    public static void kincsEladas(){

        System.out.println("Szeretnel-e eladni kincseket, vagy muzeumnak adomanyozni oket? (y/n)");
        while(inputOlvasas(new String[]{"y","n"}).equals("y")){
            if (!felfedezoInventory.vanTipus(Kincs.class)) {
                System.out.println("Nincs kincs nalad");
                return;
            }
            System.out.println("Ird le hogy hanyas szamu kincset szeretned es hogy eladi (e) vagy muzeumnak adni szeretned (m)");
            System.out.println("Pl.: 1 m");
            System.out.println("Eladassal aranyat szerezhetsz, mig ha a muzeumnak adod akkor hirnevet.");
            felfedezoInventory.inventoryKiir(new Kincs());
            int index = 0;
            String valasztas = "e";
            try{
                Scanner sc = new Scanner(System.in);
                String[] adatok = sc.nextLine().split(" ");
                index = Integer.parseInt(adatok[0]);
                valasztas = adatok[1].toLowerCase();
            }
            catch(Exception e){
                System.out.println("Hibasan adtad meg az adatokat");
                System.out.println("Szeretnel eladni valamit?");
                continue;
            }
            if (index > 0 && index <= felfedezoInventory.getSize()){
                if(felfedezoInventory.getTipus(index-1).equals(Kincs.class)){
                    if (valasztas.equals("e")){
                        Targyak kivettItem = felfedezoInventory.inventorybolKivesz(index,1);
                        felfedezo.setArany(felfedezo.getArany()+ kivettItem.getErtek());
                        System.out.println(kivettItem.getNev() + " sikeresen eladva! +" + kivettItem.getErtek() + " arany");
                        System.out.println("Arany: " + felfedezo.getArany());
                    }
                    else if(valasztas.equals("m")){
                        Targyak kivettitem = felfedezoInventory.inventorybolKivesz(index, 1);
                        felfedezo.setHirnev(felfedezo.getHirnev()+1000);
                        System.out.println(kivettitem.getNev() + " sikeresen a muzeumnak adomanyozva! +1000 hirnev");
                        System.out.println("hirnev: " + felfedezo.getHirnev());
                    }
                }
                else System.out.println("Itt nem kincs van.");
            }
            else System.out.println("Rossz indexet adtal meg");
            System.out.println("Szeretnel meg eladni valamit? (y/n)");
        }

    }

    /**
     * Kiirja ki nyerte a jatekot
     * @param veszitett true ha a jatokos veszitett fuggoseg miatt vagy mert elfogyott az energia
     */
    public static void kiNyert(boolean veszitett){
        if (veszitett){
            System.out.println("Vesztettel!");
            return;
        }
        Rivalis maxHirnev = rivalisok.get(0);
        for (int i = 0; i < rivalisok.size(); i++){
            if (rivalisok.get(i).getHirnev() > maxHirnev.getHirnev()){
                maxHirnev = rivalisok.get(i);
            }
        }
        if (maxHirnev.getHirnev() > felfedezo.getHirnev()){
            System.out.println("A gyoztes " + maxHirnev.getNev() + " " + maxHirnev.getHirnev() + " hirnevvel!");
            System.out.println("Legkozelebb jobb szerencsed lesz");
        }
        else System.out.println("Gratulalok, te gyoztel " + felfedezo.getHirnev() + " hirnevvel!");
    }

    /**
     * Kiirja milyen parancsok vannak
     */
    public static void help(){
        System.out.println("Segitseg:");
        System.out.println( "help       -> segitseg megjelenitese\n" +
                            "a, s, d, w -> mozgas\n" +
                            "eat        -> eves\n" +
                            "i          -> inventory\n" +
                            "status     -> allapot\n" +
                            "h          -> hajo interface, ha a hajon vagy\n" +
                            "x          -> jatek leallitasa, program azonnal kilep."  );
    }
}
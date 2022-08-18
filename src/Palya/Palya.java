package Palya;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

/*
tenger-T
hajo-H
to-L(AKE)
hegy-M
barlang-B
sima oltar-O
vizes oltar-o
dzsungel-D
arany piramis-P
sima falu-F
vizes falu-f
piros-P
sima fuves-G(rass)
vizes fuves-g
sima zold-Z
vizes-z
 */

/**
 * Palyat megvalosito osztaly. Itt van eltarolva maga a palya es egy ures terkep, amin a felfedezett teruleteket
 * taroljuk. A palyaval kapcsolatos dolgok itt vannak.
 */
public class Palya {
    /**
     * Maga a palya
     */
    private static char[][] palya;
    /**
     * A jatekos altal mar felderitett teruletet tartalmazza
     */
    private char[][] terkep;
    private int szelesseg;
    private int magassag;
    private int[] pozicio;

    /**
     * Egy koordinatat tarolo osztaly
     * Egy osztaly amit strukturakent lehet hasznalni, egy koordinatat tarol.
     */
    private class Pozicio {
        private int x;
        private int y;

        public Pozicio(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }


    //setterek



    public Palya() {

        System.out.println("Valassz palyat:");
        System.out.println("Alapertelmezett: 1\nRandom: 2\nBeolvasas: 3");

        int palyaTipus;
        while(true) {
            palyaTipus = 0;
            try {
                Scanner sc = new Scanner(System.in);
                palyaTipus = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Hibasan adtad meg a szamot!");
            }
            if (palyaTipus != 1 && palyaTipus != 2 && palyaTipus != 3) {
                System.out.println("Ez nem megfelelo szam");
            }
            else break;
        }
        if (palyaTipus == 1) palyaBeolvasas();
        else if(palyaTipus == 2) palyaGeneralas();
        else palyaFajlbol();
        feltolt();
        this.pozicio = kezdoPozicio();
    }

    /**
     * Egyedi palya beolvasasa a felhasznalo altal megadott filebol.
     */
    public void palyaFajlbol(){
        while (true) {
            System.out.print("Add meg a palya fajl nevet: ");
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            String[] fileExtenson = fileName.split("\\.");
            if (fileExtenson.length !=2 || !fileExtenson[1].equals("txt")){
                System.out.println("Rossz fajlt adtal meg. Ez nem txt.");
                continue;
            }

            try {
                File file = new File("./src/"+fileName);
                if (!file.exists()){
                    System.out.println("Ilyen fajl nem letezik.");
                    throw new NoSuchFileException("Nincs ilyen file");
                }
                Scanner scanner = new Scanner(file);

                String[] adatok = scanner.nextLine().split(":");

                this.szelesseg = Integer.parseInt(adatok[1]);
                this.magassag = Integer.parseInt(adatok[0]);
                palya = new char[magassag][szelesseg];
                this.terkep = new char[magassag][szelesseg];
                int sor = 0;
                while (scanner.hasNextLine()) {
                    palya[sor] = (scanner.nextLine()).toCharArray();
                    sor++;
                }
                scanner.close();
                return;
            } catch (IOException e) {
                System.err.println("Hiba történt: " + e.getMessage());
            }
        }
    }
    /**
     * Palya.Palya beolvasasa file-bol. A feladatban is szereplo pelda palyat olvassa be.
     */
    public void palyaBeolvasas(){
        try {
            Scanner scanner = new Scanner(new File("./src/palya1.txt"));
            String[] adatok = scanner.nextLine().split(":");

            this.szelesseg = Integer.parseInt(adatok[1]);
            this.magassag = Integer.parseInt(adatok[0]);
            palya = new char[magassag][szelesseg];
            this.terkep = new char[magassag][szelesseg];
            int sor = 0;
            while (scanner.hasNextLine()) {
                palya[sor] = (scanner.nextLine()).toCharArray();
                sor++;
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Hiba történt: " + e.getMessage());
        }
    }


    /**
     * General egy random palyat a felteteleknek megfeleloen
     */
    public  void palyaGeneralas(){
        while(true) {
            this.magassag = randomNumber(10, 25);
            this.szelesseg = randomNumber(10, 25);
            this.terkep = new char[magassag][szelesseg];
            int szarazfold = 0;
            palya = new char[magassag][szelesseg];
            List<int[]> szarazKoordinatak = new ArrayList<>();
            for (int i = 0; i < magassag; i++) {
                Arrays.fill(palya[i], 'T');
            }
            int valoszinuseg = 0;
            for (int i = 0; i < magassag; i++) {
                for (int j = 0; j < szelesseg; j++) {
                    if ((int) (Math.random() * 100) <= valoszinuseg) {
                        palya[i][j] = 'Z';
                        szarazKoordinatak.add(new int[]{i, j});
                        szarazfold++;
                    }
                }
                valoszinuseg = (valoszinuseg < 100) ? i * i * i * i : 100;
            }
            Map<Character, Integer> mezok = new HashMap<Character, Integer>();
            mezok.put('P', 1);
            mezok.put('M', (int) (szarazfold * 0.18));
            mezok.put('L', (int) (szarazfold * 0.05));
            mezok.put('D', (int) (szarazfold * 0.15));
            mezok.put('B', (int) (szarazfold * 0.03));
            mezok.put('O', (int) (szarazfold * 0.02));
            mezok.put('G', (int) (szarazfold * 0.18));
            kulso:
            while (true) {
                int index = randomNumber(0, szarazKoordinatak.size() - 1);
                int[] koordinatak = szarazKoordinatak.get(index);
                for (int i = -1; i < 2; i += 2) {
                    for (int j = -1; j < 2; j += 2) {
                        if (koordinatak[0] + i >= 0 && koordinatak[0] + i < palya.length && koordinatak[1] + j >= 0 && koordinatak[1] + j < palya[0].length) {
                            if (palya[koordinatak[0] + i][koordinatak[1]] == 'T') {
                                palya[koordinatak[0] + i][koordinatak[1]] = 'H';
                                szarazKoordinatak.remove(index);
                                break kulso;
                            }
                            if (palya[koordinatak[0]][koordinatak[1] + j] == 'T') {
                                palya[koordinatak[0]][koordinatak[1] + j] = 'H';
                                szarazKoordinatak.remove(index);
                                break kulso;
                            }
                        }
                    }
                }

            }
            for (Map.Entry<Character, Integer> elem : mezok.entrySet()) {
                while (elem.getValue() > 0) {
                    int index = randomNumber(0, szarazKoordinatak.size()-1);
                    int[] koordinatak = szarazKoordinatak.get(index);
                    szarazKoordinatak.remove(index);
                    palya[koordinatak[0]][koordinatak[1]] = elem.getKey();
                    elem.setValue(elem.getValue() - 1);
                }
            }
            System.out.print("Palya.Palya ellenorzese...");
            if (holVan('P', kezdoPozicio()) != null) break;
            else System.out.println("Hibas palya generalva, ujrageneralas");
        }
        vizesTerulet();
    }
    /**
     * Vegigmegy a palyan es ha vizes teruletet talal a korulotte levo teruleteket vizesse teszi
     * @see #palyaGeneralas()
     */
    public void vizesTerulet(){
        for (int k = 0; k < magassag; k++){
            for (int l = 0; l < szelesseg; l++){
                if (palya[k][l] == 'L'){
                    for (int i = -1 ; i < 2  ; i++) {
                        for (int j = -1  ; j < 2 ; j++) {
                            if (i == 0 && j == 0) continue;
                            if (k + i >= 0 && l + j >= 0 && k + i < palya.length && l + j < palya[0].length){
                                if (palya[k+i][l+j] == 'Z' || palya[k+i][l+j] == 'G' || palya[k+i][l+j] == 'F' || palya[k+i][l+j] == 'O'){
                                    palya[k + i][l + j] +=32 ;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Megkeresi a hajo helyet
     * @return visszaadja a hajo helyet
     */
    public int[] kezdoPozicio() {
        int[] pozicio = new int[2];
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya[i].length; j++) {
                //System.out.print(palya[i][j] + "  ");
                if (palya[i][j] == 'H') {
                    pozicio[1] = i;
                    pozicio[0] = j;
                    terkepFrissit(pozicio, 0);
                    return pozicio;
                }
            }
            //System.out.println();
        }

        return pozicio;
    }
    /**
     * Feltolti a terkepet '-' -el
     */
    public void feltolt() {
        for (int i = 0; i < terkep.length; i++) {
            Arrays.fill(terkep[i], ' ');
        }
    }
    /**
     * Kiirja a palyat, debuggolashoz hasznalatos
     */
    public void palyaKiir(){

        for (int i = 0; i < palya.length; i++){
            for (int k = 0; k < palya[i].length; k++){
                Pozicio p = new Pozicio(k, i);
                    System.out.print(palya[i][k] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Frissiti a terkepet a jatekos helyzete korul
     * @see #terkepFrissit(int[], int)
     * A frissitett terkepet kiirja a kepernyore, a sajat poziciot egy x jelzi,
     * Megnezi jelenleg milyen teruleten vagy
     * @see #holVagyok()
     * @param felderitoBonusz ha van felderito a csapatban akkor noveli a latotavot
     * @return visszaadko a jelenleg milyen mezon vagyunk
     */
    public char helyzet(int felderitoBonusz) {
        terkepFrissit(pozicio, felderitoBonusz);
        for (int i = 0; i < terkep.length; i++) {
            for (int k = 0; k < terkep[i].length; k++) {
                //if(i== 0 || i == terkep[i].length ) System.out.print("*  ");
                if (pozicio[0] == k && pozicio[1] == i) {
                    System.out.print("X  ");
                } else System.out.print(terkep[i][k] + "  ");
            }
            System.out.println();
        }
        holVagyok();
        return terkep[pozicio[1]][pozicio[0]];
    }

    public int getSzelesseg() {
        return szelesseg;
    }

    public int getMagassag() {
        return magassag;
    }

    /**
     * Megnezi hogy lehet e az adott iranyba lepni,
     * @param irany merre akarunk lepni
     * @return sikeres volt e a lepes
     */
    public boolean lepes(String irany) {
        switch (irany) {
            case "d":
                if (pozicio[0] + 1 < szelesseg && palya[pozicio[1]][pozicio[0] + 1] != 'T' && palya[pozicio[1]][pozicio[0] + 1] != 'L'
                        && palya[pozicio[1]][pozicio[0] + 1] != 'M') {
                    pozicio[0]++;
                    //terkepFrissit(pozicio);
                    return true;
                } else System.out.println("Erre nem lehet lepni");
                return false;
            case "a":
                if (pozicio[0] - 1 >= 0 && palya[pozicio[1]][pozicio[0] - 1] != 'T' && palya[pozicio[1]][pozicio[0] - 1] != 'L'
                        && palya[pozicio[1]][pozicio[0] - 1] != 'M') {
                    pozicio[0]--;

                    //terkepFrissit(pozicio);
                    return true;
                } else System.out.println("Erre nem lehet lepni");
                return false;
            case "w":
                if (pozicio[1] - 1 >= 0 && palya[pozicio[1] - 1][pozicio[0]] != 'T' && palya[pozicio[1] - 1][pozicio[0]] != 'L'
                        && palya[pozicio[1] - 1][pozicio[0]] != 'M' ) {
                    pozicio[1]--;
                    //terkepFrissit(pozicio);
                    return true;
                } else System.out.println("Erre nem lehet lepni");
                return false;
            case "s":
                if (pozicio[1] + 1 < magassag && palya[pozicio[1] + 1][pozicio[0]] != 'T' && palya[pozicio[1] + 1][pozicio[0]] != 'L'
                        && palya[pozicio[1] + 1][pozicio[0]] != 'M' ) {
                    pozicio[1]++;

                    //terkepFrissit(pozicio);
                    return true;
                } else System.out.println("Erre nem lehet lepni");
                return false;

            default:
                System.out.println("Hibas input");
                return false;

        }
    }
    /**
     * Frissiti a terkepet a jelenlegi pozicionk korul
     * @param pozicio hol vagyunk jelenleg
     * @param felderitoBonusz ha van felderito akkor hozzaadjuk a bonuszt
     * @see #helyzet(int)
     */
    public void terkepFrissit(int[] pozicio, int felderitoBonusz) {
        for (int i = -1 - felderitoBonusz; i < 2 + felderitoBonusz; i++) {
            for (int j = -1 - felderitoBonusz; j < 2 + felderitoBonusz; j++) {
                if (pozicio[1] + i >= 0 && pozicio[0] + j >= 0 && pozicio[1] + i < terkep.length && pozicio[0] + j < terkep[0].length){
                    terkep[pozicio[1] + i][pozicio[0] + j] = palya[pozicio[1] + i][pozicio[0] + j];

                }
            }
        }
    }
    /**
     * Megmondja a jatekosnak milyen teruleten van jelenleg
     * @return a terulet tipusat adja vissza
     * @see #helyzet(int)
     */
    public char holVagyok() {
        char hol = terkep[pozicio[1]][pozicio[0]];
        switch (hol) {
            case 'H': System.out.println("Jelenleg a hajon vagy.");
            break;
            case 'B': System.out.println("Jelenleg egy barlangban vagy.");
            break;
            case 'O': System.out.println("Jelenleg egy oltarnal vagy.");
            break;
            case 'o': System.out.println("Jelenleg egy oltarnal vagy ami egy to mellett van.");
            break;
            case 'P': System.out.println("Jelenleg egy piramisban vagy.");
            break;
            case 'V': System.out.println("Jelenleg egy vizes teruleten vagy egy to mellett.");
            break;
            case 'F': System.out.println("Jelenleg egy faluban vagy, lehetoseged van vasarolni.");
            break;
            case 'f': System.out.println("Jelenleg egy faluban vagy egy to mellett, lehetoseged van vasarolni.");
            break;
            case 'G': System.out.println("Jelenleg egy fuves teruleten vagy.");
            break;
            case 'Z': System.out.println("Jelenleg egy sima teruleten vagy, nincs itt semmi (tenyleg)");
            break;
        }
        return hol;
    }


    /**
     * A dzsungel teruletbol fuveset csinal
     */
    public void dzsungelbolFuves() {
        if (palya[pozicio[1]][pozicio[0]] == 'D')
            palya[pozicio[1]][pozicio[0]] = 'G';
    }


    /**
     * egy szamot ad az adott intervallumban
     * @param min legkisebb szam ami lehet
     * @param max legnagyobb szam ami lehet
     * @return a veletlen szamot
     */
    public static int randomNumber(int min, int max){
        return (int)(Math.random()*100%(max-min+1)+min);
    }


    /**
     * Kisorsol egy random poziciot ahol kitor a gejzir
     * @return a gejzir poziciojat
     */
    public int[] gejzirKitoresHol(){
        int x = 0;
        int y = 0;
        while(true){
            x = randomNumber(pozicio[0]+4-8, pozicio[0]);
            y = randomNumber(pozicio[1]+4-8,pozicio[1]);
            if (pozicio[1] + y >= 0 && pozicio[0] + x >= 0 && pozicio[1] + y < terkep.length && pozicio[0] + x < terkep[0].length){
                if (palya[pozicio[1]+y][pozicio[0]+x] != 'T' && pozicio[1]+y != pozicio[1] && pozicio[0]+x != pozicio[0]){
                    System.out.println("gejzirkitores: " +(pozicio[0]+x)+" " + (pozicio[1]+y));
                    return new int[]{pozicio[0]+x,pozicio[0]+y};
                }
            }
        }
    }
    /**
     * A gejzirkitores helye korul a terulet vizes terulette valtozik, ez az adott lepeseken keresztul tart
     * @param szamlalo hanyadszorra torik ki a gejzir
     * @param pozicio kol van a gejzir
     * @return csokkentjuk a szamlalot
     */
    public int gejzirKitores(int szamlalo, int[] pozicio){
        System.out.println("kitores: "+ pozicio[0]+ " "+ pozicio[1]);
        int terulet = 4 - szamlalo;
        for (int i = -1 - terulet; i < 2 + terulet; i++) {
            for (int j = -1 - terulet; j < 2 + terulet; j++) {
                if (pozicio[1] + i >= 0 && pozicio[0] + j >= 0 && pozicio[1] + i < magassag && pozicio[0] + j < szelesseg)
                    //terkep[pozicio[1] + i][pozicio[0] + j] =
                    palya[pozicio[1] + i][pozicio[0] + j] =gejzinTerulet(palya[pozicio[1] + i][pozicio[0] + j]) ;

            }
        }

        return szamlalo-1;
    }
    /**
     * Az adott teruletet megvaltoztatjuk vizes terulette, ami annyit jelent hogy kisbetus lesz
     * @param terulet mit valtoztatunk meg
     * @return a megvaltoztatett terulet ha megvaltoztattuk, egyebkent a parametert visszaadjuk
     */
    public char gejzinTerulet(char terulet){
        switch (terulet){
            case 'O': return 'o';
            case 'F': return 'f';
            case 'G': return 'g';
            case 'Z': return 'V';
        }
        return terulet;

    }


    /**
     * Lavava valtoztatjuk a vulkan koruli teruletet, majd ha a szamlalo 0 akkor vissza
     * @param szamlalo hanyadszorra torik ki a vulkan
     * @param poziciok hol van a vulkan
     * @return szamlalot csokkentjuk egyel
     */
    public int vulkanKitores(int szamlalo, int[] poziciok) {
        if (szamlalo == 0){
            for (int i = -1 -4; i < 2 + 4; i++) {
                for (int j = -1 - 4; j < 2 + 4; j++) {
                    if (poziciok[1] + i >= 0 && poziciok[0] + j >= 0 && poziciok[1] + i < palya.length && poziciok[0] + j < palya[0].length){
                        palya[poziciok[1] + i][poziciok[0] + j] = lavaVisszaTerulet(palya[poziciok[1] + i][poziciok[0] + j]);
                    }


                }
            }
            return -1;
        }
        int terulet = 4 - szamlalo;
        for (int i = -1 - terulet; i < 2 + terulet; i++) {
            for (int j = -1 - terulet; j < 2 + terulet; j++) {
                if (poziciok[1] + i >= 0 && poziciok[0] + j >= 0 && poziciok[1] + i < magassag && poziciok[0] + j < szelesseg)
                    //terkep[pozicio[1] + i][pozicio[0] + j] =
                    palya[poziciok[1] + i][poziciok[0] + j] =lavaterulet(palya[poziciok[1] + i][poziciok[0] + j]) ;

            }
        }

        return szamlalo-1;
    }
    /**
     * Megadjuk mi legyen a lavat erinto terulettel
     * @param terulet a terulet amit lava ert
     * @return   a megvaltozott terulet, vagy a parameter ha nem valtozott meg
     */
    public char lavaterulet(char terulet){
        switch (terulet){
            case 'O': return '0';
            case 'D': return '1';
            case 'P': return '2';
            case 'F': return '3';
            case 'G': return '4';
            case 'Z': return '5';

        }
        return terulet;
    }
    /**
     * Visszavaltoztatjuk a lavas teruletet
     * @param terulet mit valtoztatunk
     * @return mire valtoztatjuk
     */
    public char lavaVisszaTerulet(char terulet){
        switch (terulet){
            case '0': return 'O';
            case '1':
            case '4':
            case '5':
                return 'Z';
            case '2': return 'P';
            case '3': return 'F';
        }
        return terulet;
    }


    /**
     * Megkeresi a parameterben kapott karakternt a palyan a helyzetunkhoz kepest a legkozelebbit
     * @param mi mit keresunk
     * @param pozicio a pozicio amit nez a metodus, kezdetben a jotekos pozicioja
     * @return a keresett elem helye
     */
    public int[] holVan(char mi, int[] pozicio) {

        Set<Pozicio> poziciok = new HashSet<>();
        if (pozicio == null)
        poziciok.add(new Pozicio(this.pozicio[0], this.pozicio[1]));
        else poziciok.add(new Pozicio(pozicio[0], pozicio[1]));

        int i = 0;
        //11 utan mar sok ido a kereses
        while (i <11) {
            if (mi=='P') System.out.print((i+1) + ".. ");
            poziciok = kifejt(poziciok, mi);
            for (Pozicio p : poziciok){
                if (palya[p.y][p.x] == mi){
                    System.out.println();
                    return new int[]{p.x,p.y};
                }
            }
            i++;

        }
        System.out.println();
        return null;
    }
    /**
     * Megnezi a parameterben kapott poziciok korul mi van es ha a palyan belul vannak beleteszi azt egy
     * poziciok halmazba
     * @param poziciok  a poziciok amiket meg kell nezni
     * @return az uj halmazt az uj poziciokkal
     */
    public Set<Pozicio> kifejt(Set<Pozicio> poziciok, char mi){
        Set<Pozicio> ujPoziciok = new HashSet<>();
        for (Pozicio p:poziciok){
            if (p.y-1 >= 0){
                if(mi =='P') {
                    if(palya[p.y-1][p.x]!='M' || palya[p.y-1][p.x]!='T' || palya[p.y-1][p.x]!='L'){
                        ujPoziciok.add(new Pozicio(p.x,p.y-1));
                    }
                }
                else ujPoziciok.add(new Pozicio(p.x,p.y-1));

            }
            if (p.y+1 < magassag) {
                if(mi == 'P'){
                    if(palya[p.y+1][p.x]!='M' || palya[p.y+1][p.x]!='T' || palya[p.y+1][p.x]!='L')
                        ujPoziciok.add(new Pozicio(p.x,p.y+1));
                }
                else ujPoziciok.add(new Pozicio(p.x,p.y+1));
            }
            if (p.x-1 >= 0) {
                if(mi=='P'){
                    if(palya[p.y][p.x-1]!='M' || palya[p.y][p.x-1]!='T' || palya[p.y][p.x-1]!='L')
                        ujPoziciok.add(new Pozicio(p.x-1,p.y));
                }
                else ujPoziciok.add(new Pozicio(p.x-1,p.y));
            }
            if (p.x+1 < szelesseg) {
                if (mi=='P'){
                    if(palya[p.y][p.x+1]!='M' || palya[p.y][p.x+1]!='T' || palya[p.y][p.x+1]!='L')
                        ujPoziciok.add(new Pozicio(p.x+1,p.y));
                }
                else ujPoziciok.add(new Pozicio(p.x+1,p.y));
            }
        }

        return ujPoziciok;
    }





}

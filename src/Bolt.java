import Jatekos.Felfedezo;
import Targyak.Elelmiszer;
import Targyak.Eszkoz;
import Targyak.Targyak;

import java.util.Scanner;

/**
 * Vasarlast mogvalosito osztaly. A kuldetesek elejen es a faluban ezt az osztalyt hasznaljuk
 * hogy a jatekos tudjon vasarolni.
 */
public class Bolt {
    /**
     * Milyen aruk vannak a boltban
     */
    private  Targyak[] arucikk;

    /**
     * Bolt konstruktora, aruk feltoltese
     * @param hely hol vasarolunk: vagy falu vagy a kaland elejen
     * @param f a felfedezo adatait atadjuk igy azokat hasznalhatjuk
     */
    public  Bolt(String hely, Felfedezo f){
        if (hely.equals("falu")){
            arucikk = new Targyak[]{
                    new Eszkoz("kotel", (int)(Math.random()*10),(int)(15*(1-f.getKereskedoBonusz()))),
                    new Eszkoz("faklya", (int)(Math.random()*10),(int)(20*(1-f.getKereskedoBonusz()))),
                    new Elelmiszer("gyumolcs", (int)(Math.random()*10),(int)(3*(1-f.getKereskedoBonusz())),15),
                    new Elelmiszer("hus", (int)(Math.random()*10),(int)(10*(1-f.getKereskedoBonusz())),25),
                    new Elelmiszer("kabitoszer", (int)(Math.random()*10),(int)(30*(1-f.getKereskedoBonusz())),20),
            };
            kinalatKiir();
            System.out.println("Vasarsashoz ird be az adott aru sorszamat es hogy mennyit vennel belole.");
        }
        else{
            arucikk = new  Targyak[]{
                    new Eszkoz("kotel", (int)(Math.random()*10),(int)(10*(1-f.getKereskedoBonusz()))),
                    new Eszkoz("bozotvago", (int)(Math.random()*10),(int)(20*(1-f.getKereskedoBonusz()))),
                    new Eszkoz("faklya", (int)(Math.random()*10),(int)(25*(1-f.getKereskedoBonusz()))),
                    new Eszkoz("uveggolyo", (int)(Math.random()*10),(int)(5*(1-f.getKereskedoBonusz()))),
                    new Elelmiszer("hus", (int)(Math.random()*10),(int)(15*(1-f.getKereskedoBonusz())),25),
                    new Elelmiszer("whiskey", (int)(Math.random()*10),(int)(30*(1-f.getKereskedoBonusz())),20),
                    new Elelmiszer("csokolade", (int)(Math.random()*10),(int)(6*(1-f.getKereskedoBonusz())),20),
            };
            kinalatKiir();
            System.out.println("Vasarsashoz ird be az adott aru sorszamat es hogy mennyit vennel belole.");
        }
    }

    /**
     * Kiirja a boltban arult targyakat
     */
    public void kinalatKiir(){
        int i = 1;
        for (Targyak s : arucikk){
            System.out.println(i + ", " +String.format("%-10s",s.getNev() ) + "\t" + s.getDarabszam() + " db " + s.getErtek() + " arany/db");
            i++;
        }
    }

    /**
     * Vasarlas megvalositasa
     * @param f a felfedezo adatait ennek segitsegevel erjuk el
     * @param inventory a felfedezo inventoryjat ennek segitsegevel erjuk el
     */
    public void vasarlas(Felfedezo f, Inventory inventory){

            System.out.println("Arany: " + f.getArany());
            Scanner sc = new Scanner(System.in);
            int index = 0;
            int mennyiseg = 0;
            try{
                String[] rendeles = sc.nextLine().split(" ");
                index = Integer.parseInt(rendeles[0]);
                mennyiseg = Integer.parseInt(rendeles[1]);
            }
            catch (Exception e){
                System.out.println("Hibasan adtad meg mit szerenel!");
                return;
            }
            if (index > arucikk.length || index < 1){
                System.out.println("Nincs ilyen szamu termekukn");
                return;
            }
            if (arucikk[index-1].getErtek()* mennyiseg > f.getArany()){
                System.out.println("NIncs eleg arany");
                return;
            }
            if (arucikk[index-1].getDarabszam() < mennyiseg){
                System.out.println("Sajnos nincs eleg ebbol a termekbol");

            }
            else{
                System.out.println("Sikeres vasarlas");
                Targyak visszakuld = new Targyak();
                if (arucikk[index-1] instanceof Elelmiszer){
                    visszakuld = new Elelmiszer(arucikk[index-1].getNev(),mennyiseg,arucikk[index-1].getErtek(),((Elelmiszer) arucikk[index-1]).getEnergiaErtek());
                }
                else visszakuld = new Eszkoz(arucikk[index-1].getNev(),mennyiseg,arucikk[index-1].getErtek());
                inventory.inventorybaTesz(visszakuld);
                arucikk[index-1].darabszamCsokkentes(mennyiseg);
                f.setArany(f.getArany()-(arucikk[index-1].getErtek()*mennyiseg));
            }


    }
}

import Targyak.Elelmiszer;
import Targyak.Kincs;
import Targyak.Targyak;

import java.util.ArrayList;

/**
 * A felfedezo es a hajo inventoryjat megvalosito osztaly. Egy arraylist-ben vannak eltarolva a Targyak
 * tipusu elemek.
 */
public class Inventory {
    /**
     * Az inventoryban a targyak
     */
    private ArrayList<Targyak> inventory;

    //getterek
    public int getSlotok() {
        int slotok = 0;
        for (Targyak t : inventory){
            if (t.getDarabszam()%7==0){
                slotok+=t.getDarabszam()/7;
            }
            else slotok+=(t.getDarabszam()/7)+1;
        }
        return slotok;
    }
    public int getSize(){
        return inventory.size();
    }
    /**
     * Visszaadja az adott pozicion levo targy osztajat
     * @param index az eszkoz indexe
     * @return az eszkoz osztalya
     */
    public Class getTipus(int index){
        if (index < 0 || index > inventory.size()) return null;
        return inventory.get(index).getClass();
    }

    //setterek
    public Inventory(){
        inventory = new ArrayList<>();
        //slotok = 8;
    }

    /**
     * Egy targyat van amit az inventoryba rakunk
     * @param targy a targy amit az invotoryba koll rakni
     * @see Main
     */
    public void inventorybaTesz(Targyak targy){
        //van mar benne
        if (targy == null) return;
        for (Targyak t : inventory){
            if (t != null){
                if (t.getNev().equals(targy.getNev())){
                    t.setDarabszam(targy.getDarabszam());
                    return;
                }
            }
        }
        inventory.add(targy);

        //nincs meg benne
    }

    /**
     * Kivesz az adott indexen levo targybol az adott mennyiseget
     * @param index a targy indexe
     * @param mennyiseg a targy mennyisege
     * @return a kivett targyat visszaadja
     * @see Main
     */
    public Targyak inventorybolKivesz(int index, int mennyiseg){
        if (inventory.size() == 0) return null;
        String nev = inventory.get(index-1).getNev();
        Targyak visszaad = new Targyak(nev,0,inventory.get(index-1).getErtek());
        while(mennyiseg > 0){
            for (int i = inventory.size()-1; i>= 0; i--){
                if (inventory.get(i).getNev().equals(nev)){
                    if (inventory.get(i).getDarabszam()>mennyiseg){
                        inventory.get(i).setDarabszam(-mennyiseg);
                        visszaad.setDarabszam(mennyiseg);
                        return visszaad;
                    }
                    else if (inventory.get(i).getDarabszam() < mennyiseg){
                        System.out.println("Nincs eleg " + nev + " nalad");
                        return null;
                    }
                    else if(inventory.get(i).getDarabszam() == mennyiseg){
                        visszaad.setDarabszam(mennyiseg);
                        inventory.remove(i);
                        //slotok--;
                        return visszaad;
                    }

                }
            }
        }
        return null;
    }

    /**
     * Kiirja az inventory tartalmat
     * @param tipus ha null-t adunk meg mindent kiir, amugy meg csak az olyan osztalyu targyakat mint amilyet
     *              megadtunk
     * @see Main
     */
    public void inventoryKiir(Targyak tipus){
        if (inventory.size() == 0) {
            System.out.println("Ures az inventory.");
            return;
        }
        System.out.println("Inventory: " + ((getSlotok() > 8)? "8 + " + (getSlotok()-8): getSlotok()));
        int k = 0;
        for (Targyak elem : inventory){
            k++;
            if (tipus != null)
            if (!elem.getClass().equals(tipus.getClass())) continue;

            if (elem.getClass().equals(Kincs.class)){
                for (int i = 0; i < elem.getDarabszam();i++){
                    System.out.println(k+", " +elem.getNev() + " --> " + elem.getErtek() + " arany");
                    //slotok++;
                }
            }
            else if (elem.getDarabszam() > 7){
                for (int i = 0; i < elem.getDarabszam()/7;i++){
                    System.out.println(k+", " +String.format("%-10s",elem.getNev()) + "\t" + 7);
                    //slotok++;
                }
                if ((elem.getDarabszam()-((elem.getDarabszam()/7)*7)!=0)){
                    System.out.println(k+", " +String.format("%-10s",elem.getNev()) + "\t" + (elem.getDarabszam()-((elem.getDarabszam()/7)*7)));
                    //slotok++;
                }

            }
            else{
                System.out.println(k+", " +String.format("%-10s",elem.getNev()) + "\t" + elem.getDarabszam());
                //slotok++;
            }

        }
    }

    /**
     * Megnezi hogy az invetory tartalmazza e a megadott targyat
     * @param nev mit keresunk az inventoryban
     * @return hanyadik indexen van az adott targy, -1 ha nincs
     * @see Main
     */
    public int inventorybanVane(String nev){
        for (int i = 0; i < inventory.size();i++){
            if (inventory.get(i).getNev().equals(nev)){
                return i+1;
            }
        }
        return -1;
    }

    /**
     * Megnezi hogy van e az adott tipusbol az inventoryban
     * @param o milyen tipusbol kell lennie
     * @return van e az adott tipusbol
     * @see Main
     */
    public boolean vanTipus(Object o){
        for (Targyak t : inventory){
            if (t.getClass().equals(o)) return true;
        }
        return false;
    }

    /**
     * Az adott indexen levo targyat Elelmiszerre alakitja majd visszadja
     * @param index az etel indexe
     * @see Main
     * @return a kivalasztot etelt
     */
    public Elelmiszer eszik(int index){
        Elelmiszer etel = (Elelmiszer) inventory.get(index-1);
        inventorybolKivesz(index,1);
        return etel;
    }
}

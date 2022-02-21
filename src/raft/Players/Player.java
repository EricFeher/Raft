package raft.Players;

import java.util.ArrayList;
/** A játékos
 *
 * @author h044630 - Fehér Erik
 */
public class Player extends Object {
    private int hunger;
    private int thirst;
    private int x;
    private int y;
    private int spear;
    private ArrayList<Items> items;
    
    public Player(int x, int y){
        hunger=101;
        thirst=101;
        this.x=x;
        this.y=y;
        spear=0;
        items=new ArrayList<>();
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpear() {
        return spear;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpear(int spear) {
        this.spear = spear;
    }
    
    /** Körönkénti levonás miatt, létrehozott éhség és szomjóság levonó a playertől
     * 
    */
    
    public void actionMinus(){
        hunger--;
        thirst--;
    }
    
     /** Étkezést végrehajtó metódus, az éhség értékét növeli
     * Azért 60+1, meg 101, mert levon 1-et és következő körtől számít a +60
    */
    
    public void eat(){
        if(hunger+61>100){
            hunger=101;
        }
        else{
            hunger+=61;
        }
    }
    
    /** Ivást végrehajtó metódus, az szomjúség értékét növeli
     * Azért 40+1, meg 101, mert levon 1-et és következő körtől számít a +40
    */
    
    public void drink(){
        if(thirst+41>100){
            thirst=101;
        }
        else{
            thirst+=41;
        }
    }
}

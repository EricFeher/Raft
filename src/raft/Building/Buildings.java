package raft.building;

import raft.Resource.Resources;


/** Az építményeket egybefogó osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Buildings extends Object {
    public static int DB=0;
    int x;
    int y;
    public Buildings(int x,int y){
        this.x=x;
        this.y=y;
    }
    
    /** Fireplacehez szükséges metódus
     * 
     * @return igen ha min 25 kör eltelt
    */
    
    public boolean isFireplaceAge25() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getAge(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setEmpty(boolean b, Resources object,int age) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /** Waterpurifierhöz szükséges metódus
     * 
     * @return igen ha min 25 kör eltelt
    */
    public boolean isWaterpurifierAge25() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setAge(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

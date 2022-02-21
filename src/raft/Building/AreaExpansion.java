package raft.building;

/** A hajó bővítésért felelő osztály
 *
 * @author h044630 - Fehér Erik
 */
public class AreaExpansion extends Buildings {
    public static int DB=0;

    public AreaExpansion(int x,int y){
        super(x,y);
        DB++;
    }    
}

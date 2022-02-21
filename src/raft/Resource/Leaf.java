package raft.Resource;

/** Levél osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Leaf extends Resources {
    public static int DB=0;
    
    public Leaf(){
        super();
        DB++;
    }
    
    @Override
    public int getDB() {
        return DB;
    }

    public void setDB(int DB) {
        Leaf.DB = DB;
    }
    
    
}

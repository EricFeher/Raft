package raft.Resource;

/** Krumpli osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Potato extends Resources{
    public static int DB=0;
    
    public Potato(){
        super();
        DB++;
    }
    
    @Override
    public int getDB() {
        return DB;
    }
    
    @Override
    public void setDB(int DB) {
        Potato.DB = DB;
    }
}

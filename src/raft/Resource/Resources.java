package raft.Resource;

/** Nyersanyag osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Resources {
    public static int DB=0;
    public Resources(){
        DB++;
    }
    
    
    public int getDB() {
        return DB;
    }
    
    /**
     *
     * @param DB
     */
    public void setDB(int DB) {
        Resources.DB = DB;
    }
    
}

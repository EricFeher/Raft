package raft.Resource;

/** Szemét osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Trash extends Resources{
    public static int DB=0;
    
    public Trash(){
        super();
        DB++;
    }
    
    @Override
    public int getDB() {
        return DB;
    }
    
    @Override
    public void setDB(int DB) {
        Trash.DB = DB;
    }

}

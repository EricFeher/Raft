package raft.Resource;

/** Deszka osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Plank extends Resources {
    public static int DB=0;
    
    public Plank(){
        super();
        DB++;
    }
    
    @Override
    public int getDB() {
        return DB;
    }

    /**
     *
     * @param DB
     */
    @Override
    public void setDB(int DB) {
        Plank.DB = DB;
    }
    
}

package raft.Resource;

/** Hal osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Fish extends Resources{
    public static int DB=0;
    public Fish(){
        super();
        DB++;
    }
    
    @Override
    public int getDB() {
        return DB;
    }

    @Override
    public void setDB(int DB) {
        Fish.DB = DB;
    }

}

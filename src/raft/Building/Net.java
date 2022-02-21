package raft.building;
/** A háló
 *
 * @author h044630 - Fehér Erik
 */
public class Net extends Buildings{
    public static int DB=0;
    public Net(int x,int y){
        super(x,y);
        DB++;
    }

    public static int getDB() {
        return DB;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void setDB(int DB) {
        Net.DB = DB;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}

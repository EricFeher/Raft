package raft.Players;

import raft.Resource.Resources;

/** A táska osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Items {
    int DB=0;
    Resources r;

    public void setR(Resources r) {
        this.r = r;
    }

    public Resources getR() {
        return r;
    }
    
    
    public Items(Resources r){
        this.r=r;
        DB++;
    }
}
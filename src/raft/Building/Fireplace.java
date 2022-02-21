package raft.building;

import raft.Resource.Resources;

/** A tűzhely
 *
 * @author h044630 - Fehér Erik
 */
public class Fireplace extends Buildings {
    boolean empty;
    Resources r;
    int age;
    public Fireplace(int x,int y){
        super(x,y);
        DB++;
        empty=true;
        age=0;
    }

    @Override
    public void setEmpty(boolean empty, Resources r,int age) {
        this.empty = empty;
        this.r=r;
        this.age=age;
    }
    
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public int getAge() {
        return age;
    }
    
    @Override
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
     public boolean isFireplaceAge25(){
        return age>=25;
    }
}

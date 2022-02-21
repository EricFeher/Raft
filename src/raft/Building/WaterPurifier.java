package raft.building;

/** A víztisztító
 *
 * @author h044630 - Fehér Erik
 */
public class WaterPurifier extends Buildings {
    public static int DB=0;
    private int age;
    public WaterPurifier(int x,int y){
        super(x,y);
        DB++;
        age=0;
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
    public boolean isWaterpurifierAge25(){
        return age>=25;
    }
}

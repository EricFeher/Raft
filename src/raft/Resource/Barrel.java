package raft.Resource;

/** Hordó osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Barrel extends Resources {
    public String r[];
    
    /** A hajó létrehozását segítő metódus
     * 
    */
    public Barrel(){
        r=new String[5];
        int rand=0;
        for(int i=0;i<5;i++){
            rand=(int)(Math.random()*4);
            switch (rand){
                case 0: r[i]="Plank";
                    break;
                case 1: r[i]="Trash";
                    break;
                case 2: r[i]="Leaf";
                    break;
                case 3: r[i]="Potato";
                    break;
                default: System.out.println("Kaka van");
                    break;
            }
        }
    }

    public String[] getR() {
        return r;
    }
}

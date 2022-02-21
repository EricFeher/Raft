package raft.Actions;

import java.util.ArrayList;
import static raft.GameMaster.fails;
import raft.Map.Map;
import raft.Players.*;
import raft.Resource.*;
import raft.building.*;

/** Az akciókért felelő osztály
 *
 * @author h044630 - Fehér Erik
 */
public class Action {
    
    /** Létrehozza a lándzsát a játékosnak
     * @param p - válaszott játékos
    */
    public static void makeSpear(Player p){
        if(enoughMaterial(4,4,4)){
            dropItem(new Leaf(),4,p);
            dropItem(new Plank(),4,p);
            dropItem(new Trash(),4,p);
            p.setSpear(5);
        }
    }
    
    /** A játékos ezzel a metódussal tud halat fogni 25% eséllyel
     * @param al - a játékos trágyai
     * @return A játékos tárgyai kibővítve
    */
    
    public static ArrayList<Items> fishing(ArrayList<Items> al){
        int rand=(int)(Math.random()*4);
        if(rand==0){
            al.add(new Items(new Fish()));
        }
        return al;
    }
    
    /** A játékos körüli tárgyakat veszi fel
     * @param p - a válaszott játékos
     * @param m - a választott térkép
    */
    
    public static void pickUp(Player p, Map m){
        boolean t[]=BorderCheck(p.getX(),p.getY(),m);
        boolean van=false;
        for(int i=0;i<t.length;i++){
            if(t[i]){
                pickUpAction(i,p,m);
                van=true;
                break;
            }
        }
        if(!van){
            pickUpAction(10,p,m);
        }
        
    }
    
    /** Itt történik a kiválasztása a pickUpActionChooser metódus hívásának
     * @param index - Ezzel választjuk ki hogy merre tud felvenni tárgyakat, hogy ne menjen ki a térképről
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
    */
    
    public static void pickUpAction(int index,Player p, Map m){
        //0 - bf | 1 - jf | 2 - ja | 3 - ba | 4 - f | 5 - j | 6 - a | 7 - b | If true >> arra nem
        switch(index){
            case 0:
                pickUpActionChooser(p,m,p.getX(),p.getX()+1,p.getY(),p.getY()+1);
                break;
            case 1:
                pickUpActionChooser(p,m,p.getX(),p.getX()+1,p.getY()-1,p.getY());
                break;
            case 2:
                pickUpActionChooser(p,m,p.getX()-1,p.getX(),p.getY()-1,p.getY());
                break;
            case 3:
                pickUpActionChooser(p,m,p.getX()-1,p.getX(),p.getY(),p.getY()+1);
                break;
            case 4:
                pickUpActionChooser(p,m,p.getX(),p.getX()+1,p.getY()-1,p.getY()+1);
                break;
            case 5:
                pickUpActionChooser(p,m,p.getX()-1,p.getX()+1,p.getY()-1,p.getY());
                break;
            case 6:
                pickUpActionChooser(p,m,p.getX()-1,p.getX(),p.getY()-1,p.getY()+1);
                break;
            case 7:
                pickUpActionChooser(p,m,p.getX()-1,p.getX()+1,p.getY(),p.getY()+1);
                break;
            case 10:
                pickUpActionChooser(p,m,p.getX()-1,p.getX()+1,p.getY()-1,p.getY()+1);
                break;
            default:
                System.out.println("Nem jó a pickUpAction");
                break;
                
        }
    }
    
    /** Itt hívja meg a megfelelő helyre a pickUpItemGenerator metódust
     * @param Koordináták - Koordináta szabályozók hogy mettől meddig mehet a ciklus
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
    */
    
    public static void pickUpActionChooser(Player p, Map m, int x1, int x2,int y1,int y2){
        ArrayList<Items> items=p.getItems();
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                if(m.getLandMap()[i][j]!=1){
                    items=pickUpItemGenerator(p,m,items,i,j);
                    String resourcesMap[][]=m.getResourcesMap();
                    resourcesMap[i][j]="0";
                    m.setResourcesMap(resourcesMap);
                }
            }
        }
        p.setItems(items);
    }
    
    /** Létrehozza a tárgyakat objektumként
     * @param items - A játékos már ellőte kezelt itemjei
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @return - a Játékos már előtte kezelt itemjei
    */
    
    public static ArrayList<Items> pickUpItemGenerator(Player p, Map m, ArrayList<Items> items,int i,int j){
        switch(m.getResourcesMap()[i][j]){
            case "Plank":
                items.add(new Items(new Plank()));
                break;
            case "Trash":
                items.add(new Items(new Trash()));
                break;
            case "Leaf":
                items.add(new Items(new Leaf()));
                break;
            case "Barrel":
                Barrel b=new Barrel();
                for(int a=0;a<5;a++){
                    switch(b.r[a]){
                        case "Plank":
                            items.add(new Items(new Plank()));
                            break;
                        case "Trash":
                            items.add(new Items(new Trash()));
                            break;
                        case "Leaf":
                            items.add(new Items(new Leaf()));
                            break;
                        case "Potato":
                            items.add(new Items(new Potato()));
                            break;
                    }
                }
                break;
            case "0":
                break;
            default:
                System.out.println("Rossz a pickUpAction");
                break;
        }
        return items;
    }
    
    /** Leellnőrzi hogy az adott helyen van a háló, tűzhely, víztisztító és
     * és elvégzi a hozzájuk tartozó műveleteket
     * @param player - Kiválasztot játékos
     * @param map - Kiválaszott térkép
    */
    
    public static void buildingsCheck(Player player, Map map){
        for(int i=0;i<map.getbMap().length;i++){
            for(int j=0;j<map.getbMap()[i].length;j++){
                if(map.getbMap()[i][j]!=null){
                    if(map.getBuildingsMap()[i][j].equals("F")){
                        if(!map.getbMap()[i][j].isEmpty()){
                            map.getbMap()[i][j].setAge(map.getbMap()[i][j].getAge()+1);
                        }
                    }
                    else if(map.getBuildingsMap()[i][j].equals("W")){
                        map.getbMap()[i][j].setAge(map.getbMap()[i][j].getAge()+1);
                    }
                    else if(map.getBuildingsMap()[i][j].equals("N")){
                        player.setItems(Action.pickUpItemGenerator(player, map, player.getItems(), i, j));
                        String m[][]=map.getResourcesMap();
                        m[i][j]="0";
                        map.setResourcesMap(m);
                    }
                }
            }
        }
    }
    
    /** A map széleit ellenőrzi, hogy kimegyünk e belőle
     * @param x
     * @param y
     * A kiválaszott koordináták körül nézi
     * @param m - Kiválaszott térkép
     * @return egy boolean tömb ami alapján a többi fügvényben el tudjuk dönteni hogy merre mehet
    */
    
    public static boolean[] BorderCheck(int x ,int y, Map m){
        boolean t[]=new boolean[8];
        //0 - bf | 1 - jf | 2 - ja | 3 - ba | 4 - f | 5 - j | 6 - a | 7 - b | If true >> arra nem
        if(y==0 && x==0){
            t[0]=true;
        }
        if(y==m.getMapY()-1 && x==0){
            t[1]=true;
        }
        if(y==m.getMapY()-1 && x==m.getMapX()-1){
            t[2]=true;
        }
        if(y==0 && x==m.getMapX()-1){
            t[3]=true;
        }
        if(x==0){
            t[4]=true;
        }
        if(y==m.getMapY()-1){
            t[5]=true;
        }
        if(x==m.getMapX()-1){
            t[6]=true;
        }
        if(y==0){
            t[7]=true;
        }
        return t;
    }
    
    /** A játékos ezzel a metódussal tud mozogni,ez végzi a valós mozgást
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @param x
     * @param y
     * Ezekkel a paraméterekkel döntöd el hogy merre menjenek
    */
    
    public static void moveButReally(Player p, Map m,int x, int y){
            if(!m.getBuildingsMap()[p.getX()+x][p.getY()+y].equals("N")){
                int playerMap[][]=m.getPlayerMap();
                playerMap[p.getX()][p.getY()]=0;
                p.setX(p.getX()+x);
                p.setY(p.getY()+y);
                playerMap[p.getX()][p.getY()]=10;
                m.setPlayerMap(playerMap);
            }
            else{
                fails.setText("A HÁLÓRA NEM LÉPÜNK RÁ");
            }
    }
    
    /** A játékos ezzel a metódussal választja ki hogy merre mozogjon
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @param where - Merre menjen
    */

    public static void move(String where,Player p, Map m){
        switch(where){
            case "bf":
                moveButReally(p,m,-1,-1);
            break;
            case "jf":
                moveButReally(p,m,-1,1);
            break;
            case "ja":
                moveButReally(p,m,1,1);
            break;
            case "ba":
                moveButReally(p,m,1,-1);
            break;
            case "f":
                moveButReally(p,m,-1,0);
            break;
            case "j":
               moveButReally(p,m,0,1);
            break;
            case "a":
                moveButReally(p,m,1,0);
            break;
            case "b":
                moveButReally(p,m,0,-1);
            break;
            default:
                System.out.println("Ilyen irány nincs");
            break;
        }
            
            
    }
    
    /** A játékos ezzel a metódussal választja ki hova építsen
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @param b - Ezzel választja ki hogy mit építsen
     * @param where - Ezzel választja ki hogy hova építsen
    */
    
    public static void build(String b,String where,Player p, Map m){
        switch(where){
            case "bf":
                buildPlace(b,p,m,p.getX()-1,p.getY()-1);
            break;
            case "jf":
                buildPlace(b,p,m,p.getX()-1,p.getY()+1);
            break;
            case "ja":
                buildPlace(b,p,m,p.getX()+1,p.getY()+1);
            break;
            case "ba":
                buildPlace(b,p,m,p.getX()+1,p.getY()-1);
            break;
            case "f":
                buildPlace(b,p,m,p.getX()-1,p.getY());
            break;
            case "j":
               buildPlace(b,p,m,p.getX(),p.getY()+1);
            break;
            case "a":
                buildPlace(b,p,m,p.getX()+1,p.getY());
            break;
            case "b":
                buildPlace(b,p,m,p.getX(),p.getY()-1);
            break;
            default:
                System.out.println("Ilyen irány nincs");
            break;
        }
    }
    
    /** A játékos ezzel a metódussal tud ennivalót sütni
     * @param f - Amire helyezzük a sütnivalót (Fireplace)
     * @param al - A player itemei
     * @param whatIWantToEat - Amit enni akarunk (Krumpli/Hal)
     * @return - A player itemei miután kivettük belőle a kaját
    */
    
    public static ArrayList<Items> cook(Buildings f, ArrayList<Items> al,Resources whatIWantToEat){ //WhatIWantToEat helyére majd egy new Potato()/new Fish()
        boolean siker=false;
        for(int i=0;i<al.size();i++){
            if((al.get(i).getR().getClass()+"").equals(whatIWantToEat.getClass()+"")){
                f.setEmpty(false, al.get(i).getR(),0);
                siker=true;
                al.remove(i--);
                Resources.DB-=2; //Lehet hibás majd nézd meg futásnál
                whatIWantToEat.setDB(whatIWantToEat.getDB()-2);
                break;
            }
        }
        if(!siker){
            System.out.println("Nincs potato se halacska");
        }
        return al;
    }
    
    /** A játékos ezzel a metódussal mondja meg hogy hova nem tud építeni.
     * @param building - Az építmény amit építeni akar
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @param x
     * @param y
     * Ezekkel a paraméterekkel dönti el hogy hova építsen
    */

    public static void buildPlace(String building, Player p, Map m, int x,int y){
        System.out.println(x+" "+y);
        if(m.getbMap()[x][y]==null){
            if(x==p.getX() && y==p.getY()){
                fails.setText("Magam alá nem tudok építeni");
                return;
                }    
            buildHelp(building,x,y,p,m);
            }
        else{
            fails.setText("Itt már van egy építmény.");
        }
    }
    
    /** Ezzel a metódussal választja ki hogy mit tud és hova mennyiért építeni
     * @param building - Az építmány amit építeni akar
     * @param p - Kiválasztot játékos
     * @param m - Kiválaszott térkép
     * @param x
     * @param y
     * Ezekkel a paraméterekkel dönti el hogy hova építsen
    */
    
    public static void buildHelp(String building,int x,int y, Player p, Map m){
        Buildings b = null;
        int lMap[][]=m.getLandMap();
        int wMap[][]=m.getWaterMap();
        String bMap[][]=m.getBuildingsMap();
        switch(building){
            case "AreaExpansion":
                if(enoughMaterial(2,2,0)){
                    if(m.getWaterMap()[x][y]==1){
                        if(buildCheck(x,y,m)){
                            if(m.getSharkMap()[x][y]!=666){
                                wMap[x][y]=0;
                                lMap[x][y]=1;
                                dropItem(new Plank(),2,p);
                                dropItem(new Leaf(),2,p);
                            }else{
                                fails.setText("Csóró cápa, tudom meg akar ölni, de ne építs már rá.");
                            }
                        }else{
                             fails.setText("A hajót csak már a meglévő hajó mellett tudod bővíteni.");
                        }
                    }else{
                        fails.setText("A hajót csak vízre bővítheted.");
                    }
                }else{
                    fails.setText("Nincs elég nyersanyagod a hajód bővíteni.");
                }
                break;
            case "FirePlace": 
                if(enoughMaterial(2,4,3)){
                    if(m.getLandMap()[x][y]==1){
                        b=new Fireplace(x,y);
                        bMap[x][y]="F";
                        dropItem(new Plank(),2,p);
                        dropItem(new Leaf(),4,p);
                        dropItem(new Trash(),3,p);
                    }else{
                        fails.setText("Tűzhelyet csak hajóra lehet rakni.");
                    }
                }else{
                    fails.setText("Nincs elég nyersanyagod tűzhelyet építeni.");
                }
                break;
            case "WaterPurifier": 
                if(enoughMaterial(2,0,4)){
                    if(m.getLandMap()[x][y]==1){
                        b=new WaterPurifier(x,y);
                        bMap[x][y]="W";
                        dropItem(new Plank(),2,p);
                        dropItem(new Trash(),4,p);
                        
                    }else{
                        fails.setText("Víztisztítót csak hajóra lehet rakni.");
                    }
                }else{
                    fails.setText("Nincs elég nyersanyagod víztisztítót építeni.");
                }
                break;
            case "Net": 
                if(enoughMaterial(2,6,0)){
                    if(m.getWaterMap()[x][y]==1){
                        if(buildCheck(x,y,m)){
                            if(m.getSharkMap()[x][y]!=666){
                                b=new Net(x,y);
                                bMap[x][y]="N";
                                dropItem(new Plank(),2,p);
                                dropItem(new Leaf(),6,p);
                            }else{
                                fails.setText("Csóró cápa, tudom meg akar ölni, de ne építs már rá.");
                            }
                        }else{
                            fails.setText("Hálót csak hajó mellé lehet építeni.");
                        }
                    }else{
                        fails.setText("Hálót csak vízre lehet építeni");
                    }
                }else{
                    fails.setText("Nincs elég nyersanyagod hálót építeni.");
                }
                break;
            default: System.out.println("Kaka van az építésnél");
                break;
        }
        Buildings bOMap[][]=m.getbMap();
        bOMap[x][y]=b;
        m.setbMap(bOMap);
        m.setWaterMap(wMap);
        m.setLandMap(lMap);
        m.setBuildingsMap(bMap);
    }
    
    /** Ezzel a metódussal mondhatjuk meg hogy mire akarunk építeni
     * @param m - Kiválaszott térkép
     * @param x
     * @param y
     * Ezekkel a paraméterekkel nézi meg hova tud építeni (+1,-1)
     * @return 
    */
    
    public static boolean buildCheck(int x,int y, Map m){
        boolean t[]=BorderCheck(x,y,m);
        //0 - bf | 1 - jf | 2 - ja | 3 - ba | 4 - f | 5 - j | 6 - a | 7 - b | If true >> arra nem
        boolean ok=false;
        if(t[0]){
            if(m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y+1]==1){
                ok=true;
            }
        }else if(t[1]){
            if(m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y-1]==1){
                ok=true;
            }
        }else if(t[2]){
            if(m.getLandMap()[x-1][y]==1 || m.getLandMap()[x][y-1]==1){
                ok=true;
            }
        }else if(t[3]){
            if(m.getLandMap()[x-1][y]==1 || m.getLandMap()[x][y+1]==1){
                ok=true;
            }
        }else if(t[4]){
            if(m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y+1]==1 || m.getLandMap()[x][y-1]==1){
                ok=true;
            }
        }else if(t[5]){
            if(m.getLandMap()[x-1][y]==1 || m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y-1]==1){
                ok=true;
            }
        }else if(t[6]){
            if(m.getLandMap()[x-1][y]==1 || m.getLandMap()[x][y+1]==1 || m.getLandMap()[x][y-1]==1){
                ok=true;
            }
        }else if(t[7]){
            if(m.getLandMap()[x-1][y]==1 || m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y+1]==1){
                ok=true;
            }
        }else{
            if(m.getLandMap()[x+1][y]==1 || m.getLandMap()[x][y+1]==1 || m.getLandMap()[x][y-1]==1 || m.getLandMap()[x-1][y]==1){
                ok=true;
            }
        }
        return ok;
    }
    
    /** Megmondja hogy elég-e a nyersanyag egy adott építményhez
     * 
     * @param plank - deszka száma
     * @param leaf - levél száma
     * @param trash - szemét száma
     * @return Elég e vagy nem elég
    */
    
    public static boolean enoughMaterial(int plank,int leaf, int trash){
        return plank<=Plank.DB && leaf<=Leaf.DB && trash<=Trash.DB;
    }
    
    /** Ezzel a metódussal dobjuk ki a felhasznált itemeket
     * 
     * @param item - Melyik itemet dobjuk ki
     * @param qt - Mennyit dobunk ki
     * @param p - Melyik player itemjeiből dobunk ki
    */
    
    public static void dropItem(Resources item, int qt, Player p){
        Resources.DB--;
        item.setDB(item.getDB()-1);
        int db=0;
        for(int i=0;i<p.getItems().size();i++){
            if((item.getClass()+"").equals((p.getItems().get(i).getR().getClass()+""))){
                Resources.DB--;
                item.setDB(item.getDB()-1);
                p.getItems().remove(i--);
                db++;
            }
            if(db==qt){
                break;
            }
        }
    }
    
    /** Ez a metódus felel a cápa mozgatásáért a hajó körül (Igen a hajó körül, 
     * mert soha nem ér a hajóhoz és az egész map a hajó körül van[Máskor please jobban írják meg az instrukciókat a feladathoz])
     * @param s - Kiválasztott cápa
     * @param m - Kiválaszott térkép
    */
    
    public static void sharkMove(Shark s,Map m){
        boolean ok=true;
        int sMap[][]=m.getSharkMap();
        sMap[s.getX()][s.getY()]=0;
        while(ok){
            int x=(int)(Math.random()*26);
            int y=(int)(Math.random()*36);
            if(m.getLandMap()[x][y]!=1 && !m.getBuildingsMap()[x][y].equals("N")){
                sMap[x][y]=666;
                s.setX(x);
                s.setY(y);
                m.setSharkMap(sMap);
                ok=false;
            }
        }
    }
}

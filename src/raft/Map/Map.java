package raft.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import raft.Players.*;
import raft.Players.Player;
import raft.building.Buildings;

/** A térképekért felelős osztály
 *
 * @author h044630 - Fehér Erik
 */
public final class Map {
    int mapX=26;
    int mapY=36;
    String resourcesMap[][];
    String buildingsMap[][];
    int playerMap[][];
    int sharkMap[][];
    int waterMap[][];
    int landMap[][];
    JLabel map[][];
    Buildings bMap[][];
    
    public Map(Player p, Shark s){
        playerMap=new int[mapX][mapY];
        sharkMap=new int[mapX][mapY];
        waterMap=new int[mapX][mapY];
        bMap=new Buildings[mapX][mapY];
        resourcesMap=createMap();
        buildingsMap=createMap();
        playerMap[p.getX()][p.getY()]=10;
        sharkMap[s.getX()][s.getY()]=666;
        landMap=createLandMap();
        moveResource();
        map=drawMap(p);
    }

    public void setbMap(Buildings[][] bMap) {
        this.bMap = bMap;
    }

    public Buildings[][] getbMap() {
        return bMap;
    }
    /** A map kirajzolás előkészületéhez szükséges metódus
     * @param p - Kiválaszott player, kell mert így tudjuk meg hogy van e lándzsája
     * @return sok kis kép amit összerak.
    */
    public JLabel[][] drawMap(Player p){
        JLabel[][] result=new JLabel[mapX][mapY];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++){
                result[i][j]=new JLabel();
                if(this.playerMap[i][j]==10 && this.sharkMap[i][j]==666 && p.getSpear()>0){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/nodie.jpg"));
                }
                else if(this.playerMap[i][j]==10 && this.sharkMap[i][j]==666){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/die.jpg"));
                }
                else if(this.playerMap[i][j]==10){
                    if(this.waterMap[i][j]==1){
                        result[i][j].setIcon(new ImageIcon("src/raft/pictures/characteruw.jpg"));
                    }
                    else{
                        result[i][j].setIcon(new ImageIcon("src/raft/pictures/character.jpg"));
                    }
                    
                }
                else if(this.sharkMap[i][j]==666){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/shark.jpg"));
                    waterMap[i][j]=1;
                }
                else if(this.buildingsMap[i][j].equals("F")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/fireplace.jpg"));
                }
                else if(this.buildingsMap[i][j].equals("N")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/net.jpg"));
                }
                else if(this.buildingsMap[i][j].equals("W")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/waterpurifier.jpg"));
                }
                else if(this.landMap[i][j]==1){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/land.jpg"));
                }
                else if(this.resourcesMap[i][j].equals("Barrel")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/barrel.jpg"));
                }
                else if(this.resourcesMap[i][j].equals("Leaf")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/leaf.jpg"));
                }
                else if(this.resourcesMap[i][j].equals("Plank")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/plank.jpg"));
                }
                else if(this.resourcesMap[i][j].equals("Trash")){
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/trash.jpg"));
                }
                else {
                    this.waterMap[i][j]=1;
                    result[i][j].setIcon(new ImageIcon("src/raft/pictures/water.jpg"));
                }
            }
        }
        return result;
    }
    
    /** A hajó létrehozását segítő metódus
     * 
    */
    
    private int[][] createLandMap(){
        int result[][]=new int[mapX][mapY];
        for(int i=12;i<=13;i++){
            for(int j=17;j<=18;j++){
                result[i][j]=1;
            }
        }
        return result;
    }
    
    /** A térképek létrehozását segítő metódus
     * 
    */
    
    private String[][] createMap(){
        String result[][]=new String[mapX][mapY];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++){
                result[i][j]="0";
            }
        }
        return result;
    }
    
    
    /** A nyersanyagok fentről lefele mozgatásáért felelő metódus
     * 
    */
    
    public void moveResource(){
        for(int i=this.resourcesMap.length-2;i>=0;i--){
            this.resourcesMap[i+1]=this.resourcesMap[i];
        }
        this.resourcesMap[0]=resourceSpawning();
    }
    
    /** A nyersanyagok létrejöttéért felelő metódus
     * 
    */
    
    private String[] resourceSpawning(){
        String[] result=new String[mapY];
        for(int i=0;i<result.length;i++){
            result[i]="0";
        }
        int szam=0;
        int max=(int)(Math.random()*4);
        int index=(int)(Math.random()*36);
        //System.out.println(max);
        int t[]=new int[max];
        boolean jo;
        for(int i=0;i<max;i++){
            jo=true;
            t[i]=index;
            if(0!=i){
                while(jo){
                    jo=false;
                    index=(int)(Math.random()*36);
                    szam=(int)(Math.random() * 100);
                    //System.out.println(szam);
                    for(int j=0;j<=i;j++){
                        if(t[i-1]==index){
                            jo=true;
                            break;
                        }
                    }
                }
                //System.out.println("Kuki1");
            }
            else{
               szam=(int)(Math.random() * 100);
                //System.out.println("Kuki "+szam);
            }
            if(szam>95){
                result[index]="Barrel";
            }
            else if(szam>63){
                result[index]="Plank";
            }
            else if(szam>31){
                result[index]="Leaf";
            }
            else if(szam>=0){
                result[index]="Trash";
            }
        }
        /*for(int i=0;i<result.length;i++){
            System.out.print(result[i]+" ");
        }
        System.out.println("");*/
        return result;
    }
    
    public String[][] getResourcesMap() {
        return resourcesMap;
    }

    public String[][] getBuildingsMap() {
        return buildingsMap;
    }

    public int[][] getPlayerMap() {
        return playerMap;
    }

    public int[][] getSharkMap() {
        return sharkMap;
    }

    public int[][] getWaterMap() {
        return waterMap;
    }

    public JLabel[][] getMap() {
        return map;
    }

    public void setResourcesMap(String[][] resourcesMap) {
        this.resourcesMap = resourcesMap;
    }

    public void setBuildingsMap(String[][] buildingsMap) {
        this.buildingsMap = buildingsMap;
    }

    public void setPlayerMap(int[][] playerMap) {
        this.playerMap = playerMap;
    }

    public void setSharkMap(int[][] sharkMap) {
        this.sharkMap = sharkMap;
    }

    public void setWaterMap(int[][] waterMap) {
        this.waterMap = waterMap;
    }

    public void setMap(JLabel[][] map) {
        this.map = map;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public void setLandMap(int[][] landMap) {
        this.landMap = landMap;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int[][] getLandMap() {
        return landMap;
    }
    
    
}

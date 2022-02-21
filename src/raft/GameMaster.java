package raft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import raft.Actions.Action;
import raft.Map.Map;
import raft.Players.*;
import raft.Resource.*;


/** A játék handlerje
 *
 * @author h044630 - Fehér Erik
 */
public final class GameMaster implements ActionListener{
    private Player player;
    private Shark shark;
    private Map map;
    private int rounds;
    private boolean move,build,fireplace,waterpurifier,land,net;
    private JFrame f;
    private JPanel pn;
    private JPanel pn2;
    private JLabel lb[][];
    private JPanel pn2Buttons;
    private JPanel pn2Messages;
    private JButton btMove,btUse,btFishing,btBuild;
    private JButton btMoveArray[][];
    private JButton btFireplace,btWaterpurifier,btNet,btLand,btSpear;
    private JButton btPotato,btFish,btDrink,btEat;
    public static JLabel fails, data;
    
    public GameMaster() throws InterruptedException{
        game();
    }
    
    /** A játékot elindító függvény
     * @throws java.lang.InterruptedException
    */
    
    public void game() throws InterruptedException{
        rounds=0;
        player=new Player(12,17);
        shark=new Shark(11,17);
        map=new Map(player,shark);
        gui();
        afterAction();
    }
    
    /** A grafikus felületért felelős függvény
    */
    
    public void gui(){
        f=new JFrame();
        pn=new JPanel();
        pn2=new JPanel();
        pn2Buttons=new JPanel();
        pn2Messages=new JPanel();
        btMove=new JButton("Mozog");
        btUse=new JButton("Használ");
        btFishing=new JButton("Horgász");
        btBuild=new JButton("Épít");
        btFireplace=new JButton("Tűzhely");
        btSpear=new JButton("Lándzsa");
        btWaterpurifier=new JButton("Víztisztító");
        btNet=new JButton("Háló");
        btLand=new JButton("Hajó bővítés");
        btPotato=new JButton("Krumpli");
        btFish=new JButton("Hal");
        btEat=new JButton("Eszik");
        btDrink=new JButton("Iszik");
        btMoveArray=new JButton[3][3];
        data=new JLabel(" Adatok");
        fails=new JLabel(" Fails");
        String t[][]={{"Balra-Fel","Fel","Jobbra-Fel"},{"Balra","Semmi","Jobbra"},{"Balra-Le","Le","Jobbra-Le"}};
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                btMoveArray[i][j]=new JButton(t[i][j]);
                btMoveArray[i][j].setVisible(false);
                btMoveArray[i][j].addActionListener(this);
                pn2Buttons.add(btMoveArray[i][j]);
            }
        }
        pn2Buttons.setBackground(Color.yellow);
        f.setSize(1500, 1000);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.add(pn,BorderLayout.CENTER);
        f.add(pn2,BorderLayout.SOUTH);
        pn.setFocusable(true);
        pn.setBackground(Color.black);
        pn.setSize(1500,500);
        pn.setVisible(true);
        pn.setLayout(new GridLayout(27,37,0,0));
        pn2.setMaximumSize(new Dimension(1000,100));
        pn2.setBackground(Color.white);
        pn2.setLayout(new GridLayout(1,2));
        pn2.add(pn2Buttons);
        pn2Buttons.add(btMove);
        pn2Buttons.add(btUse);
        pn2Buttons.add(btFishing);
        pn2Buttons.add(btBuild);
        pn2Buttons.add(btSpear);
        pn2Buttons.add(btFireplace);
        pn2Buttons.add(btWaterpurifier);
        pn2Buttons.add(btNet);
        pn2Buttons.add(btLand);
        pn2Buttons.add(btPotato);
        pn2Buttons.add(btFish);
        pn2Buttons.add(btEat);
        pn2Buttons.add(btDrink);
        btSpear.setVisible(false);
        btFireplace.setVisible(false);
        btWaterpurifier.setVisible(false);
        btNet.setVisible(false);
        btLand.setVisible(false);
        btPotato.setVisible(false);
        btFish.setVisible(false);
        btEat.setVisible(false);
        btDrink.setVisible(false);
        pn2.add(pn2Messages);
        btMove.addActionListener(this);
        btBuild.addActionListener(this);
        btUse.addActionListener(this);
        btFishing.addActionListener(this);
        btSpear.addActionListener(this);
        btFireplace.addActionListener(this);
        btWaterpurifier.addActionListener(this);
        btNet.addActionListener(this);
        btLand.addActionListener(this);
        btFish.addActionListener(this);
        btPotato.addActionListener(this);
        btDrink.addActionListener(this);
        btEat.addActionListener(this);
        pn2Messages.setLayout(new GridLayout(2,1));
        pn2Messages.add(data);
        pn2Messages.add(fails);
        
    }
    
    /** Minden körben ez frissíti a játék állását
     * @throws java.lang.InterruptedException
    */
    
    public void roundRefresh() throws InterruptedException{
        guiRound();
        gameDieCheck();
        buttonsEnable();
        Action.buildingsCheck(player, map);
    }
    
    /** A körönkénti grafikus elemek frissítéséért felelős függvény
    */
    
    public void guiRound(){
        lb=map.drawMap(player);
        pn.removeAll();
        for(int i=0;i<lb[0].length+1;i++){
            pn.add(new JLabel(i+""));
        }
        for(int i=0;i<lb.length;i++){
            if(i>0){
                pn.add(new JLabel(i+""));
            }
            for(int j=0;j<lb[i].length;j++){
                pn.add(lb[i][j]);
            }
        }
        
        pn.add(new JLabel(lb[0].length+""));
        pn.revalidate();
        pn.repaint();
    }
    
    /** Ha  a játékban nem tudunk valamit csinálni,
     * ezzel a függvénnyel egyszerre tilthatjuk le azokat
    */
    
    public void buttonsEnable(){
        if(map.getWaterMap()[player.getX()][player.getY()]==1){
            btFishing.setEnabled(true);
            btBuild.setEnabled(false);
        }
        else{
            btFishing.setEnabled(false);
            btBuild.setEnabled(true);
        }
        
        if(map.getbMap()[player.getX()][player.getY()]==null){
            btUse.setEnabled(false);
        }
        else btUse.setEnabled(true);
    }
    
    /** A függvény ellenőrzi, hogy
     * vége-e a játéknak
    */
    
    public void gameDieCheck(){
        if(rounds==1000){
            pn2Buttons.removeAll();
            pn2Buttons.add(new JLabel("Nyertél! Vége a játéknak elérted az 1000 kört."));
        }
        if(map.getSharkMap()[player.getX()][player.getY()]==666 && player.getSpear()>0){
            fails.setText("Figyelj oda! Visszaverted a cápa támadást, de már nem sokat bírsz, amint visszaérsz a hajódra javítsd meg a lándzsád.");
        }
        else if(map.getSharkMap()[player.getX()][player.getY()]==666){
            pn2Buttons.removeAll();
            pn2Buttons.add(new JLabel("Meghaltál, mert megölt a cápa."));
        }
        if(player.getThirst()<=0){
            pn2Buttons.removeAll();
            pn2Buttons.add(new JLabel("Meghaltál szomjúságban."));
        }
        if(player.getHunger()<=0){
            pn2Buttons.removeAll();
            pn2Buttons.add(new JLabel("Meghaltál éhségben."));
        }
    }
    
    /** Ez a metódus léptet egy kört minden akció után
    */
    
    public void afterAction() throws InterruptedException{
        rounds++;
        player.actionMinus();
        map.moveResource();
        Action.pickUp(player,map);
        Action.sharkMove(shark, map);
        map.setMap(map.drawMap(player));
        roundRefresh();
        data.setText("Krumpli: "+Potato.DB+" Szemét: "+Trash.DB+" Deszka: "+Plank.DB+" Levél: "+Leaf.DB+" Hal: "+Fish.DB+" Lándzsa: "+player.getSpear()+" Hidratáltság: "+player.getThirst()+" Éhség: "+player.getHunger()+" Kör: "+rounds+"/1000");
    }
    
    /** Előhozza a mozgató gombokat
    */
    
    public void guiMove(){ //x ,y player positions
        guiBasicButtonsSetVisible(false);
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
               if(player.getX()+i>=0 && player.getY()+j>=0 && player.getX()+i<map.getMapX() && player.getY()+j<map.getMapY() && (i!=0 || j!=0)){
                   btMoveArray[i+1][j+1].setVisible(true);
                } 
            }
        }
    }
    
    /** Előhozza a építmény gombokat
    */
    
    public void whatToBuild(){
        guiBasicButtonsSetVisible(false);
        btSpear.setVisible(true);
        btFireplace.setVisible(true);
        btWaterpurifier.setVisible(true);
        btLand.setVisible(true);
        btNet.setVisible(true);
    }
    
    /** Előhozza a hova építsünk gombokat
    */
    
    public void whereToBuild(){
        guiBasicButtonsSetVisible(false);
        btSpear.setVisible(false);
        btFireplace.setVisible(false);
        btWaterpurifier.setVisible(false);
        btLand.setVisible(false);
        btNet.setVisible(false);
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
               if(player.getX()+i>=0 && player.getY()+j>=0 && player.getX()+i<map.getMapX() && player.getY()+j<map.getMapY() && (i!=0 || j!=0)){
                   btMoveArray[i+1][j+1].setVisible(true);
                } 
            }
        }
    }
    
    /** Elrejti a mozgató gombokat
    */
    
    public void guiMoveSetVisibleOff(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                btMoveArray[i][j].setVisible(false);
            }
        }
    }
    
    /** Beállíthatjuk hogy az alap gombokat eltűntessük vagy megjelenítsük.
     * @param b - igen -> Megjelen | nem -> Eltűntet
    */
    
    public void guiBasicButtonsSetVisible(boolean b){
        btMove.setVisible(b);
        btFishing.setVisible(b);
        btUse.setVisible(b);
        btBuild.setVisible(b);
    }
    
    /** Építés gui részéért felelő metódus, ami meghívja az
     * építésért felelős függvényt, és a gombokat megjelenési tulajdonságait állítja be
     * @param way - Hova építsen
     * @throws java.lang.InterruptedException
    */
    
    public void build(String way) throws InterruptedException{
        String b="";
        if(fireplace){
            b="FirePlace";
            fireplace=false;
        }else if(waterpurifier){
            b="WaterPurifier";
            waterpurifier=false;
        }else if(land){
            b="AreaExpansion";
            land=false;
        }else if(net){
            b="Net";
            net=false;
        }
        Action.build(b,way, player, map);
        guiBasicButtonsSetVisible(true);
        guiMoveSetVisibleOff();
        afterAction();
        build=false;
        
        btSpear.setVisible(false);
        btFireplace.setVisible(false);
        btWaterpurifier.setVisible(false);
        btLand.setVisible(false);
        btNet.setVisible(false);
    }
    
    /** Mozgás gui részéért felelő metódus, ami meghívja az
     * mozgásért felelős függvényt, és a gombokat megjelenési tulajdonságait állítja be
     * @param way - Merre menjen
     * @throws java.lang.InterruptedException
    */
    
    public void move(String way) throws InterruptedException{
        Action.move(way, player, map);
        guiBasicButtonsSetVisible(true);
        guiMoveSetVisibleOff();
        afterAction();
        move=false;
    }
    
    /** Ha a karakterünk egy adott helyre lép,
     * a megfelelő gombokat fogja láthatóvá tenni,
    */
    
    public void stepInBuilding(){
    if(map.getBuildingsMap()[player.getX()][player.getY()].equals("F")){
        if(map.getbMap()[player.getX()][player.getY()].isEmpty()){
            if(Fish.DB>0){
                guiBasicButtonsSetVisible(false);
                btFish.setVisible(true);
            }
            if(Potato.DB>0){
                guiBasicButtonsSetVisible(false);
                btPotato.setVisible(true);
            }
            if(Potato.DB==0 && Fish.DB==0) {
                fails.setText("Nincs se halad se krumplid amit meg tudnál sütni.");
            }
        }
        else if(map.getbMap()[player.getX()][player.getY()].isFireplaceAge25()){
            guiBasicButtonsSetVisible(false);
            btEat.setVisible(true);
        }
        else{
            fails.setText("Még "+(25-map.getbMap()[player.getX()][player.getY()].getAge())+" kör van amíg elkészül.");
        }
    }
    else if(map.getBuildingsMap()[player.getX()][player.getY()].equals("W")){
        if(map.getbMap()[player.getX()][player.getY()].isWaterpurifierAge25()){
            guiBasicButtonsSetVisible(false);
            btDrink.setVisible(true);
        }
        else{
            fails.setText("Még "+(25-map.getbMap()[player.getX()][player.getY()].getAge())+" kör van amíg lesz elég víz.");
        }
    }
}
    
    /** Különböző akciókért felelős függvény
     * @param ae - Adott akció
    */
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        basicEvents(ae);
        wayEvents(ae);
        buildingEvents(ae);
        useEvents(ae);
    }
    
    /** Alap gombok akcióiért felelős függvény
     * @param ae - Adott akció
    */
    
    public void basicEvents(ActionEvent ae){
        try{
        switch(ae.getActionCommand()){
            case "Mozog":
                    guiMove();
                    move=true;
                break;
            case "Horgász":
                    player.setItems(Action.fishing(player.getItems()));
                    afterAction();
                break;
            case "Használ":
                stepInBuilding();
                break;
            case "Épít":
                    whatToBuild();
                    build=true;
                break;
            }
        } catch(InterruptedException e){    
            System.out.println("basicEvents");
        }
    }
    
    /** "Merre" gombok akcióiért felelős függvény
     * @param ae - Adott akció
    */
    
    public void wayEvents(ActionEvent ae){
        switch(ae.getActionCommand()){
            case "Balra-Fel":
                wayEventsHelper("bf");
                break;
            case "Fel":
                wayEventsHelper("f");
                break;
            case "Jobbra-Fel":
                wayEventsHelper("jf");
                break;
            case "Balra":
                 wayEventsHelper("b");
                break;
            case "Jobbra":
                wayEventsHelper("j");
                break;
            case "Balra-Le":
                wayEventsHelper("ba");
                break;
            case "Le":
                wayEventsHelper("a");
                break;
            case "Jobbra-Le":
                wayEventsHelper("ja");
                break;
            }
    }
    
    /** "Merre" gombok akcióiért felelős függvény segítője
     * Az építés és a mozgás közötti választásért felel
     * @param way - Merre építsen/menjen
    */
    
    public void wayEventsHelper(String way){
        try{
            if(move){
            move(way);
            }else if(build){
                build(way);
            }
        } catch(InterruptedException e){    
            System.out.println("useEventsHelper");
        }
        
    }
    
    /** Mit építés gombok akcióiért felelős függvény
     * @param ae - Adott akció
    */
    
    public void buildingEvents(ActionEvent ae){
        try{
        switch(ae.getActionCommand()){
            case "Tűzhely":
                whereToBuild();
                fireplace=true;
                break;
            case "Víztisztító":
                whereToBuild();
                waterpurifier=true;
                break;
            case "Háló":
                whereToBuild();
                net=true;
                break;
            case "Hajó bővítés":
                whereToBuild();
                land=true;
                break;
            case "Lándzsa":
                Action.makeSpear(player);
                btSpear.setVisible(false);
                btFireplace.setVisible(false);
                btWaterpurifier.setVisible(false);
                btLand.setVisible(false);
                btNet.setVisible(false);
                guiBasicButtonsSetVisible(true);
                afterAction();
                break;
            }
        } catch(InterruptedException e){    
            System.out.println("buildingEvents");
        }
    }
    
    /** Mit használjak(iszik,eszik,süt) gombok akcióiért felelős függvény
     * @param ae - Adott akció
    */
    
    public void useEvents(ActionEvent ae){
        try{
        switch(ae.getActionCommand()){
            case "Krumpli":
                player.setItems(Action.cook(map.getbMap()[player.getX()][player.getY()],player.getItems(),new Potato()));
                btPotato.setVisible(false);
                btFish.setVisible(false);
                guiBasicButtonsSetVisible(true);
                afterAction();
                break;
            case "Hal":
                player.setItems(Action.cook(map.getbMap()[player.getX()][player.getY()],player.getItems(),new Fish()));
                btPotato.setVisible(false);
                btFish.setVisible(false);
                guiBasicButtonsSetVisible(true);
                afterAction();
                break;
            case "Eszik":
                player.eat();
                map.getbMap()[player.getX()][player.getY()].setEmpty(true, null,0);
                btEat.setVisible(false);
                guiBasicButtonsSetVisible(true);
                afterAction();
                break;
                
            case "Iszik":
                player.drink();
                map.getbMap()[player.getX()][player.getY()].setAge(0);
                btDrink.setVisible(false);
                guiBasicButtonsSetVisible(true);
                afterAction();
                break;
            }
        } catch(InterruptedException e){    
            System.out.println("useEvents");
        }
    }
    
}

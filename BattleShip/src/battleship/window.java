
package battleship;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

//By: Beau Marwaha

//TO DO:
//Done :)

//EXTRA:
//intelligent AI
//grid 1-10 a-j
public class window implements MouseListener, ActionListener, MouseWheelListener{
    
    JFrame window = new JFrame("BattleShip");
    JPanel mainPanel = new JPanel(new GridLayout(2,1));
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel yourGridPanel = new JPanel(new GridLayout(10,10,1,1));
    JPanel enemyGridPanel = new JPanel(new GridLayout(10,10,1,1));
    JPanel optGridPanel = new JPanel(new GridLayout(10,1));
    JPanel optGridPanel2 = new JPanel(new GridLayout(12,1));
    JPanel[][] yourgrid = new JPanel[10][10];
    int[][] yourshipPlace = new int [10][10];
    JPanel[][] enemygrid = new JPanel[10][10];
    int[][] enemyshipPlace = new int [10][10];
    JPanel[][] optgrid = new JPanel[10][1];
    JPanel[][] optgrid2 = new JPanel[12][1];
    JLabel[][] stats = new JLabel[12][1];
    
    JButton save = new JButton("        SAVE        ");
    JButton load = new JButton("        LOAD        ");
    JButton exit = new JButton("         EXIT         ");
    JButton newGame = new JButton("   NEW GAME   ");
    
    JLabel place = new JLabel("Ships to Place:");
    JButton carrier = new JButton("     Carrier-5     ");
    JButton battleShip = new JButton(" Battle Ship-4 ");
    JButton submarine = new JButton(" Submarine-3 ");
    JButton destroyer = new JButton("  Destroyer-3  ");
    JButton ptBoat = new JButton(" Patrol Boat-2 ");
    
    public boolean carrierActive = false;
    public boolean battleShipActive = false;
    public boolean submarineActive = false;
    public boolean destroyerActive = false;
    public boolean ptBoatActive = false;
    
    public boolean carrierActiveE = true;
    public boolean battleShipActiveE = true;
    public boolean submarineActiveE = true;
    public boolean destroyerActiveE = true;
    public boolean ptBoatActiveE = true;
    
    boolean alignmentH = true;
    boolean readyPlay = false;
    
    public int carrierHits = 0;
    public int battleShipHits = 0;
    public int submarineHits = 0;
    public int destroyerHits = 0;
    public int ptBoatHits = 0;
    public int shipsSunk = 0;
    
    public int carrierHitsE = 0;
    public int battleShipHitsE = 0;
    public int submarineHitsE = 0;
    public int destroyerHitsE = 0;
    public int ptBoatHitsE = 0;
    public int shipsSunkE = 0;
    
    public boolean win = false;
    
    //sets up window and calls for enemy ships to be placed
    public window(){
        
        window.setVisible(true);
        window.add(mainPanel);
        mainPanel.setBackground(Color.white);
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
        topPanel.add(BorderLayout.CENTER, enemyGridPanel);
        topPanel.add(BorderLayout.EAST, optGridPanel);
        bottomPanel.add(BorderLayout.CENTER, yourGridPanel);
        bottomPanel.add(BorderLayout.EAST, optGridPanel2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(700, 700);
        window.setLocationRelativeTo(null);
        
        optGridPanel.setBackground(Color.BLACK);
        optGridPanel2.setBackground(Color.BLACK);
        yourGridPanel.setBackground(Color.BLACK);
        enemyGridPanel.setBackground(Color.BLACK);
        
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                yourgrid[row][col] = new JPanel();
                yourgrid[row][col].setBackground(Color.CYAN);
                yourGridPanel.add(yourgrid[row][col]);
                yourgrid[row][col].addMouseListener(this);
                yourgrid[row][col].addMouseWheelListener(this);
                yourshipPlace[row][col] = 0;
            }
        }
        
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                enemygrid[row][col] = new JPanel();
                enemygrid[row][col].setBackground(Color.red);
                enemyGridPanel.add(enemygrid[row][col]);
                enemygrid[row][col].addMouseListener(this);
                enemygrid[row][col].addMouseWheelListener(this);
                enemyshipPlace[row][col] = 0;
            }
        }
        
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 1; col++){
                optgrid[row][col] = new JPanel();
                optgrid[row][col].setBackground(Color.black);
                optGridPanel.add(optgrid[row][col]);
                optgrid[row][col].addMouseListener(this);
            }
        }
        save.addActionListener(this);
        load.addActionListener(this);
        newGame.addActionListener(this);
        exit.addActionListener(this);
        place.setForeground(Color.white);
        optgrid[0][0].add(save);
        optgrid[1][0].add(load);
        optgrid[2][0].add(newGame);
        optgrid[3][0].add(exit);
        place.setFont(new Font("Verdana",1,14));
        optgrid[4][0].add(place);
        carrier.addActionListener(this);
        battleShip.addActionListener(this);
        submarine.addActionListener(this);
        destroyer.addActionListener(this);
        ptBoat.addActionListener(this);
        optgrid[5][0].add(carrier);
        optgrid[6][0].add(battleShip);
        optgrid[7][0].add(submarine);
        optgrid[8][0].add(destroyer);
        optgrid[9][0].add(ptBoat);
        
        
        for(int row = 0; row < 12; row++){
            for(int col = 0; col < 1; col++){
                optgrid2[row][col] = new JPanel();
                optgrid2[row][col].setBackground(Color.black);
                optGridPanel2.add(optgrid2[row][col]);
                optgrid2[row][col].addMouseListener(this);
            }
        }
        
        
        stats[0][0] = new JLabel();
        optgrid2[0][0].add(stats[0][0]);
        stats[0][0].setForeground(Color.white);
        stats[0][0].setFont(new Font("Verdana",1,15));
        stats[0][0].setText("Your Ships:");
        stats[1][0] = new JLabel();
        optgrid2[1][0].add(stats[1][0]);
        stats[1][0].setForeground(Color.white);
        stats[1][0].setText("Carrier");
        stats[2][0] = new JLabel();
        optgrid2[2][0].add(stats[2][0]);
        stats[2][0].setForeground(Color.white);
        stats[2][0].setText("Battle Ship");
        stats[3][0] = new JLabel();
        optgrid2[3][0].add(stats[3][0]);
        stats[3][0].setForeground(Color.white);
        stats[3][0].setText("Submarine");
        stats[4][0] = new JLabel();
        optgrid2[4][0].add(stats[4][0]);
        stats[4][0].setForeground(Color.white);
        stats[4][0].setText("Destroyer");
        stats[5][0] = new JLabel();
        optgrid2[5][0].add(stats[5][0]);
        stats[5][0].setForeground(Color.white);
        stats[5][0].setText("PT Boat");
        
        stats[6][0] = new JLabel();
        optgrid2[6][0].add(stats[6][0]);
        stats[6][0].setForeground(Color.white);
        stats[6][0].setFont(new Font("Verdana",1,15));
        stats[6][0].setText("Enemy Ships:");
        stats[7][0] = new JLabel();
        optgrid2[7][0].add(stats[7][0]);
        stats[7][0].setForeground(Color.white);
        stats[7][0].setText("Carrier");
        stats[8][0] = new JLabel();
        optgrid2[8][0].add(stats[8][0]);
        stats[8][0].setForeground(Color.white);
        stats[8][0].setText("Battle Ship");
        stats[9][0] = new JLabel();
        optgrid2[9][0].add(stats[9][0]);
        stats[9][0].setForeground(Color.white);
        stats[9][0].setText("Submarine");
        stats[10][0] = new JLabel();
        optgrid2[10][0].add(stats[10][0]);
        stats[10][0].setForeground(Color.white);
        stats[10][0].setText("Destroyer");
        stats[11][0] = new JLabel();
        optgrid2[11][0].add(stats[11][0]);
        stats[11][0].setForeground(Color.white);
        stats[11][0].setText("PT Boat");

        save.setEnabled(false);
        
        Random gen = new Random();
        int a = gen.nextInt(2);
        int b = gen.nextInt(2);
        int c = gen.nextInt(2);
        int d = gen.nextInt(2);
        int e = gen.nextInt(2);
        
        if (a == 0){
            placeEnemyCarrierHor();
        }
        if (a == 1){
            placeEnemyCarrierVert();
        }
        
        if (b == 0){
            placeEnemyBattleShipHor();
        }
        if (b == 1){
            placeEnemyBattleShipVert();
        }
        
        if (c == 0){
            placeEnemySubmarineHor();
        }
        if (c == 1){
            placeEnemySubmarineVert();
        }
        
        if (d == 0){
            placeEnemyDestroyerHor();
        }
        if (d == 1){
            placeEnemyDestroyerVert();
        }
        
        if (e == 0){
            placeEnemyPTBoatHor();
        }
        if (e == 1){
            placeEnemyPTBoatVert();
        }
        
    }
    
    //places your ships upon mouse click
    @Override
    public void mouseClicked(MouseEvent e) {
         for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                if (alignmentH){
                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (row + 1 < 10) && (yourshipPlace[row+1][col] == 0) && (row + 2 < 10) && (yourshipPlace[row+2][col] == 0) && (row + 3 < 10) && (yourshipPlace[row+3][col] == 0) && (row + 4 < 10) && (yourshipPlace[row+4][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 1;
                        JLabel jlabel1 = new JLabel("C");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+1][col].setBackground(Color.white);
                        yourshipPlace[row+1][col] = 1;
                        JLabel jlabel2 = new JLabel("C");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row+1][col].add(jlabel2);
                        yourgrid[row+1][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+2][col].setBackground(Color.white);
                        yourshipPlace[row+2][col] = 1;
                        JLabel jlabel3 = new JLabel("C");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row+2][col].add(jlabel3);
                        yourgrid[row+2][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+3][col].setBackground(Color.white);
                        yourshipPlace[row+3][col] = 1;
                        JLabel jlabel4 = new JLabel("C");
                        jlabel4.setFont(new Font("Verdana",1,15));
                        yourgrid[row+3][col].add(jlabel4);
                        yourgrid[row+3][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+4][col].setBackground(Color.white);
                        yourshipPlace[row+4][col] = 1;
                        JLabel jlabel5 = new JLabel("C");
                        jlabel5.setFont(new Font("Verdana",1,15));
                        yourgrid[row+4][col].add(jlabel5);
                        yourgrid[row+4][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        carrier.setEnabled(false);
                        carrierActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (row + 1 < 10) && (yourshipPlace[row+1][col] == 0) && (row + 2 < 10) && (yourshipPlace[row+2][col] == 0) && (row + 3 < 10) && (yourshipPlace[row+3][col] == 0)&& (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 2;
                        JLabel jlabel1 = new JLabel("B");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+1][col].setBackground(Color.white);
                        yourshipPlace[row+1][col] = 2;
                        JLabel jlabel2 = new JLabel("B");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row+1][col].add(jlabel2);
                        yourgrid[row+1][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+2][col].setBackground(Color.white);
                        yourshipPlace[row+2][col] = 2;
                        JLabel jlabel3 = new JLabel("B");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row+2][col].add(jlabel3);
                        yourgrid[row+2][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+3][col].setBackground(Color.white);
                        yourshipPlace[row+3][col] = 2;
                        JLabel jlabel4 = new JLabel("B");
                        jlabel4.setFont(new Font("Verdana",1,15));
                        yourgrid[row+3][col].add(jlabel4);
                        yourgrid[row+3][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        battleShip.setEnabled(false);
                        battleShipActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (row + 1 < 10) && (yourshipPlace[row+1][col] == 0) && (row + 2 < 10) && (yourshipPlace[row+2][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 3;
                        JLabel jlabel1 = new JLabel("S");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+1][col].setBackground(Color.white);
                        yourshipPlace[row+1][col] = 3;
                        JLabel jlabel2 = new JLabel("S");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row+1][col].add(jlabel2);
                        yourgrid[row+1][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+2][col].setBackground(Color.white);
                        yourshipPlace[row+2][col] = 3;
                        JLabel jlabel3 = new JLabel("S");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row+2][col].add(jlabel3);
                        yourgrid[row+2][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        submarine.setEnabled(false);
                        submarineActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (row + 1 < 10) && (yourshipPlace[row+1][col] == 0) && (row + 2 < 10) && (yourshipPlace[row+2][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 4;
                        JLabel jlabel1 = new JLabel("D");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+1][col].setBackground(Color.white);
                        yourshipPlace[row+1][col] = 4;
                        JLabel jlabel2 = new JLabel("D");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row+1][col].add(jlabel2);
                        yourgrid[row+1][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+2][col].setBackground(Color.white);
                        yourshipPlace[row+2][col] = 4;
                        JLabel jlabel3 = new JLabel("D");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row+2][col].add(jlabel3);
                        yourgrid[row+2][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        destroyer.setEnabled(false);
                        destroyerActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (row + 1 < 10) && (yourshipPlace[row +1][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 5;
                        JLabel jlabel1 = new JLabel("PT");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row+1][col].setBackground(Color.white);
                        yourshipPlace[row+1][col] = 5;
                        JLabel jlabel2 = new JLabel("PT");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row+1][col].add(jlabel2);
                        yourgrid[row+1][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        ptBoat.setEnabled(false);
                        ptBoatActive = false;
                    }
                } else if (!alignmentH)  {
                    //*********************************************
                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (col + 1 < 10) && (yourshipPlace[row][col+1] == 0) && (col + 2 < 10) && (yourshipPlace[row][col+2] == 0) && (col + 3 < 10) && (yourshipPlace[row][col+3] == 0) && (col + 4 < 10) && (yourshipPlace[row][col+4] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 1;
                        JLabel jlabel1 = new JLabel("C");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+1].setBackground(Color.white);
                        yourshipPlace[row][col+1] = 1;
                        JLabel jlabel2 = new JLabel("C");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+1].add(jlabel2);
                        yourgrid[row][col+1].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+2].setBackground(Color.white);
                        yourshipPlace[row][col+2] = 1;
                        JLabel jlabel3 = new JLabel("C");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+2].add(jlabel3);
                        yourgrid[row][col+2].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+3].setBackground(Color.white);
                        yourshipPlace[row][col+3] = 1;
                        JLabel jlabel4 = new JLabel("C");
                        jlabel4.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+3].add(jlabel4);
                        yourgrid[row][col+3].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+4].setBackground(Color.white);
                        yourshipPlace[row][col+4] = 1;
                        JLabel jlabel5 = new JLabel("C");
                        jlabel5.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+4].add(jlabel5);
                        yourgrid[row][col+4].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        carrier.setEnabled(false);
                        carrierActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (col + 1 < 10) && (yourshipPlace[row][col+1] == 0) && (col + 2 < 10) && (yourshipPlace[row][col+2] == 0) && (col + 3 < 10) && (yourshipPlace[row][col+3] == 0)&& (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 2;
                        JLabel jlabel1 = new JLabel("B");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+1].setBackground(Color.white);
                        yourshipPlace[row][col+1] = 2;
                        JLabel jlabel2 = new JLabel("B");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+1].add(jlabel2);
                        yourgrid[row][col+1].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+2].setBackground(Color.white);
                        yourshipPlace[row][col+2] = 2;
                        JLabel jlabel3 = new JLabel("B");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+2].add(jlabel3);
                        yourgrid[row][col+2].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+3].setBackground(Color.white);
                        yourshipPlace[row][col+3] = 2;
                        JLabel jlabel4 = new JLabel("B");
                        jlabel4.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+3].add(jlabel4);
                        yourgrid[row][col+3].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        battleShip.setEnabled(false);
                        battleShipActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (col + 1 < 10) && (yourshipPlace[row][col+1] == 0) && (col + 2 < 10) && (yourshipPlace[row][col+2] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 3;
                        JLabel jlabel1 = new JLabel("S");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+1].setBackground(Color.white);
                        yourshipPlace[row][col+1] = 3;
                        JLabel jlabel2 = new JLabel("S");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+1].add(jlabel2);
                        yourgrid[row][col+1].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+2].setBackground(Color.white);
                        yourshipPlace[row][col+2] = 3;
                        JLabel jlabel3 = new JLabel("S");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+2].add(jlabel3);
                        yourgrid[row][col+2].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        submarine.setEnabled(false);
                        submarineActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (col + 1 < 10) && (yourshipPlace[row][col+1] == 0) && (col + 2 < 10) && (yourshipPlace[row][col+2] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 4;
                        JLabel jlabel1 = new JLabel("D");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+1].setBackground(Color.white);
                        yourshipPlace[row][col+1] = 4;
                        JLabel jlabel2 = new JLabel("D");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+1].add(jlabel2);
                        yourgrid[row][col+1].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+2].setBackground(Color.white);
                        yourshipPlace[row][col+2] = 4;
                        JLabel jlabel3 = new JLabel("D");
                        jlabel3.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+2].add(jlabel3);
                        yourgrid[row][col+2].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        destroyer.setEnabled(false);
                        destroyerActive = false;
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (col + 1 < 10) && (yourshipPlace[row][col+1] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        yourshipPlace[row][col] = 5;
                        JLabel jlabel1 = new JLabel("PT");
                        jlabel1.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col].add(jlabel1);
                        yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        yourgrid[row][col+1].setBackground(Color.white);
                        yourshipPlace[row][col+1] = 5;
                        JLabel jlabel2 = new JLabel("PT");
                        jlabel2.setFont(new Font("Verdana",1,15));
                        yourgrid[row][col+1].add(jlabel2);
                        yourgrid[row][col+1].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                        ptBoat.setEnabled(false);
                        ptBoatActive = false;
                    }
                    //******************************************************************
                }
                if (readyPlay && e.getSource() == enemygrid[row][col]){
                        checkEnemyHit(row, col);
                        if (!win){
                            enemyAttack();
                        }
                }
            }
         } 
         if (!readyPlay && !carrier.isEnabled() && !battleShip.isEnabled() && !submarine.isEnabled() && !destroyer.isEnabled() && !ptBoat.isEnabled()){
             readyPlay = true;
             JOptionPane.showMessageDialog(null, "All ships placed, click on an enemy tile to fire");
             save.setEnabled(true);
         }
         
        
    }
    
    //nothing
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    //nothing
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    //adds white blocks when entering a grid panel
    @Override
    public void mouseEntered(MouseEvent e) {
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                if (alignmentH){
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.white);
                    }
                    if (e.getSource() == enemygrid[row][col]){
                        enemygrid[row][col].setBackground(Color.white);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                        if (row + 3 < 10){
                            yourgrid[row+3][col].setBackground(Color.white);
                        }
                        if (row + 4 < 10){
                            yourgrid[row+4][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                        if (row + 3 < 10){
                            yourgrid[row+3][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                    }
                } else if (!alignmentH) {
                    //*******************************
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.white);
                    }
                    if (e.getSource() == enemygrid[row][col]){
                        enemygrid[row][col].setBackground(Color.white);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                        if (col + 3 < 10){
                            yourgrid[row][col+3].setBackground(Color.white);
                        }
                        if (col + 4 < 10){
                            yourgrid[row][col+4].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                        if (col + 3 < 10){
                            yourgrid[row][col+3].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                    }
                    //***************************************
                }
 
            }
        }
    }
    
    //removes white blocks when exiting a grid panel
    @Override
    public void mouseExited(MouseEvent e) {
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                if (alignmentH){
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.CYAN);
                    }
                    if (e.getSource() == enemygrid[row][col]){
                        enemygrid[row][col].setBackground(Color.red);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                        if ((row + 3 < 10) && yourshipPlace[row+3][col] == 0){
                            yourgrid[row+3][col].setBackground(Color.CYAN);
                        }
                        if ((row + 4 < 10) && yourshipPlace[row+4][col] == 0){
                            yourgrid[row+4][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                        if ((row + 3 < 10) && yourshipPlace[row+3][col] == 0){
                            yourgrid[row+3][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                    }
                } else if (!alignmentH) {
                    //*******************************************
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.CYAN);
                    }
                    if (e.getSource() == enemygrid[row][col]){
                        enemygrid[row][col].setBackground(Color.red);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                        if ((col + 3 < 10) && yourshipPlace[row][col+3] == 0){
                            yourgrid[row][col+3].setBackground(Color.CYAN);
                        }
                        if ((col + 4 < 10) && yourshipPlace[row][col+4] == 0){
                            yourgrid[row][col+4].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                        if ((col + 3 < 10) && yourshipPlace[row][col+3] == 0){
                            yourgrid[row][col+3].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                    }
                    //**************************************
                }
                
            }
        }
    }
    
    //button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save){
            try {
                saveGame();
            } catch (IOException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource() == load){
            for(int row = 0; row < 10; row++){
                for(int col = 0; col < 10; col++){
                    yourgrid[row][col].removeAll();
                    enemygrid[row][col].removeAll();
                    yourgrid[row][col].setBackground(Color.cyan);
                    enemygrid[row][col].setBackground(Color.red);
                    yourgrid[row][col].setBorder(null);
                    enemygrid[row][col].setBorder(null);
                    yourgrid[row][col].updateUI();
                    enemygrid[row][col].updateUI();
                }
            }    
            String name = JOptionPane.showInputDialog("Enter save name:");
            try {
                loadGame(name);
            } catch (IOException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource() == newGame){
            window.dispose();
            window gui = new window();
        }
        
        if (e.getSource() == exit){
            window.dispose();
        }
        
        if (e.getSource() == carrier){
            carrierActive = true;
            battleShipActive = false;
            submarineActive = false;
            destroyerActive = false;
            ptBoatActive = false;
        }
        
        if (e.getSource() == battleShip){
            battleShipActive = true;
            carrierActive = false;
            submarineActive = false;
            destroyerActive = false;
            ptBoatActive = false;
        }
        
        if (e.getSource() == submarine){
            submarineActive = true;
            carrierActive = false;
            battleShipActive = false;
            destroyerActive = false;
            ptBoatActive = false;
        }
        
        if (e.getSource() == destroyer){
            destroyerActive = true;
            carrierActive = false;
            battleShipActive = false;
            submarineActive = false;
            ptBoatActive = false;
        }
        
        if (e.getSource() == ptBoat){
            ptBoatActive = true;
            carrierActive = false;
            battleShipActive = false;
            submarineActive = false;
            destroyerActive = false;
        }

    }
    
    //changes ship alignment when scrolled
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (alignmentH){
            alignmentH = false;
            for(int row = 0; row < 10; row++){
                for(int col = 0; col < 10; col++){
                    //removes white vertical panels
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.CYAN);
                    }
                    if (e.getSource() == enemygrid[row][col] && enemyshipPlace[row][col] == 0){
                        enemygrid[row][col].setBackground(Color.red);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                        if ((row + 3 < 10) && yourshipPlace[row+3][col] == 0){
                            yourgrid[row+3][col].setBackground(Color.CYAN);
                        }
                        if ((row + 4 < 10) && yourshipPlace[row+4][col] == 0){
                            yourgrid[row+4][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                        if ((row + 3 < 10) && yourshipPlace[row+3][col] == 0){
                            yourgrid[row+3][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                        if ((row + 2 < 10) && yourshipPlace[row+2][col] == 0){
                            yourgrid[row+2][col].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((row + 1 < 10) && yourshipPlace[row+1][col] == 0){
                            yourgrid[row+1][col].setBackground(Color.CYAN);
                        }
                    }
                    //places white horizontal panels
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.white);
                    }


                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                        if (col + 3 < 10){
                            yourgrid[row][col+3].setBackground(Color.white);
                        }
                        if (col + 4 < 10){
                            yourgrid[row][col+4].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                        if (col + 3 < 10){
                            yourgrid[row][col+3].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                        if (col + 2 < 10){
                            yourgrid[row][col+2].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (col + 1 < 10){
                            yourgrid[row][col+1].setBackground(Color.white);
                        }
                    }
                }
            }  
        } else if (!alignmentH){
            alignmentH = true;
            for(int row = 0; row < 10; row++){
                for(int col = 0; col < 10; col++){
                    //removes white Horizontal panels
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.CYAN);
                    }
                    if (e.getSource() == enemygrid[row][col] && enemyshipPlace[row][col] == 0){
                        enemygrid[row][col].setBackground(Color.red);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                        if ((col + 3 < 10) && yourshipPlace[row][col+3] == 0){
                            yourgrid[row][col+3].setBackground(Color.CYAN);
                        }
                        if ((col + 4 < 10) && yourshipPlace[row][col+4] == 0){
                            yourgrid[row][col+4].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                        if ((col + 3 < 10) && yourshipPlace[row][col+3] == 0){
                            yourgrid[row][col+3].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                        if ((col + 2 < 10) && yourshipPlace[row][col+2] == 0){
                            yourgrid[row][col+2].setBackground(Color.CYAN);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.CYAN);
                        if ((col + 1 < 10) && yourshipPlace[row][col+1] == 0){
                            yourgrid[row][col+1].setBackground(Color.CYAN);
                        }
                    }
                    //places white vertical panels
                    if (e.getSource() == yourgrid[row][col] && yourshipPlace[row][col] == 0){
                        yourgrid[row][col].setBackground(Color.white);
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (carrierActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                        if (row + 3 < 10){
                            yourgrid[row+3][col].setBackground(Color.white);
                        }
                        if (row + 4 < 10){
                            yourgrid[row+4][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (battleShipActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                        if (row + 3 < 10){
                            yourgrid[row+3][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (submarineActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (destroyerActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                        if (row + 2 < 10){
                            yourgrid[row+2][col].setBackground(Color.white);
                        }
                    }

                    if (e.getSource() == yourgrid[row][col] && (yourshipPlace[row][col] == 0) && (ptBoatActive)){
                        yourgrid[row][col].setBackground(Color.white);
                        if (row + 1 < 10){
                            yourgrid[row+1][col].setBackground(Color.white);
                        }
                    }
                }
            }
        }  
    }
    
    //places enemy carrier horizontal
    public final void placeEnemyCarrierHor() {
        Random gen = new Random();
        while (carrierActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (col + 1 < 10) && (enemyshipPlace[row][col+1] == 0) && (col + 2 < 10) && (enemyshipPlace[row][col+2] == 0) && (col + 3 < 10) && (enemyshipPlace[row][col+3] == 0) && (col + 4 < 10) && (enemyshipPlace[row][col+4] == 0)){
                enemyshipPlace[row][col] = 1;
                enemyshipPlace[row][col+1] = 1;
                enemyshipPlace[row][col+2] = 1;
                enemyshipPlace[row][col+3] = 1;
                enemyshipPlace[row][col+4] = 1;
                carrierActiveE = false;
            }
        }
        
    }
    
    //places enemy carrier vertical
    public final void placeEnemyCarrierVert() {
        Random gen = new Random();
        while (carrierActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (row + 1 < 10) && (enemyshipPlace[row+1][col] == 0) && (row + 2 < 10) && (enemyshipPlace[row+2][col] == 0) && (row + 3 < 10) && (enemyshipPlace[row+3][col] == 0) && (row + 4 < 10) && (enemyshipPlace[row+4][col] == 0)){
                enemyshipPlace[row][col] = 1;
                enemyshipPlace[row+1][col] = 1;
                enemyshipPlace[row+2][col] = 1;
                enemyshipPlace[row+3][col] = 1;
                enemyshipPlace[row+4][col] = 1;
                carrierActiveE = false;
            }
        }
    }
    
    //places enemy battleship horizontal
    public final void placeEnemyBattleShipHor() {
        Random gen = new Random();
        while (battleShipActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (col + 1 < 10) && (enemyshipPlace[row][col+1] == 0) && (col + 2 < 10) && (enemyshipPlace[row][col+2] == 0) && (col + 3 < 10) && (enemyshipPlace[row][col+3] == 0)){
                enemyshipPlace[row][col] = 2;
                enemyshipPlace[row][col+1] = 2;
                enemyshipPlace[row][col+2] = 2;
                enemyshipPlace[row][col+3] = 2;
                battleShipActiveE = false;
            }
        }
    }
    
    //places enemy battleship vertical
    public final void placeEnemyBattleShipVert() {
        Random gen = new Random();
        while (battleShipActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (row + 1 < 10) && (enemyshipPlace[row+1][col] == 0) && (row + 2 < 10) && (enemyshipPlace[row+2][col] == 0) && (row + 3 < 10) && (enemyshipPlace[row+3][col] == 0)){
                enemyshipPlace[row][col] = 2;
                enemyshipPlace[row+1][col] = 2;
                enemyshipPlace[row+2][col] = 2;
                enemyshipPlace[row+3][col] = 2;
                battleShipActiveE = false;
            }
        }
    }
    
    //places enemy submarine horizontal
    public final void placeEnemySubmarineHor() {
        Random gen = new Random();
        while (submarineActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (col + 1 < 10) && (enemyshipPlace[row][col+1] == 0) && (col + 2 < 10) && (enemyshipPlace[row][col+2] == 0)){
                enemyshipPlace[row][col] = 3;
                enemyshipPlace[row][col+1] = 3;
                enemyshipPlace[row][col+2] = 3;
                submarineActiveE = false;
            }
        }
    }
    
    //places enemy submarine vertical
    public final void placeEnemySubmarineVert() {
        Random gen = new Random();
        while (submarineActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (row + 1 < 10) && (enemyshipPlace[row+1][col] == 0) && (row + 2 < 10) && (enemyshipPlace[row+2][col] == 0)){
                enemyshipPlace[row][col] = 3;
                enemyshipPlace[row+1][col] = 3;
                enemyshipPlace[row+2][col] = 3;
                submarineActiveE = false;
            }
        }
    }
    
    //places enemy destroyer horizontal
    public final void placeEnemyDestroyerHor() {
        Random gen = new Random();
        while (destroyerActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (col + 1 < 10) && (enemyshipPlace[row][col+1] == 0) && (col + 2 < 10) && (enemyshipPlace[row][col+2] == 0)){
                enemyshipPlace[row][col] = 4;
                enemyshipPlace[row][col+1] = 4;
                enemyshipPlace[row][col+2] = 4;
                destroyerActiveE = false;
            }
        }
    }
    
    //places enemy destroyer vertical
    public final void placeEnemyDestroyerVert() {
        Random gen = new Random();
        while (destroyerActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (row + 1 < 10) && (enemyshipPlace[row+1][col] == 0) && (row + 2 < 10) && (enemyshipPlace[row+2][col] == 0)){
                enemyshipPlace[row][col] = 4;
                enemyshipPlace[row+1][col] = 4;
                enemyshipPlace[row+2][col] = 4;
                destroyerActiveE = false;
            }
        }
    }
    
    //places enemy pt boat horizontal
    public final void placeEnemyPTBoatHor() {
        Random gen = new Random();
        while (ptBoatActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (col + 1 < 10) && (enemyshipPlace[row][col+1] == 0)){
                enemyshipPlace[row][col] = 5;
                enemyshipPlace[row][col+1] = 5;
                ptBoatActiveE = false;
            }
        }
    }
    
    //places enemy pt boat vertical
    public final void placeEnemyPTBoatVert() {
        Random gen = new Random();
        while (ptBoatActiveE) {
            int row = gen.nextInt(10);
            int col = gen.nextInt(10);
            if ((enemyshipPlace[row][col] == 0) && (row + 1 < 10) && (enemyshipPlace[row +1][col] == 0)){
                enemyshipPlace[row][col] = 5;
                enemyshipPlace[row+1][col] = 5;
                ptBoatActiveE = false;
            }
        }
    }

    //handles your hit/miss on enemy grid and win conditions
    private void checkEnemyHit(int row, int col) {
        if (enemyshipPlace[row][col] == 0){
            JLabel jlabel1 = new JLabel("O");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemyshipPlace[row][col] = 11;
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            try {
                playSound("Miss");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Missed");
        }

        if (enemyshipPlace[row][col] == 1){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            carrierHitsE += 1;
            enemyshipPlace[row][col] = 6;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Hit");
            if (carrierHitsE == 5){
                shipsSunkE += 1;
                stats[7][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "You sunk the enemy Carrier!");
            }
        }

        if (enemyshipPlace[row][col] == 2){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            battleShipHitsE += 1;
            enemyshipPlace[row][col] = 7;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Hit");
            if (battleShipHitsE == 4){
                shipsSunkE += 1;
                stats[8][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "You sunk the enemy Battle Ship!");
            }
        }

        if (enemyshipPlace[row][col] == 3){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            submarineHitsE += 1;
            enemyshipPlace[row][col] = 8;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Hit");
            if (submarineHitsE == 3){
                shipsSunkE += 1;
                stats[9][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "You sunk the enemy Submarine!");
            }
        }

        if (enemyshipPlace[row][col] == 4){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            destroyerHitsE += 1;
            enemyshipPlace[row][col] = 9;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Hit");
            if (destroyerHitsE == 3){
                shipsSunkE += 1;
                stats[10][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "You sunk the enemy Destroyer!");
            }
        }

        if (enemyshipPlace[row][col] == 5){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            enemygrid[row][col].add(jlabel1);
            enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            ptBoatHitsE += 1;
            enemyshipPlace[row][col] = 10;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "You Hit");
            if (ptBoatHitsE == 2){
                shipsSunkE += 1;
                stats[11][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "You sunk the enemy PT Boat!");
            }
        }

        if (shipsSunkE == 5){
            JOptionPane.showMessageDialog(null, "Congrats, You Won!!");
            window.dispose();
            win = true;
        }
    }

    //handles enemy hit/miss on your grid and lose conditions
    private void enemyAttack() {
        Random gen = new Random(); 
        int row = gen.nextInt(10); 
        int col = gen.nextInt(10);
        while (yourshipPlace[row][col] > 5){
            row = gen.nextInt(10);
            col = gen.nextInt(10);
        }  

        if (yourshipPlace[row][col] == 0){
            JLabel jlabel1 = new JLabel("O");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourshipPlace[row][col] = 11;
            yourgrid[row][col].add(jlabel1);
            yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
            try {
                playSound("Miss");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "The enemy Missed");
        }

        if (yourshipPlace[row][col] == 1){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourgrid[row][col].add(jlabel1);
            carrierHits += 1;
            yourshipPlace[row][col] = 6;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "Your Carrier was Hit by the enemy");
            if (carrierHits == 5){
                shipsSunk += 1;
                stats[1][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "The enemy sunk your Carrier!");
            }
        }

        if (yourshipPlace[row][col] == 2){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourgrid[row][col].add(jlabel1);
            battleShipHits += 1;
            yourshipPlace[row][col] = 7;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "Your Battle Ship was Hit by the enemy");
            if (battleShipHits == 4){
                shipsSunk += 1;
                stats[2][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "The enemy sunk your Battle Ship!");
            }
        }

        if (yourshipPlace[row][col] == 3){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourgrid[row][col].add(jlabel1);
            submarineHits += 1;
            yourshipPlace[row][col] = 8;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "Your Submarine was Hit by the enemy");
            if (submarineHits == 3){
                shipsSunk += 1;
                stats[3][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "The enemy sunk your Submarine!");
            }
        }

        if (yourshipPlace[row][col] == 4){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourgrid[row][col].add(jlabel1);
            destroyerHits += 1;
            yourshipPlace[row][col] = 9;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "Your Destroyer was Hit by the enemy");
            if (destroyerHits == 3){
                shipsSunk += 1;
                stats[4][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "The enemy sunk your Destroyer!");
            }
        }

        if (yourshipPlace[row][col] == 5){
            JLabel jlabel1 = new JLabel("X");
            jlabel1.setFont(new Font("Verdana",1,15));
            yourgrid[row][col].add(jlabel1);
            ptBoatHits += 1;
            yourshipPlace[row][col] = 10;
            try {
                playSound("Hit");
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, "Your PT Boat was Hit by the enemy");
            if (ptBoatHits == 2){
                shipsSunk += 1;
                stats[5][0].setForeground(Color.gray);
                try {
                    playSound("Sinking");
                } catch (Exception ex) {
                }
                JOptionPane.showMessageDialog(null, "The enemy sunk your PT Boat!");
            }
        }

        if (shipsSunk == 5){
            JOptionPane.showMessageDialog(null, "You Lost");
            window.dispose();
        }

        yourgrid[row][col].updateUI();
    }
    
    //saves the game
    public void saveGame () throws IOException {
        String name = JOptionPane.showInputDialog("Enter a *NEW* Save Name:");
        
        File saveFile = new File("src//battleship//" + name + ".txt");

        BufferedWriter wr = new BufferedWriter(new FileWriter(saveFile, true));

        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
            wr.write(Integer.toString(yourshipPlace[row][col]));
            wr.newLine();
            }
        }  

        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
            wr.write(Integer.toString(enemyshipPlace[row][col]));
            wr.newLine();
            }
        }  
        
        wr.write(Integer.toString(carrierHits));
        wr.newLine();
        wr.write(Integer.toString(battleShipHits));
        wr.newLine();
        wr.write(Integer.toString(submarineHits));
        wr.newLine();
        wr.write(Integer.toString(destroyerHits));
        wr.newLine();
        wr.write(Integer.toString(ptBoatHits));
        wr.newLine();
        wr.write(Integer.toString(shipsSunk));
        wr.newLine();
        wr.write(Integer.toString(carrierHitsE));
        wr.newLine();
        wr.write(Integer.toString(battleShipHitsE));
        wr.newLine();
        wr.write(Integer.toString(submarineHitsE));
        wr.newLine();
        wr.write(Integer.toString(destroyerHitsE));
        wr.newLine();
        wr.write(Integer.toString(ptBoatHitsE));
        wr.newLine();
        wr.write(Integer.toString(shipsSunkE));
        wr.newLine();

        wr.close();

    }
    
    //loads a game
    public void loadGame (String name) throws IOException, FileNotFoundException{
           
        BufferedReader rd = new BufferedReader(new FileReader("src//battleship//" + name + ".txt"));
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
               String contents = rd.readLine();
               yourshipPlace[row][col] = Integer.parseInt(contents);
            }
        } 
        
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                String contents = rd.readLine();
                enemyshipPlace[row][col] = Integer.parseInt(contents);
            }
        } 
        
        String contents1 = rd.readLine();
        carrierHits = Integer.parseInt(contents1);
        String contents2 = rd.readLine();
        battleShipHits = Integer.parseInt(contents2);
        String contents3 = rd.readLine();
        submarineHits = Integer.parseInt(contents3);
        String contents4 = rd.readLine();
        destroyerHits = Integer.parseInt(contents4);
        String contents5 = rd.readLine();
        ptBoatHits = Integer.parseInt(contents5);
        String contents6 = rd.readLine();
        shipsSunk = Integer.parseInt(contents6);
        String contents7 = rd.readLine();
        carrierHitsE = Integer.parseInt(contents7);
        String contents8 = rd.readLine();
        battleShipHitsE = Integer.parseInt(contents8);
        String contents9 = rd.readLine();
        submarineHitsE = Integer.parseInt(contents9);
        String contents10 = rd.readLine();
        destroyerHitsE = Integer.parseInt(contents10);
        String contents11 = rd.readLine();
        ptBoatHitsE = Integer.parseInt(contents11);
        String contents12 = rd.readLine();
        shipsSunkE = Integer.parseInt(contents12);
        
        if (carrierHits == 5){
            stats[1][0].setForeground(Color.gray);
        }
        if (battleShipHits == 4){
            stats[2][0].setForeground(Color.gray);
        }
        if (submarineHits == 3){
            stats[3][0].setForeground(Color.gray);
        }
        if (destroyerHits == 3){
            stats[4][0].setForeground(Color.gray);
        }
        if (ptBoatHits == 2){
            stats[5][0].setForeground(Color.gray);
        }
        
        if (carrierHitsE == 5){
            stats[7][0].setForeground(Color.gray);
        }
        if (battleShipHitsE == 4){
            stats[8][0].setForeground(Color.gray);
        }
        if (submarineHitsE == 3){
            stats[9][0].setForeground(Color.gray);
        }
        if (destroyerHitsE == 3){
            stats[10][0].setForeground(Color.gray);
        }
        if (ptBoatHitsE == 2){
            stats[11][0].setForeground(Color.gray);
        }
        
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                //sets your ships
                if (yourshipPlace[row][col] == 0){
                    //tile empty
                }
                if (yourshipPlace[row][col] == 1){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("C");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 2){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("B");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 3){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("S");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 4){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("D");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 5){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("PT");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 6){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("CX");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 7){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("BX");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 8){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("SX");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 9){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("DX");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 10){
                    yourgrid[row][col].setBackground(Color.white);
                    JLabel jlabel1 = new JLabel("PTX");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (yourshipPlace[row][col] == 11){
                    JLabel jlabel1 = new JLabel("O");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    yourgrid[row][col].add(jlabel1);
                    yourgrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                
                //sets enemy ships
                if (enemyshipPlace[row][col] == 6){
                    JLabel jlabel1 = new JLabel("X");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (enemyshipPlace[row][col] == 7){
                    JLabel jlabel1 = new JLabel("X");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (enemyshipPlace[row][col] == 8){
                    JLabel jlabel1 = new JLabel("X");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (enemyshipPlace[row][col] == 9){
                    JLabel jlabel1 = new JLabel("X");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (enemyshipPlace[row][col] == 10){
                    JLabel jlabel1 = new JLabel("X");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                if (enemyshipPlace[row][col] == 11){
                    JLabel jlabel1 = new JLabel("O");
                    jlabel1.setFont(new Font("Verdana",1,15));
                    enemygrid[row][col].add(jlabel1);
                    enemygrid[row][col].setBorder(new LineBorder(Color.BLACK)); // make it easy to see
                }
                yourgrid[row][col].updateUI();
                enemygrid[row][col].updateUI();
                
            }
        }
        carrier.setEnabled(false);
        battleShip.setEnabled(false);
        submarine.setEnabled(false);
        destroyer.setEnabled(false);
        ptBoat.setEnabled(false);
        readyPlay = true;
        JOptionPane.showMessageDialog(null, "Loaded, click on an enemy tile to fire");
    }
    
    //handles playing sounds in game
    public void playSound (String S) throws Exception{
        Clip c = AudioSystem.getClip();
        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("src//battleship//" + S + ".wav"));
        c.close();
        c.open(sound);
        c.loop(0);
    }
}

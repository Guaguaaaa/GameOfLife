package GameOfLifeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame{

    /**
     * Setup JFrame
     * repaint "paintComponent"
     *
     *
     * Click boundary -> x + 7, y + 30
     * boarder -> x = 7 + 7, y = 30 + 7
     */

    public int spacing = 1;

    public boolean pause = true;
    public boolean clear = false;

    public int mouseX = -100;
    public int mouseY = -100;

    public int generationX = 1125;
    public int generationY = 5;
    public int numberOfGeneration = 0;

    public int[][] aliveInt = new int[64][36];

    public int[][] neighbours = new int[64][36];

    public GUI(){
        this.setTitle("Game of Life");
        this.setSize(1294, 837);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for(int i = 0;i < 64;i++){
            for(int j = 0;j < 36;j++){
                aliveInt[i][j] = 0;
            }
        }
        aliveInt[31][16] = 1;
        aliveInt[32][17] = 1;
        aliveInt[30][18] = 1;
        aliveInt[31][18] = 1;
        aliveInt[32][18] = 1;


        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

    }

    public class Board extends JPanel{
        public void paintComponent(Graphics g) {
            try {
                //System.out.println("gui.pause is: " + gui.pause);
                System.out.println();
                if(pause == false) {
                    //System.out.println("gui.pause != true");
                    for (int i = 0; i < 64; i++) {
                        for (int j = 0; j < 36; j++) {
                            if (aliveInt[i][j] == 1) {
                                if (neighbours[i][j] < 2 || neighbours[i][j] > 3) {
                                    aliveInt[i][j] = 0;
                                }
                                if (neighbours[i][j] == 2 || neighbours[i][j] == 3) {
                                    aliveInt[i][j] = 1;
                                }
                            }
                            if (aliveInt[i][j] == 0) {
                                if (neighbours[i][j] == 3) {
                                    aliveInt[i][j] = 1;
                                }
                                if (neighbours[i][j] == 2 && aliveInt[i][j] == 1) {
                                    aliveInt[i][j] = 1;
                                }
                            }
                        }
                    }
                    numberOfGeneration++;
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 36; j++) {
                    neighbours[i][j] = isN(i, j);
                }
            }

            g.setColor(Color.black);
            g.fillRect(0, 0, 1280, 800);
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 36; j++) {
                    g.setColor(Color.lightGray);
                    g.fillRect(spacing + i * 20, spacing + j * 20 + 80, 20 - 2 * spacing, 20 - 2 * spacing);
                }
            }

            if(clear){
                for(int i = 0;i < 64;i++){
                    for(int j = 0;j < 36;j++){
                        aliveInt[i][j] = 0;
                    }
                }
                numberOfGeneration = 0;
                clear = false;
            }

            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 36; j++) {
                    if (aliveInt[i][j] == 1) {
                        g.setColor(Color.yellow);
                        g.fillRect(spacing + i * 20, spacing + j * 20 + 80, 20 - 2 * spacing, 20 - 2 * spacing);
                    }
                    if (aliveInt[i][j] == 0) {
                        g.setColor(Color.gray);
                        g.fillRect(spacing + i * 20, spacing + j * 20 + 80, 20 - 2 * spacing, 20 - 2 * spacing);
                    }
                }
            }

            //pause (start/stop)
            g.setColor(Color.darkGray);
            g.fillRect(425, 10, 280, 60);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.PLAIN, 60));
            if (pause) {
                g.drawString("START", 475, 60);
            } else if (!pause) {
                g.drawString("STOP", 495, 60);
            }

            //generations
            //generationX = 1125, generationY = 5;
            g.setColor(Color.darkGray);
            g.fillRect(generationX, generationY, 140, 70);

            g.setFont(new Font("Tahoma", Font.PLAIN, 80));
            g.setColor(Color.gray);
            if (numberOfGeneration <= 999) {
                g.drawString(Integer.toString(numberOfGeneration), generationX, generationY + 65);
            }else if(numberOfGeneration > 999){
                g.drawString("99+", generationX, generationY + 65);
            }

            //clear
            g.setColor(Color.darkGray);
            g.fillRect(10, 10, 200, 60);
            g.setFont(new Font("Tahoma", Font.PLAIN, 60));
            g.setColor(Color.white);
            g.drawString("CLEAR", 20, 60);
        }
    }

    public int isN(int x, int y){
        int neighs = 0;
        if(x > 0 && y > 0) {
            if (aliveInt[x - 1][y - 1] == 1) {
                neighs++;
            }
        }
        if(y > 0) {
            if (aliveInt[x][y - 1] == 1) {
                neighs++;
            }
            if(x < 63) {
                if (aliveInt[x + 1][y - 1] == 1) {
                    neighs++;
                }
            }
        }
        if(x > 0) {
            if (aliveInt[x - 1][y] == 1) {
                neighs++;
            }
        }
        if(x < 63) {
            if (aliveInt[x + 1][y] == 1) {
                neighs++;
            }
        }
        if(y < 35) {
            if (x > 0) {
                if (aliveInt[x - 1][y + 1] == 1) {
                    neighs++;
                }
            }
            if (aliveInt[x][y + 1] == 1) {
                neighs++;
            }
            if(x < 63) {
                if (aliveInt[x + 1][y + 1] == 1) {
                    neighs++;
                }
            }
        }
        return neighs;
    }

    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            //System.out.println("mouseX - " + mouseX + ", mouseY - " + mouseY);
        }
    }

    public class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //input cells
            if(inBoxX() != -1 && inBoxY() != -1) {
                if(aliveInt[inBoxX()][inBoxY()] == 0){
                    aliveInt[inBoxX()][inBoxY()] = 1;
                }else if(aliveInt[inBoxX()][inBoxY()] == 1){
                    aliveInt[inBoxX()][inBoxY()] = 0;
                }
            }

            //pause button
            if(mouseX > 432 && mouseY > 40 && mouseX < 712 && mouseY < 100){
                if(pause == true){
                    pause = false;
                }else if(pause == false){
                    pause = true;
                }
            }

            //buttons
            //17, 217, 40, 100
            if(mouseX > 17 && mouseX < 217 && mouseY > 40 && mouseY < 100){
                clear = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public int inBoxX(){
        for(int i = 0;i < 64;i++){
            for(int j = 0;j < 36;j++){
                if(mouseX >= spacing+i*20+7 && mouseX < spacing+i*20+20+7-2*spacing && mouseY >= spacing+j*20+80+29 && mouseY < spacing+j*20+20+80+29-2*spacing){
                    return i;
                }
            }
        }
        return -1;
    }
    public int inBoxY(){
        for(int i = 0;i < 64;i++){
            for(int j = 0;j < 36;j++){
                if(mouseX >= spacing+i*20+7 && mouseX < spacing+i*20+20+7-2*spacing && mouseY >= spacing+j*20+80+29 && mouseY < spacing+j*20+20+80+29-2*spacing){
                    return j;
                }
            }
        }
        return -1;
    }
}
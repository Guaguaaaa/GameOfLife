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
    public int speed = 950;

    public boolean pause = true;
    public boolean clear = false;

    public int mouseX = -100;
    public int mouseY = -100;

    public int generationX = 1125;
    public int generationY = 5;
    public int numberOfGeneration = 0;

    public boolean lifes = false;
    public boolean oscillators = false;
    public boolean spaceships = false;
    public boolean gun = false;

    public int[][] aliveInt = new int[64][36];

    public int[][] neighbours = new int[64][36];

    public GUI(){
        this.setTitle("Game of Life");
        this.setSize(1494, 917);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
            //System.out.println("gui paintComponent running");
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 36; j++) {
                    neighbours[i][j] = isN(i, j);
                }
            }

            g.setColor(Color.black);
            g.fillRect(0, 0, 1280, 880);
            g.setColor(Color.lightGray);
            g.fillRect(0, 80, 1280, 720);
            g.setColor(Color.darkGray);
            g.fillRect(1280, 0, 200, 880);
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 36; j++) {
                    g.setColor(Color.gray);
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

            for(int i = 0; i < 64;i++){
                for (int j = 0; j < 36; j++) {
                    if (mouseX >= spacing + i * 20 + 7 && mouseX < spacing + i * 20 + 20 + 7 - 2 * spacing && mouseY >= spacing + j * 20 + 80 + 29 && mouseY < spacing + j * 20 + 20 + 80 + 29 - 2 * spacing) {
                        if (aliveInt[inBoxX()][inBoxY()] == 0) {
                            g.setColor(new Color(178, 178, 178));
                            g.fillRect(spacing + i * 20, spacing + j * 20 + 80, 20 - 2 * spacing, 20 - 2 * spacing);
                        }
                        if(aliveInt[inBoxX()][inBoxY()] == 1){
                            g.setColor(new Color(255, 255, 200));
                            g.fillRect(spacing + i * 20, spacing + j * 20 + 80, 20 - 2 * spacing, 20 - 2 * spacing);
                        }
                    }
                }
            }

            if(lifes){
                for(int i = 0;i < 64;i++){
                    for(int j = 0;j < 36;j++){
                        aliveInt[i][j] = 0;
                    }
                }
                numberOfGeneration = 0;
                //Block
                aliveInt[1][1] = 1;
                aliveInt[1][2]= 1;
                aliveInt[2][1] = 1;
                aliveInt[2][2] = 1;

                //Beehive
                aliveInt[11][1] = 1;
                aliveInt[12][1] = 1;
                aliveInt[10][2] = 1;
                aliveInt[11][3] = 1;
                aliveInt[12][3] = 1;
                aliveInt[13][2] = 1;

                //loaf
                aliveInt[1][11] = 1;
                aliveInt[2][10] = 1;
                aliveInt[3][10] = 1;
                aliveInt[4][11] = 1;
                aliveInt[4][12] = 1;
                aliveInt[2][12] = 1;
                aliveInt[3][13] = 1;

                //boat
                aliveInt[10][10] = 1;
                aliveInt[10][11] = 1;
                aliveInt[11][10] = 1;
                aliveInt[11][12] = 1;
                aliveInt[12][11] = 1;

                //tub
                aliveInt[21][1] = 1;
                aliveInt[20][2] = 1;
                aliveInt[22][2] = 1;
                aliveInt[21][3] = 1;
                lifes = false;
            }
            //same with Oscillators
            if(oscillators) {
                for (int i = 0; i < 64; i++) {
                    for (int j = 0; j < 36; j++) {
                        aliveInt[i][j] = 0;
                    }
                }
                numberOfGeneration = 0;

                //blinker
                aliveInt[21][2] = 1;
                aliveInt[22][2] = 1;
                aliveInt[23][2] = 1;

                //Toad
                aliveInt[21][12] = 1;
                aliveInt[22][12] = 1;
                aliveInt[23][12] = 1;
                aliveInt[20][13] = 1;
                aliveInt[21][13] = 1;
                aliveInt[22][13] = 1;

                //beacon
                aliveInt[11][20] = 1;
                aliveInt[11][21] = 1;
                aliveInt[12][20] = 1;
                aliveInt[12][21] = 1;
                aliveInt[13][22] = 1;
                aliveInt[13][23] = 1;
                aliveInt[14][22] = 1;
                aliveInt[14][23] = 1;

                //pulsar
                aliveInt[4][2] = 1;
                aliveInt[5][2] = 1;
                aliveInt[6][2] = 1;
                aliveInt[10][2] = 1;
                aliveInt[11][2] = 1;
                aliveInt[12][2] = 1;
                aliveInt[2][4] = 1;
                aliveInt[7][4] = 1;
                aliveInt[9][4] = 1;
                aliveInt[14][4] = 1;
                aliveInt[2][5] = 1;
                aliveInt[7][5] = 1;
                aliveInt[9][5] = 1;
                aliveInt[14][5] = 1;
                aliveInt[2][6] = 1;
                aliveInt[7][6] = 1;
                aliveInt[9][6] = 1;
                aliveInt[14][6] = 1;
                aliveInt[4][7] = 1;
                aliveInt[5][7] = 1;
                aliveInt[6][7] = 1;
                aliveInt[10][7] = 1;
                aliveInt[11][7] = 1;
                aliveInt[12][7] = 1;
                aliveInt[4][9] = 1;
                aliveInt[5][9] = 1;
                aliveInt[6][9] = 1;
                aliveInt[10][9] = 1;
                aliveInt[11][9] = 1;
                aliveInt[12][9] = 1;
                aliveInt[4][14] = 1;
                aliveInt[5][14] = 1;
                aliveInt[6][14] = 1;
                aliveInt[10][14] = 1;
                aliveInt[11][14] = 1;
                aliveInt[12][14] = 1;
                aliveInt[2][10] = 1;
                aliveInt[7][10] = 1;
                aliveInt[9][10] = 1;
                aliveInt[14][10] = 1;
                aliveInt[2][11] = 1;
                aliveInt[7][11] = 1;
                aliveInt[9][11] = 1;
                aliveInt[14][11] = 1;
                aliveInt[2][12] = 1;
                aliveInt[7][12] = 1;
                aliveInt[9][12] = 1;
                aliveInt[14][12] = 1;

                //Penta-decathlon
                aliveInt[32][6] = 1;
                aliveInt[32][7] = 1;
                aliveInt[31][8] = 1;
                aliveInt[33][8] = 1;
                aliveInt[32][9] = 1;
                aliveInt[32][10] = 1;
                aliveInt[32][11] = 1;
                aliveInt[32][12] = 1;
                aliveInt[31][13] = 1;
                aliveInt[33][13] = 1;
                aliveInt[32][14] = 1;
                aliveInt[32][15] = 1;
                oscillators = false;
            }
            //same with spaceships
            if(spaceships){
                for(int i = 0;i < 64;i++){
                    for(int j = 0;j < 36; j++){
                        aliveInt[i][j] = 0;
                    }
                }

                //glider
                aliveInt[32][1] = 1;
                aliveInt[33][2] = 1;
                aliveInt[31][3] = 1;
                aliveInt[32][3] = 1;
                aliveInt[33][3] = 1;

                //lightweight spaceship
                aliveInt[1][1] = 1;
                aliveInt[1][3] = 1;
                aliveInt[4][1] = 1;
                aliveInt[5][2] = 1;
                aliveInt[5][3] = 1;
                aliveInt[2][4] = 1;
                aliveInt[3][4] = 1;
                aliveInt[4][4] = 1;
                aliveInt[5][4] = 1;

                //middleweight spaceship
                aliveInt[3][7] = 1;
                aliveInt[1][8] = 1;
                aliveInt[5][8] = 1;
                aliveInt[6][9] = 1;
                aliveInt[1][10] = 1;
                aliveInt[6][10] = 1;
                aliveInt[2][11] = 1;
                aliveInt[3][11] = 1;
                aliveInt[4][11] = 1;
                aliveInt[5][11] = 1;
                aliveInt[6][11] = 1;

                //heavyweight spaceship
                aliveInt[3][15] = 1;
                aliveInt[4][15] = 1;
                aliveInt[1][16] = 1;
                aliveInt[6][16] = 1;
                aliveInt[7][17] = 1;
                aliveInt[1][18] = 1;
                aliveInt[7][18] = 1;
                aliveInt[2][19] = 1;
                aliveInt[3][19] = 1;
                aliveInt[4][19] = 1;
                aliveInt[5][19] = 1;
                aliveInt[6][19] = 1;
                aliveInt[7][19] = 1;

                spaceships = false;
            }

            //gosper's glider gun
            if(gun){
                for(int i = 0;i < 64;i++){
                    for(int j = 0;j < 36;j++){
                        aliveInt[i][j] = 0;
                    }
                }
                numberOfGeneration = 0;

                aliveInt[1][5] = 1;
                aliveInt[2][5] = 1;
                aliveInt[12][4] = 1;
                aliveInt[13][3] = 1;
                aliveInt[11][5] = 1;
                aliveInt[11][6] = 1;
                aliveInt[11][7] = 1;
                aliveInt[15][6] = 1;
                aliveInt[16][8] = 1;
                aliveInt[17][6] = 1;
                aliveInt[18][6] = 1;
                aliveInt[1][6] = 1;
                aliveInt[2][6] = 1;
                aliveInt[14][3] = 1;
                aliveInt[12][8] = 1;
                aliveInt[13][9] = 1;
                aliveInt[14][9] = 1;
                aliveInt[16][4] = 1;
                aliveInt[17][5] = 1;
                aliveInt[17][7] = 1;
                aliveInt[21][3] = 1;
                aliveInt[21][4] = 1;
                aliveInt[21][5] = 1;
                aliveInt[22][3] = 1;
                aliveInt[22][4] = 1;
                aliveInt[22][5] = 1;
                aliveInt[23][2] = 1;
                aliveInt[23][6] = 1;
                aliveInt[25][1] = 1;
                aliveInt[25][2] = 1;
                aliveInt[25][6] = 1;
                aliveInt[25][7] = 1;
                aliveInt[35][3] = 1;
                aliveInt[35][4] = 1;
                aliveInt[36][3] = 1;
                aliveInt[36][4] = 1;
                gun = false;
            }

            //pause (start/stop)
            g.setColor(Color.darkGray);
            if(mouseX > 832 && mouseY > 40 && mouseX < 1112 && mouseY < 100){
                g.setColor(Color.gray);
            }
            g.fillRect(825, 10, 280, 60);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.PLAIN, 60));
            if (pause) {
                g.drawString("START", 875, 60);
            } else if (!pause) {
                g.drawString("STOP", 895, 60);
            }

            //explain
            g.setColor(Color.white);
            g.drawString("Rules", 1300, 60);
            g.setFont(new Font("Tahoma", Font.PLAIN, 12));
            g.drawString("The game followed three rules:", 1290, 100);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("1. Any live cell with two or three", 1290, 130);
            g.drawString("live neighbours survives.", 1305, 145);
            g.drawString("2. Any dead cell with three live", 1290, 165);
            g.drawString("neighbours becomes a live cell.", 1305, 180);
            g.drawString("3. All other live cells die in the", 1290, 200);
            g.drawString("next generation. Similarly, all", 1305, 215);
            g.drawString("other dead cells stay dead.", 1305, 230);
            //types
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.PLAIN, 40));
            g.drawString("Types:", 1290, 280);
            g.setColor(Color.lightGray);
            Font types = new Font("Tahoma", Font.PLAIN, 15);
            Font explaination = new Font("Tahoma", Font.PLAIN, 12);
            g.setFont(types);
            g.drawString("Still lifes:", 1290, 310);
            g.setFont(explaination);
            g.drawString("- do not change from one", 1290, 330);
            g.drawString("generation to the next", 1298, 345);
            g.setFont(types);
            g.drawString("Oscillators: ", 1290, 375);
            g.setFont(explaination);
            g.drawString("- return to their initial state after", 1290, 395);
            g.drawString("a finite number of generations", 1298, 410);
            g.setFont(types);
            g.drawString("Spaceships:", 1290, 440);
            g.setFont(explaination);
            g.drawString("- translate themselves across the", 1290, 460);
            g.drawString("grid.", 1298, 475);


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
            if(mouseX > 17 && mouseX < 217 && mouseY > 40 && mouseY < 100){
                g.setColor(Color.gray);
            }
            g.fillRect(10, 10, 200, 60);
            g.setFont(new Font("Tahoma", Font.PLAIN, 60));
            g.setColor(Color.white);
            g.drawString("CLEAR", 20, 60);

            //speed
            g.setColor(Color.darkGray);
            if (mouseX > 237 && mouseX < 297 && mouseY > 40 && mouseY < 100) {
                g.setColor(Color.gray);
            }
            g.fillRect(230, 10, 60, 60);
            g.setColor(Color.darkGray);
            if (mouseX > 637 && mouseX < 697 && mouseY > 40 && mouseY < 100) {
                g.setColor(Color.GRAY);
            }
            g.fillRect(630, 10, 60, 60);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.BOLD, 100));
            g.drawString("-", 238, 72);
            g.setFont(new Font("Tohoma", Font.BOLD, 80));
            g.drawString("+", 636, 67);
            g.setFont((new Font("Tahoma", Font.PLAIN, 60)));
            if(speed == 0){
                g.drawString("Speed:1", 300, 60);
            }else{
                g.drawString("Speed:" + speed, 300, 60);
            }

            //types
            g.setColor(Color.darkGray);
            //X + 7, Y + 30
            if(mouseX > 17 && mouseX < 217 && mouseY > 840 && mouseY < 900){
                g.setColor(Color.gray);
            }
            g.fillRect(10, 810, 200, 60);
            g.setColor(Color.darkGray);
            if(mouseX > 237 && mouseX < 437 && mouseY > 840 && mouseY < 900){
                g.setColor(Color.gray);
            }
            g.fillRect(230, 810, 200, 60);
            g.setColor(Color.darkGray);
            if(mouseX > 457 && mouseX < 657 && mouseY > 840 && mouseY < 900){
                g.setColor(Color.gray);
            }
            g.fillRect(450, 810, 200, 60);
            g.setColor(Color.darkGray);
            if(mouseX > 677 && mouseX < 967 && mouseY > 840 && mouseY < 900){
                g.setColor(Color.gray);
            }
            g.fillRect(670, 810, 290, 60);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.PLAIN, 44));
            g.drawString("Still Lifes", 20, 855);
            g.setFont(new Font("Tahoma", Font.PLAIN, 40));
            g.drawString("Oscillators", 237, 855);
            g.setFont(new Font("Tahoma", Font.PLAIN, 38));
            g.drawString("Spaceships", 457, 855);
            g.setFont(new Font("Tahoma", Font.PLAIN, 30));
            g.drawString(" Gosper's glider gun", 677, 850);
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
            if(mouseX > 832 && mouseY > 40 && mouseX < 1112 && mouseY < 100){
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

            if(mouseX > 17 && mouseX < 217 && mouseY > 840 && mouseY < 900){
                lifes = true;
            }
            if(mouseX > 237 && mouseX < 437 && mouseY > 840 && mouseY < 900){
                oscillators = true;
            }
            if(mouseX > 457 && mouseX < 657 && mouseY > 840 && mouseY < 900){
                spaceships = true;
            }
            if(mouseX > 677 && mouseX < 967 && mouseY > 840 && mouseY < 900){
                gun = true;
            }

            //speed
            if (mouseX > 237 && mouseX < 297 && mouseY > 40 && mouseY < 100) {
                if (speed > 0) {
                    speed -= 50;
                }
            }
            if (mouseX > 637 && mouseX < 697 && mouseY > 40 && mouseY < 100) {
                if(speed < 1000) {
                    speed += 50;
                }
            }
            //System.out.println(speed);
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
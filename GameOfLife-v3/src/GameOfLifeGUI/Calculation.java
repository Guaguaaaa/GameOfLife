package GameOfLifeGUI;

import static GameOfLifeGUI.Main.gui;

public class Calculation extends Thread{

    /**
     * Any live cell with two or three live neighbours survives.
     * Any dead cell with three live neighbours becomes a live cell.
     * All other live cells die in the next generation. Similarly, all other dead cells stay dead.
     */

    @Override
    public void run() {
        while (true) {
            try {
                //System.out.println("gui.pause is: " + gui.pause);
                System.out.println();
                if(gui.pause == false) {
                    //System.out.println("gui.pause != true");
                    for (int i = 0; i < 64; i++) {
                        for (int j = 0; j < 36; j++) {
                            if (gui.aliveInt[i][j] == 1) {
                                if (gui.neighbours[i][j] < 2 || gui.neighbours[i][j] > 3) {
                                    gui.aliveInt[i][j] = 0;
                                }
                                if (gui.neighbours[i][j] == 2 || gui.neighbours[i][j] == 3) {
                                    gui.aliveInt[i][j] = 1;
                                }
                            }
                            if (gui.aliveInt[i][j] == 0) {
                                if (gui.neighbours[i][j] == 3) {
                                    gui.aliveInt[i][j] = 1;
                                }
                                if (gui.neighbours[i][j] == 2 && gui.aliveInt[i][j] == 1) {
                                    gui.aliveInt[i][j] = 1;
                                }
                            }
                        }
                    }
                    gui.numberOfGeneration++;
                    if (gui.speed == 1000) {
                        Thread.sleep(1);
                    } else {
                        Thread.sleep(1000 - gui.speed);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            //System.out.println("thread calc");
        }
    }
}

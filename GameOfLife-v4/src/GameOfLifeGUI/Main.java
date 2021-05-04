package GameOfLifeGUI;

public class Main implements Runnable{
    static GUI gui = new GUI();
    public static void main(String[] args){

        /**
         * run Calc thread
         * run GUI thread - repaint the paint component
         */

        //start a new thread of GUI
        new Thread(new Main()).start();
        //start a new thread of calculation
        Calculation calc = new Calculation();
        calc.start();
    }

    @Override
    public void run(){
        while(true) {
            try {
                //keep repainting/updating the gui window
                gui.repaint();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

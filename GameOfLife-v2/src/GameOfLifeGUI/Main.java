package GameOfLifeGUI;

public class Main implements Runnable{
    static GUI gui = new GUI();
    public static void main(String[] args){

        /**
         * run Calc thread
         * run paintComponent - repaint() thread
         */

        new Thread(new Main()).start();
        Calculation calc = new Calculation();
        calc.start();
    }

    @Override
    public void run(){
        while(true) {
            try {
                gui.repaint();
                //Thread.sleep(50);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

package GameOfLifeGUI;

public class Main implements Runnable{
    static GUI gui = new GUI();
    public static void main(String[] args){

        new Thread(new Main()).start();
    }

    @Override
    public void run(){
        while(true) {
            try {
                gui.repaint();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

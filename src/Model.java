public class Model {

    private View view;
    private Controller controller;

    // Sample instance variables:
    private int time;
    private String typedChar = "";
//    private int mx, my;
    
    private Airplane ap;

    public Model() {
        view = new View(this);
        controller = new Controller(this);
        ap = new Airplane(this);
    }

    public synchronized void processTimeElapsed(int msec) {
        time++;
        ap.update();
        view.repaint();
    }

    public synchronized void processKeyTyped(String typed) {
        typedChar = typed;
        ap.move(typedChar);
        view.repaint();
    }

    public synchronized void processMousePressed() {
        view.playBombSound();
        view.repaint();
    }

    public void start() {
        controller.start();
    }

    public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }

    public int getTime() {
        return time;
    }

    public String getTypedChar() {
        return typedChar;
    }
    
    public Airplane getAirplane() {
    	return ap;
    }

}

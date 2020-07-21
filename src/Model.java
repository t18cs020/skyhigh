public class Model {

    private View view;
    private Controller controller;

    // Sample instance variables:
    private int time;
    private String typedChar = "";
    private int mx, my;

    public Model() {
        view = new View(this);
        controller = new Controller(this);
    }

    public synchronized void processTimeElapsed(int msec) {
        time++;
        view.repaint();
    }

    public synchronized void processKeyTyped(String typed) {
        typedChar = typed;
        view.repaint();
    }

    public synchronized void processMousePressed(int x, int y) {
        mx = x;
        my = y;
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

    public int getMX() {
        return mx;
    }

    public int getMY() {
        return my;
    }

}

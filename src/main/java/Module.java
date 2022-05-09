import java.util.ArrayList;

public class Module {
    ModuleType type;
    int N;
    int x;
    int y;

    public Module(int n, int x, int y) {
        N = n;
        this.x = x;
        this.y = y;
        for(int i = 0; i < N; i++)
            addContainer(i, "");
    }

    public ModuleType getType() {
        return type;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    ArrayList<Container> containers = new ArrayList<>();

    public void setType(ModuleType type) {
        this.type = type;
    }

    public void addContainer(int n, String productName){
        boolean wasAdded = false;
        if(n >= 0 && n < N){
            Container container = new Container(n, productName);

        for (int i = 0; i < containers.size(); i++){
            if(containers.get(i).getN() == n){
                containers.set(i, container);
                wasAdded = true;
            }
        }

        if(!wasAdded)
            containers.add(container);
        }
    }
    public float getRideTime(){
        return getType().getRideTime();
    }


    public int calculateTakeTime(int n){
        switch (this.type){
            case H -> {
                return 3*n+4;
            }
            case B -> {
                return 2*n+2;
            }
            case S -> {
                return n+1;
            }
            default -> {
                return 0;
            }
        }
    }
}

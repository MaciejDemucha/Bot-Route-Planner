import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Bot {
    int xs, ys, xf, yf, direction, counter;
    float time;
    ArrayList<Integer> xToFind = new ArrayList<>();
    ArrayList<Integer> yToFind = new ArrayList<>();

    ArrayList<Integer> xHistory = new ArrayList<Integer>();
    ArrayList<Integer> yHistory = new ArrayList<>();

    ArrayList<Float> results = new ArrayList<>();
    boolean hasProduct = false;
    Grid grid;
    String product;

    public Bot(int xs, int ys, int xf, int yf, String product) {
        this.xs = xs;
        this.ys = ys;
        this.xf = xf;
        this.yf = yf;
        this.product = product;
    }

    public int getXs() {
        return xs;
    }

    public void setXs(int xs) {
        this.xs = xs;
    }

    public int getYs() {
        return ys;
    }

    public void setYs(int ys) {
        this.ys = ys;
    }

    public void setStart(int x, int y){
        setXs(x);
        setYs(y);
    }

    public int getXf() {
        return xf;
    }

    public void setXf(int xf) {
        this.xf = xf;
    }

    public int getYf() {
        return yf;
    }

    public void setYf(int yf) {
        this.yf = yf;
    }

    public void setFinish(int x, int y){
        setXf(x);
        setYf(y);
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public String getTask(){
        return "Product name: " + product + "\n"+ "Start: "+ getXs() + " " +  getYs() + "\nFinish: "+ getXf() + " " +  getYf();
    }

    public static Bot readFromFile(BufferedReader reader) throws Exception{
        try {
            String line = reader.readLine();
            String[] txt = line.split(" ");

            Bot bot = new Bot(0, 0,0,0,"");

            bot.setStart(Integer.parseInt(txt[0]), Integer.parseInt(txt[1]));
            line = reader.readLine();
            txt = line.split(" ");
            bot.setFinish(Integer.parseInt(txt[0]), Integer.parseInt(txt[1]));
            line = reader.readLine();
            txt = line.split(" ");
            bot.setProduct(txt[0]);

            return bot;
        } catch(IOException e){
            throw new Exception("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    public static Bot readFromFile(String file_name) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
            return Bot.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new Exception("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new Exception("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    public void getPath(){
            for(int i = 0; i < grid.getX(); i++){
                for(int j = 0; j < grid.getY(); j++){
                    for(int k = 0; k < grid.gridArray[i][j].getN(); k++){
                        if(grid.getProductInContainer(i, j, k).equals(product)){
                            xToFind.add(i);
                            yToFind.add(j);
                        }
                    }
                }
            }

        //if xp < 0 -> right
        //if yp < 0 -> down
        //abs xp i yp -> ile ruchów
        int backupXs = this.xs;
        int backupYs = this.ys;
        xHistory.add(backupXs);
        yHistory.add(backupYs);
        for (int i = 0; i < xToFind.size(); i++) {
            int xp = this.xs - xToFind.get(i);
            int yp = this.ys - yToFind.get(i);
            while(xs != xToFind.get(i) || ys != yToFind.get(i)){
                chooseDirection(xp, yp);
            }

            int n = 0;
            for(int j = 0; j < grid.gridArray[xToFind.get(i)][yToFind.get(i)].getN(); j++){
                if(grid.gridArray[xToFind.get(i)][yToFind.get(i)].getContainers().get(j).getProductName().equals(product))
                    break;
                n++;
            }

            time += grid.gridArray[xToFind.get(i)][yToFind.get(i)].calculateTakeTime(n);
            hasProduct = true;
            xToFind.set(i, xf);
            yToFind.set(i, yf);
            xp = this.xs - xToFind.get(i);
            yp = this.ys - yToFind.get(i);

            while(xs != xToFind.get(i) || ys != yToFind.get(i)){
                chooseDirection(xp, yp);
            }
            System.out.println(time);
            System.out.println(counter);
            for(int a = 0; a < xHistory.size(); a++)
                System.out.println(xHistory.get(a) + " " + yHistory.get(a));

            results.add(time);
            Collections.sort(results);
            time = 0;
            counter = 0;
            xHistory.clear();
            yHistory.clear();
            xHistory.add(backupXs);
            yHistory.add(backupYs);
            this.xs = backupXs;
            this.ys = backupYs;
        }

        System.out.println(results.get(0));
    }
    //1 - right
    //2 - down
    //3 - left
    //4 - up
    public void chooseDirection(int xp, int yp){
            float cost = Float.POSITIVE_INFINITY;
            float   goRight = checkRideCost(xs, ys),
                    goDown = checkRideCost(xs, ys),
                    goLeft = checkRideCost(xs, ys),
                    goUp = checkRideCost(xs, ys);

            if(xs+1 < grid.getX() && checkRideCost(xs+1, ys) != Float.POSITIVE_INFINITY && checkRideCost(xs+1, ys) > checkRideCost(xs, ys))
                goRight = checkRideCost(xs+1, ys);

            if(ys+1 < grid.getY() && checkRideCost(xs, ys+1) != Float.POSITIVE_INFINITY && checkRideCost(xs, ys+1) > checkRideCost(xs, ys))
                goDown = checkRideCost(xs, ys+1);

            if(xs-1 >= 0 && checkRideCost(xs-1, ys) != Float.POSITIVE_INFINITY && checkRideCost(xs-1, ys) > checkRideCost(xs, ys))
                goLeft = checkRideCost(xs-1, ys);

            if(ys-1 >= 0 && checkRideCost(xs, ys-1) != Float.POSITIVE_INFINITY && checkRideCost(xs, ys-1) > checkRideCost(xs, ys))
                goUp = checkRideCost(xs, ys-1);

            if(xs+1 < grid.getX() && !isOutOfOrderRight() && xp < 0 &&  cost > goRight){
                direction = 1;
                cost = goRight;
            }
            if(ys+1 < grid.getY() && !isOutOfOrderDown() && yp < 0 &&  cost > goDown){
                direction = 2;
                cost = goDown;
            }

            if(xs-1 >= 0 && !isOutOfOrderLeft() && xp > 0 &&  cost > goLeft){
                direction = 3;
                cost = goLeft;
            }

            if(ys-1 >= 0 && !isOutOfOrderUp() && yp > 0 &&  cost > goUp){
                direction = 4;
                cost = goUp;
            }

            switch (direction){
                case 1 -> this.xs++;
                case 2 -> this.ys++;
                case 3 -> this.xs--;
                case 4 -> this.ys--;
            }
            time += cost;
            counter++;
            xHistory.add(this.xs);
            yHistory.add(this.ys);
    }

    public float checkRideCost(int x, int y){
        return grid.gridArray[x][y].getRideTime();
    }

    public boolean isOutOfOrderRight(){
       return grid.gridArray[xs+1][ys].getType().equals(ModuleType.O);
    }

    public boolean isOutOfOrderLeft(){
        return grid.gridArray[xs-1][ys].getType().equals(ModuleType.O);
    }

    public boolean isOutOfOrderDown(){
        return grid.gridArray[xs][ys+1].getType().equals(ModuleType.O);
    }

    public boolean isOutOfOrderUp(){
        return grid.gridArray[xs][ys-1].getType().equals(ModuleType.O);
    }
}

import java.io.*;

public class Grid {
    private int X, Y;
    Module[][] gridArray;
    public Grid(int x, int y) {
        X = x;
        Y = y;
        gridArray = new Module[x][y];
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public String getSize(){
        return getX() + "x" + getY();
    }

    public String getProductInContainer(int x, int y, int n){
        return gridArray[x][y].getContainers().get(n).getProductName();
    }

    public static Grid readFromFile(BufferedReader reader) throws Exception{
        try {
            String line = reader.readLine();
            String[] txt = line.split(" ");
            //tworzenie gridu
            Grid grid = new Grid(Integer.parseInt(txt[0]), Integer.parseInt(txt[1]));
            //wypełnienie go modułami
            for(int i = 0; i < grid.getX(); i++){
                for(int j = 0; j < grid.getY(); j++){
                    grid.gridArray[i][j] = new Module(Integer.parseInt(txt[2]), i, j);
                }
            }
            //ustawienie typów modułów i dodanie pustych kontenerów
            for(int i = 0; i < grid.getY(); i++){
                line = reader.readLine();
                txt = line.split("");
                for(int j = 0; j < grid.getX(); j++){
                    grid.gridArray[j][i].setType(ModuleType.valueOf(txt[j]));
                }
            }
            //wpakowanie produktów w wybrane miejsca
            line=reader.readLine();
            while(line != null){
                txt = line.split(" ");
                grid.gridArray[Integer.parseInt(txt[1])][Integer.parseInt(txt[2])].addContainer(Integer.parseInt(txt[3]), txt[0]);
                line = reader.readLine();
            }

            return grid;
        } catch(IOException e){
            throw new Exception("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    public static Grid readFromFile(String file_name) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
            return Grid.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new Exception("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new Exception("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }
}

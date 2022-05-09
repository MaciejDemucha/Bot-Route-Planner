public class Main {
    public static void main(String[] args) throws Exception {
        Grid grid = Grid.readFromFile(args[0]);
        Bot bot = Bot.readFromFile(args[1]);
        bot.setGrid(grid);
        bot.getPath();
    }
}

package game;
import game.controller.*;
import game.model.*;
import javax.swing.*;

public class Game{
    static int N;
    static boolean bot;

    public static void main(String[] args){
        boolean test = true;

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Dots and Boxes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            if (test){
                N = 3;
                bot = true;
            }
            else {
                // ask the user for the size of the grid and if he wants to play against a bot
                JOptionPane.showMessageDialog(frame, """
                        Welcome to Dots and Boxes!
                        The goal of the game is to close as many boxes as possible.
                        You can close a box by adding the fourth side to it.
                        When you close a box, you get a point and can play again.
                        The player who closes the most boxes wins.
                        Good luck!""");
                try {
                    N = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the size of the grid (between 2 and 10):"));
                    if (N < 2 || N > 10) {
                        throw new NumberFormatException("Grid size has to be between 2 and 10.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Defaulting to 5.");
                    N = 5;
                }
                int botInt = JOptionPane.showConfirmDialog(frame, "Play against a bot?", "Bot", JOptionPane.YES_NO_OPTION);
                bot = botInt == JOptionPane.YES_OPTION;
            }


            Grid grid = new Grid(N, bot);

            GameView view = new GameView(grid);

            Controller controller = new Controller(grid, view);

            MouseController mouseController = new MouseController(view, controller, grid);
            view.addMouseListener(mouseController);
            view.addMouseMotionListener(mouseController);

            frame.add(view);
            frame.setVisible(true);

            if (grid.getPlayer1().isBot()){
                view.repaint();
                view.paintImmediately(view.getBounds());
                controller.botTurn();
            }
        });
    }
}

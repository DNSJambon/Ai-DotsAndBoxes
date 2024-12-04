package dqn;
import game.model.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class SelfPlay {
    public static void main(String[] args){
        GameState game = new GameState(5);

        int stop = 100;

        while (!game.isGameOver()){
            int[] state = game.getState();
            System.out.println(Arrays.toString(game.getState()));
            int action = new Random().nextInt(state.length);
            while (state[action] != 0) {
                action = new Random().nextInt(state.length);
            }
            game.applyAction(action);
            stop--;
            if (stop == 0){
                break;
            }

        }
        System.out.println(Arrays.toString(game.getState()));
        SwingUtilities.invokeLater(() -> DisplayGameState.display(game.grid));


        System.out.println("Game Over");
        System.out.println("Player 1: " + game.grid.getPlayer1().getScore());
        System.out.println("Player 2: " + game.grid.getPlayer2().getScore());



    }
}

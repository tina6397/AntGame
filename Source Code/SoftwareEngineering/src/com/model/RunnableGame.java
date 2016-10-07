
package com.model;

import com.model.exceptions.AntNotFoundException;

/**
 * Created by junho on 15. 4. 1..
 */
public class RunnableGame implements Runnable {

    Game game;

    /**
     * Runs the game for 300000 rounds
     * @param game
     */
    public RunnableGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 300000; i++) {
            game.nextRound();
            if (i % 1000 == 0) {
                game.getGUI().updateGUI(game);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }     //
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        game.getGUI().updateGUI(game);


        System.out.println(game.getWinner());

        try {
            game.getGUI().signalGameEnd(game);
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }
    }
}
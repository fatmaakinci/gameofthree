package com.fatma.gameofthree.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameFinishEvent extends GameEvent
{
    private String winner;

    public GameFinishEvent(String gameId, String winner)
    {
        super(gameId);
        this.winner = winner;
    }

    @Override
    public String getMessage()
    {
        return String.format("Game is over. The winner is %s.", winner);
    }
}

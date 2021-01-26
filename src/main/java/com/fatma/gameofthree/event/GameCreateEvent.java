package com.fatma.gameofthree.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameCreateEvent extends GameEvent
{
    private final String player;

    public GameCreateEvent(String gameId, String player)
    {
        super(gameId);
        this.player = player;
    }

    @Override
    public String getMessage()
    {
        return "New game is successfully created. Please wait for another player to join.";
    }
}

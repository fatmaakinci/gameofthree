package com.fatma.gameofthree.model.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameCreateEvent extends AbstractGameEvent
{
    public GameCreateEvent(String gameId)
    {
        super(gameId);
    }

    @Override
    public String getMessage()
    {
        return "New game is successfully created. Please wait for another player to join.";
    }
}

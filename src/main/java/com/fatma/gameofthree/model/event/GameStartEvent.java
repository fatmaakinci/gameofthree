package com.fatma.gameofthree.model.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameStartEvent extends AbstractGameEvent
{
    private final int startNumber;

    public GameStartEvent(String gameId, int startNumber)
    {
        super(gameId);
        this.startNumber = startNumber;
    }

    @Override
    public String getMessage()
    {
        return String.format("Game is started with number %s.", startNumber);
    }
}

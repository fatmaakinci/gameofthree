package com.fatma.gameofthree.model.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class PlayerMoveEvent extends AbstractGameEvent
{
    private final String player;

    private final int addedNumber;

    private final int resultNumber;

    public PlayerMoveEvent(String gameId, String player, int addedNumber, int resultNumber)
    {
        super(gameId);
        this.player = player;
        this.addedNumber = addedNumber;
        this.resultNumber = resultNumber;
    }

    @Override
    public String getMessage()
    {
        return String.format("%s made move. Added:%d, Result:%d", player, addedNumber, resultNumber);
    }
}

package com.fatma.gameofthree.event;

import com.fatma.gameofthree.helper.CalculationResult;
import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class PlayerMoveEvent extends GameEvent
{
    private final String player;

    private final int addedNumber;

    private final int resultNumber;

    public PlayerMoveEvent(String gameId, String player, CalculationResult moveResult)
    {
        super(gameId);
        this.player = player;
        this.addedNumber = moveResult.getAddedNumber();
        this.resultNumber = moveResult.getResultNumber();
    }

    @Override
    public String getMessage()
    {
        return String.format("%s made move. Added:%d, Result:%d", player, addedNumber, resultNumber);
    }
}

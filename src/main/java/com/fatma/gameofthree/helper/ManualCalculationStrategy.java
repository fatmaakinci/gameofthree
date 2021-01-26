package com.fatma.gameofthree.helper;

import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;

import java.util.Set;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
public class ManualCalculationStrategy implements ICalculationStrategy
{
    @Override
    public CalculationResult calculate(Integer number, Integer addition)
    {
        if (!Set.of(-1, 0, 1).contains(addition))
        {
            throw new GameException(ErrorCode.INVALID_ADDITION_RANGE);
        }

        int addedNumber = number + addition;

        if ((addedNumber % 3) != 0)
        {
            throw new GameException(ErrorCode.INVALID_ADDITION);
        }

        return new CalculationResult(addition,addedNumber / 3);
    }
}

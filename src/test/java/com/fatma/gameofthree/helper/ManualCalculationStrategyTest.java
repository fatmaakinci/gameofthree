package com.fatma.gameofthree.helper;

import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@ExtendWith(SpringExtension.class)
public class ManualCalculationStrategyTest
{
    private final ManualCalculationStrategy calculationStrategy = new ManualCalculationStrategy();

    @Test
    public void testInvalidRange()
    {
        GameException gameException = assertThrows(GameException.class,
                () -> calculationStrategy.calculate(13, 2));

        assertEquals(ErrorCode.INVALID_ADDITION_RANGE, gameException.getErrorCode());
    }

    @Test
    public void testInvalidAddition()
    {
        GameException gameException = assertThrows(GameException.class,
                () -> calculationStrategy.calculate(13, 1));

        assertEquals(ErrorCode.INVALID_ADDITION, gameException.getErrorCode());
    }

    @Test
    public void test_subtract_one()
    {
        CalculationResult result = calculationStrategy.calculate(13, -1);

        assertEquals(-1, result.getAddedNumber());
        assertEquals(4, result.getResultNumber());
    }

    @Test
    public void test_add_one()
    {
        CalculationResult result = calculationStrategy.calculate(14, 1);

        assertEquals(1, result.getAddedNumber());
        assertEquals(5, result.getResultNumber());
    }

    @Test
    public void test_add_zero()
    {
        CalculationResult result = calculationStrategy.calculate(18, 0);

        assertEquals(0, result.getAddedNumber());
        assertEquals(6, result.getResultNumber());
    }
}

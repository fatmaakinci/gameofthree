package com.fatma.gameofthree.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@ExtendWith(SpringExtension.class)
public class AutoCalculationStrategyTest
{
    private final AutoCalculationStrategy calculationStrategy = new AutoCalculationStrategy();

    @Test
    public void testSubtractOne()
    {
        CalculationResult result = calculationStrategy.calculate(13, null);

        assertEquals(-1, result.getAddedNumber());
        assertEquals(4, result.getResultNumber());
    }

    @Test
    public void testAddOne()
    {
        CalculationResult result = calculationStrategy.calculate(14, null);

        assertEquals(1, result.getAddedNumber());
        assertEquals(5, result.getResultNumber());
    }

    @Test
    public void testAddZero()
    {
        CalculationResult result = calculationStrategy.calculate(18, null);

        assertEquals(0, result.getAddedNumber());
        assertEquals(6, result.getResultNumber());
    }
}

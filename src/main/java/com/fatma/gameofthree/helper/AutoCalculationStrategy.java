package com.fatma.gameofthree.helper;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
public class AutoCalculationStrategy implements ICalculationStrategy
{
    @Override
    public CalculationResult calculate(Integer number, Integer addition)
    {
        int remaining = number % 3;
        int divisionResult = number / 3;

        if (remaining == 0)
        {
            return new CalculationResult(0, divisionResult);
        }
        else if (remaining == 1)
        {
            return new CalculationResult(-1, divisionResult);
        }
        else
        {
            return new CalculationResult(1, divisionResult + 1);
        }
    }
}

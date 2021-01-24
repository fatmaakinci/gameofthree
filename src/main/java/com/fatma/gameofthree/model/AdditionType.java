package com.fatma.gameofthree.model;

import com.fatma.gameofthree.helper.AutoCalculationStrategy;
import com.fatma.gameofthree.helper.ICalculationStrategy;
import com.fatma.gameofthree.helper.ManualCalculationStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
@RequiredArgsConstructor
public enum AdditionType
{
    MANUAL(new ManualCalculationStrategy()),
    AUTO(new AutoCalculationStrategy());

    private final ICalculationStrategy calculationStrategy;
}



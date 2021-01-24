package com.fatma.gameofthree.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
@AllArgsConstructor
public abstract class AbstractGameEvent
{
    private final String gameId;

    public abstract String getMessage();
}

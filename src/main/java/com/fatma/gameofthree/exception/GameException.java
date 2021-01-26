package com.fatma.gameofthree.exception;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameException extends RuntimeException
{
    private final ErrorCode errorCode;

    public GameException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

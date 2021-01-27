package com.fatma.gameofthree.exception;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class GameException extends RuntimeException
{
    private final ErrorCode errorCode;

    private final String username;

    public GameException(ErrorCode errorCode, String username)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.username = username;
    }
}

package com.fatma.gameofthree.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode
{
    INVALID_STARTER("You can not start the game!"),
    INVALID_TURN("It is not your turn!"),
    INVALID_PLAYER("You are not a player of this game!"),
    INVALID_GAME_STATE("Game is not in valid state!"),
    INVALID_ADDITION_RANGE("Invalid number selected. You can only choose {-1, 0, 1}"),
    INVALID_ADDITION("Invalid number selected. The result after addition should be divisible by 3."),
    GAME_NOT_FOUND("Game not found!");

    private final String message;
}

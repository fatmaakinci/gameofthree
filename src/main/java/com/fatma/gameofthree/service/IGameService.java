package com.fatma.gameofthree.service;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;

/**
 * Created by fatmaakinci on 23.01.2021.
 */
public interface IGameService
{
    void joinGame(String username);

    void startGame(StartGameRequest request);

    void makeMove(MakeMoveRequest request);
}

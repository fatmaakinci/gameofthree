package com.fatma.gameofthree.controller;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;
import com.fatma.gameofthree.exception.GameException;
import com.fatma.gameofthree.service.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * Created by fatmaakinci on 23.01.2021.
 */
@Controller
@RequiredArgsConstructor
public class GameController
{
    private final IGameService gameService;

    @MessageMapping("/join/{username}")
    public void join(@DestinationVariable String username)
    {
        gameService.joinGame(username);
    }

    @MessageMapping("/start")
    public void start(StartGameRequest request)
    {
        gameService.startGame(request);
    }

    @MessageMapping("/move")
    public void move(MakeMoveRequest request)
    {
        gameService.makeMove(request);
    }

    @MessageExceptionHandler
    @SendToUser("queue/error")
    public String handleException(GameException ex)
    {
        return ex.getMessage();
    }
}

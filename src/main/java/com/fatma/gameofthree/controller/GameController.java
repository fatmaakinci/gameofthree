package com.fatma.gameofthree.controller;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;
import com.fatma.gameofthree.exception.GameException;
import com.fatma.gameofthree.service.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * Created by fatmaakinci on 23.01.2021.
 */
@Controller
@RequiredArgsConstructor
public class GameController
{
    private static final String USER_ERROR_QUEUE = "/queue/error/";

    private final IGameService gameService;

    private final SimpMessageSendingOperations messageSendingOperations;

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
    public void handleException(GameException ex)
    {
        messageSendingOperations.convertAndSend(USER_ERROR_QUEUE + ex.getUsername(), ex.getMessage());
    }
}

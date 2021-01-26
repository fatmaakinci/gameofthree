package com.fatma.gameofthree.service;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;
import com.fatma.gameofthree.event.*;
import com.fatma.gameofthree.event.listener.GameEventListener;
import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;
import com.fatma.gameofthree.model.AdditionType;
import com.fatma.gameofthree.model.Game;
import com.fatma.gameofthree.model.GameState;
import com.fatma.gameofthree.repository.IGameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameServiceTest
{
    private static final String PLAYER1 = "john";
    private static final String PLAYER2 = "marry";

    private static final String GAME_ID = "abc-2345";

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameRepository gameRepository;

    @MockBean
    private GameEventListener eventListener;

    @Test
    public void testJoinGame_newGameCreated()
    {
        gameService.joinGame(PLAYER1);

        List<Game> games = gameRepository.findAll();
        assertEquals(1, games.size());
        assertGame(games.get(0), GameState.HAS_EMPTY_SLOT, PLAYER1);

        verify(eventListener, times(1)).processGameCreateEvent(any(GameCreateEvent.class));
        verify(eventListener, times(1)).processGameEvent(any(GameCreateEvent.class));
    }

    @Test
    @Sql("/game_with_empty_slot.sql")
    public void testJoinGame_joinedExistingGame()
    {
        gameService.joinGame(PLAYER2);

        Game game = gameRepository.findFirstByState(GameState.READY);
        assertEquals(GAME_ID, game.getId());
        assertGame(game, GameState.READY, PLAYER1, PLAYER2);

        verify(eventListener, times(1)).processPlayerJoinEvent(any(PlayerJoinEvent.class));
        verify(eventListener, times(1)).processGameEvent(any(PlayerJoinEvent.class));
    }

    @Test
    public void testStartGame_gameNotExists()
    {
        StartGameRequest request = new StartGameRequest(GAME_ID, PLAYER1);

        GameException gameException = assertThrows(GameException.class, () -> gameService.startGame(request));

        assertEquals(ErrorCode.GAME_NOT_FOUND, gameException.getErrorCode());

        verify(eventListener, never()).processGameEvent(any(GameStartEvent.class));
    }

    @Test
    @Sql("/game_ready.sql")
    public void testStartGame_success()
    {
        StartGameRequest request = new StartGameRequest(GAME_ID, PLAYER1);

        gameService.startGame(request);

        List<Game> games = gameRepository.findAll();
        assertEquals(1, games.size());
        assertGame(games.get(0), GameState.STARTED, PLAYER1, PLAYER2);

        verify(eventListener, times(1)).processGameEvent(any(GameStartEvent.class));
    }

    @Test
    public void tesMakeMove_gameNotExists()
    {
        MakeMoveRequest request = new MakeMoveRequest(GAME_ID, PLAYER1, AdditionType.AUTO, null);

        GameException gameException = assertThrows(GameException.class, () -> gameService.makeMove(request));

        assertEquals(ErrorCode.GAME_NOT_FOUND, gameException.getErrorCode());

        verify(eventListener, never()).processGameEvent(any(PlayerMoveEvent.class));
    }

    @Test
    @Sql("/game_started.sql")
    public void testMakeMove_success()
    {
        MakeMoveRequest request = new MakeMoveRequest(GAME_ID, PLAYER1, AdditionType.AUTO, null);

        gameService.makeMove(request);

        List<Game> games = gameRepository.findAll();
        assertEquals(1, games.size());
        assertGame(games.get(0), GameState.FINISHED, PLAYER1, PLAYER2);

        verify(eventListener, times(1)).processGameEvent(any(PlayerMoveEvent.class));
        verify(eventListener, times(1)).processGameEvent(any(GameFinishEvent.class));
    }

    private void assertGame(Game game, GameState state, String firstPlayer, String secondPlayer)
    {
        assertGame(game, state, firstPlayer);
        assertEquals(secondPlayer, game.getSecondPlayer());
    }

    private void assertGame(Game game, GameState state, String firstPlayer)
    {
        assertEquals(state, game.getState());
        assertEquals(firstPlayer, game.getFirstPlayer());
    }
}

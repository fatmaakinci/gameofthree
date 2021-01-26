package com.fatma.gameofthree.model;

import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@ExtendWith(SpringExtension.class)
public class GameTest
{
    private static final String PLAYER1 = "john";
    private static final String PLAYER2 = "marry";

    @Test
    public void testCreateGame()
    {
        Game game = new Game(PLAYER1);

        assertNotNull(game.getId());
        assertEquals(PLAYER1, game.getFirstPlayer());
        assertEquals(GameState.HAS_EMPTY_SLOT, game.getState());
        assertTrue(game.isFirstPlayerTurn());
    }

    @Test
    public void testAddSecondPlayer()
    {
        Game game = new Game(PLAYER1);

        game.addSecondPlayer(PLAYER2);

        assertEquals(PLAYER2, game.getSecondPlayer());
        assertEquals(GameState.READY, game.getState());
    }

    @Test
    public void testStartGame_invalidState()
    {
        Game game = new Game(PLAYER1);

        GameException gameException = assertThrows(GameException.class, () -> game.startGame(PLAYER1));

        assertEquals(ErrorCode.INVALID_GAME_STATE, gameException.getErrorCode());
    }

    @Test
    public void testStartGame_invalidUser()
    {
        Game game = new Game(PLAYER1);
        game.addSecondPlayer(PLAYER2);

        GameException gameException = assertThrows(GameException.class, () -> game.startGame(PLAYER2));

        assertEquals(ErrorCode.INVALID_STARTER, gameException.getErrorCode());
    }

    @Test
    public void testStartGame_success()
    {
        Game game = new Game(PLAYER1);
        game.addSecondPlayer(PLAYER2);

        game.startGame(PLAYER1);

        assertTrue(game.getNumber() > 100 && game.getNumber() < 400);
        assertEquals(GameState.STARTED, game.getState());
        assertFalse(game.isFirstPlayerTurn());
    }

    @Test
    public void testMakeMove_invalidState()
    {
        Game game = new Game(PLAYER1);

        GameException gameException = assertThrows(GameException.class, () -> game.makeMove(PLAYER1, AdditionType.AUTO, null));

        assertEquals(ErrorCode.INVALID_GAME_STATE, gameException.getErrorCode());
    }

    @Test
    public void testMakeMove_invalidPlayer()
    {
        Game game = new Game(PLAYER1);
        game.addSecondPlayer(PLAYER2);
        game.startGame(PLAYER1);

        GameException gameException = assertThrows(GameException.class, () -> game.makeMove("stranger", AdditionType.AUTO, null));

        assertEquals(ErrorCode.INVALID_PLAYER, gameException.getErrorCode());
    }

    @Test
    public void testMakeMove_invalidTurn()
    {
        Game game = new Game(PLAYER1);
        game.addSecondPlayer(PLAYER2);
        game.startGame(PLAYER1);

        GameException gameException = assertThrows(GameException.class, () -> game.makeMove(PLAYER1, AdditionType.AUTO, null));

        assertEquals(ErrorCode.INVALID_TURN, gameException.getErrorCode());
    }

    @Test
    public void testMakeMove_valid()
    {
        Game game = new Game(PLAYER1);
        game.addSecondPlayer(PLAYER2);
        game.startGame(PLAYER1);
        game.setNumber(35);

        game.makeMove(PLAYER2, AdditionType.AUTO, null);

        assertEquals(12, game.getNumber());
        assertTrue(game.isFirstPlayerTurn());

        game.makeMove(PLAYER1, AdditionType.MANUAL, 0);

        assertEquals(4, game.getNumber());
        assertFalse(game.isFirstPlayerTurn());

        game.makeMove(PLAYER2, AdditionType.MANUAL, -1);

        assertEquals(1, game.getNumber());
        assertEquals(GameState.FINISHED, game.getState());
    }
}

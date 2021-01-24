package com.fatma.gameofthree.service;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;
import com.fatma.gameofthree.model.exception.ErrorCode;
import com.fatma.gameofthree.model.exception.GameException;
import com.fatma.gameofthree.model.Game;
import com.fatma.gameofthree.model.GameState;
import com.fatma.gameofthree.repository.IGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GameService implements IGameService
{
    private final IGameRepository gameRepository;

    @Override
    public void joinGame(String username)
    {
        Game game = gameRepository.findFirstByState(GameState.HAS_EMPTY_SLOT);

        if (game == null)
        {
            game = new Game(username);
            //TODO new game created event
        }
        else
        {
            game.addSecondPlayer(username);

            //TODO fire second player joined to game event
        }

        gameRepository.save(game);
    }

    @Override
    public void startGame(StartGameRequest request)
    {
        Game game = findGame(request.getGameId());

        game.startGame(request.getPlayerName());

        gameRepository.save(game);

        //TODO fire game started event
        //TODO First move made automatically
    }

    @Override
    public void makeMove(MakeMoveRequest request)
    {
        Game game = findGame(request.getGameId());

        game.makeMove(request.getPlayerName(), request.getAdditionType(), request.getAddition());

        gameRepository.save(game);

        //TODO fire move maded event
        //TODO fire game finished event
    }

    private Game findGame(String gameId)
    {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameException(ErrorCode.GAME_NOT_FOUND));
    }
}

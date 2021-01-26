package com.fatma.gameofthree.service;

import com.fatma.gameofthree.controller.dto.MakeMoveRequest;
import com.fatma.gameofthree.controller.dto.StartGameRequest;
import com.fatma.gameofthree.model.Game;
import com.fatma.gameofthree.model.GameState;
import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;
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
        }
        else
        {
            game.addSecondPlayer(username);
        }

        gameRepository.save(game);
    }

    @Override
    public void startGame(StartGameRequest request)
    {
        Game game = findGame(request.getGameId());

        game.startGame(request.getPlayerName());

        gameRepository.save(game);
    }

    @Override
    public void makeMove(MakeMoveRequest request)
    {
        Game game = findGame(request.getGameId());

        game.makeMove(request.getPlayerName(), request.getAdditionType(), request.getAddition());

        gameRepository.save(game);
    }

    private Game findGame(String gameId)
    {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameException(ErrorCode.GAME_NOT_FOUND));
    }
}

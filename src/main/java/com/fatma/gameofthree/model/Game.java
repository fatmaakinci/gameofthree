package com.fatma.gameofthree.model;

import com.fatma.gameofthree.event.*;
import com.fatma.gameofthree.exception.ErrorCode;
import com.fatma.gameofthree.exception.GameException;
import com.fatma.gameofthree.helper.CalculationResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Created by fatmaakinci on 23.01.2021.
 */
@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
public class Game extends AbstractAggregateRoot<Game>
{
    private static final int NUMBER_LOWER_BOUND = 100;
    private static final int NUMBER_UPPER_BOUND = 400;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_player")
    private String firstPlayer;

    @Column(name = "second_player")
    private String secondPlayer;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private GameState state;

    @Column(name = "first_player_turn")
    private boolean firstPlayerTurn;

    @Column(name = "number")
    private Integer number;

    public Game(String firstPlayer)
    {
        this.firstPlayer = firstPlayer;
        this.id = UUID.randomUUID().toString();
        this.state = GameState.HAS_EMPTY_SLOT;
        this.firstPlayerTurn = true;

        registerEvent(new GameCreateEvent(id, firstPlayer));
    }

    public void addSecondPlayer(String secondPlayer)
    {
        this.secondPlayer = secondPlayer;
        this.state = GameState.READY;

        registerEvent(new PlayerJoinEvent(id, secondPlayer));
    }

    public void startGame(String player)
    {
        if (state != GameState.READY)
        {
            throw new GameException(ErrorCode.INVALID_GAME_STATE, player);
        }

        if (firstPlayer.equals(player))
        {
            this.number = generateRandomNumber();
            this.state = GameState.STARTED;
            switchTurn();

            registerEvent(new GameStartEvent(id, number));
        }
        else
        {
            throw new GameException(ErrorCode.INVALID_STARTER, player);
        }
    }

    public void makeMove(String player, AdditionType additionType, Integer addition)
    {
        if (state != GameState.STARTED)
        {
            throw new GameException(ErrorCode.INVALID_GAME_STATE, player);
        }

        if (isUserAllowedToMakeMove(player))
        {
            CalculationResult result = additionType.getCalculationStrategy().calculate(number, addition, player);
            this.number = result.getResultNumber();
            switchTurn();

            registerEvent(new PlayerMoveEvent(id, player, result));

            if (number == 1)
            {
                this.state = GameState.FINISHED;

                registerEvent(new GameFinishEvent(id, player));
            }
        }
    }

    private boolean isUserAllowedToMakeMove(String player)
    {
        if (!Set.of(firstPlayer, secondPlayer).contains(player))
        {
            throw new GameException(ErrorCode.INVALID_PLAYER, player);
        }
        if (isFirstPlayerTurn() && player.equals(firstPlayer))
        {
            return true;
        }

        if (isSecondPlayerTurn() && player.equals(secondPlayer))
        {
            return true;
        }

        throw new GameException(ErrorCode.INVALID_TURN, player);
    }

    private boolean isSecondPlayerTurn()
    {
        return !firstPlayerTurn;
    }

    private void switchTurn()
    {
        this.firstPlayerTurn = !firstPlayerTurn;
    }

    private int generateRandomNumber()
    {
        return new Random().nextInt(NUMBER_UPPER_BOUND - NUMBER_LOWER_BOUND) + NUMBER_LOWER_BOUND;
    }
}

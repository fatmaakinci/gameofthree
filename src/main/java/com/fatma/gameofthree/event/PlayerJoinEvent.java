package com.fatma.gameofthree.event;

import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class PlayerJoinEvent extends GameEvent
{
    private final String player;

    public PlayerJoinEvent(String gameId, String player)
    {
        super(gameId);
        this.player = player;
    }

    @Override
    public String getMessage()
    {
        return String.format("%s has joined the game.", player);
    }
}

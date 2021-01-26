package com.fatma.gameofthree.event.listener;

import com.fatma.gameofthree.event.GameCreateEvent;
import com.fatma.gameofthree.event.GameEvent;
import com.fatma.gameofthree.event.PlayerJoinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@Component
@RequiredArgsConstructor
public class GameEventListener
{
    private static final String GAME_TOPIC = "/topic/game/";

    private static final String USER_QUEUE = "/queue/message/";

    private final SimpMessageSendingOperations messageSendingOperations;

    @TransactionalEventListener
    public void processGameCreateEvent(GameCreateEvent event)
    {
        messageSendingOperations.convertAndSend(USER_QUEUE + event.getPlayer(), event);
    }

    @TransactionalEventListener
    public void processPlayerJoinEvent(PlayerJoinEvent event)
    {
        messageSendingOperations.convertAndSend(USER_QUEUE + event.getPlayer(), event);
    }

    @TransactionalEventListener
    public void processGameEvent(GameEvent event)
    {
        messageSendingOperations.convertAndSend(GAME_TOPIC + event.getGameId(), event);
    }
}

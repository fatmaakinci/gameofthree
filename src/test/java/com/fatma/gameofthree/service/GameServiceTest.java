package com.fatma.gameofthree.service;

import com.fatma.gameofthree.repository.IGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by fatmaakinci on 26.01.2021.
 */
@SpringBootTest
public class GameServiceTest
{
    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameRepository gameRepository;
}

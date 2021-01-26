package com.fatma.gameofthree.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StartGameRequest
{
    private String gameId;

    private String playerName;
}

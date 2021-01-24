package com.fatma.gameofthree.controller.dto;

import com.fatma.gameofthree.model.AdditionType;
import lombok.Getter;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
public class MakeMoveRequest
{
    private String gameId;

    private String playerName;

    private AdditionType additionType;

    private Integer addition;
}

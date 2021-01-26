package com.fatma.gameofthree.controller.dto;

import com.fatma.gameofthree.model.AdditionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MakeMoveRequest
{
    private String gameId;

    private String playerName;

    private AdditionType additionType;

    private Integer addition;
}

package com.fatma.gameofthree.repository;

import com.fatma.gameofthree.model.Game;
import com.fatma.gameofthree.model.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fatmaakinci on 24.01.2021.
 */
@Repository
public interface IGameRepository extends JpaRepository<Game, String>
{
    Game findFirstByState(GameState state);
}

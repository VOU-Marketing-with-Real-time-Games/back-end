package com.vou.backend.game.game_info;

import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.repository.GameInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GameInfoRepositoryTest {
    @Autowired
    private GameInfoRepository gameRepository;

    @Test
    public void testCreateGameInfo() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setName("Quizz Game");
        gameInfo.setImage("test_image.png");
        gameInfo.setType(GameType.QUIZZ);
        gameInfo.setManual("Test manual");
        gameInfo.setEnable(true);

        GameInfo savedGameInfo = gameRepository.save(gameInfo);

        assertThat(savedGameInfo).isNotNull();
        assertThat(savedGameInfo.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindGameInfoById() {
        Long id = 2L;
        Optional<GameInfo> optionalGameInfo = gameRepository.findById(id);

        assertThat(optionalGameInfo).isPresent();
        GameInfo gameInfo = optionalGameInfo.get();
        assertThat(gameInfo.getName()).isEqualTo("Quizz Game");
    }

    @Test
    public void testUpdateGameInfo() {
        Long id = 2L;
        Optional<GameInfo> optionalGameInfo = gameRepository.findById(id);
        GameInfo gameInfo = optionalGameInfo.get();
        gameInfo.setName("Updated Game Name");

        GameInfo updatedGameInfo = gameRepository.save(gameInfo);

        assertThat(updatedGameInfo.getName()).isEqualTo("Updated Game Name");
    }

    @Test
    public void getGameInfoByType() {
        GameType type = GameType.QUIZZ;
        GameInfo gameInfo = gameRepository.findByType(type);

        assertThat(gameInfo).isNotNull();
    }

    @Test
    public void testDeleteGameInfo() {
        Long id = 2L;
        gameRepository.deleteById(id);

        Optional<GameInfo> optionalGameInfo = gameRepository.findById(id);

        assertThat(optionalGameInfo).isNotPresent();
    }

}

package com.vou.backend.game.game_info;

import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.repository.GameCampaignRepository;
import com.vou.backend.game.game_info.repository.GameInfoRepository;
import com.vou.backend.game.game_info.repository.UserCampaignGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserCampaignGameRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserCampaignGameRepository userCampaignGameRepository;

    @Autowired
    private GameCampaignRepository gameCampaignRepository;


    @Test
    public void testSaveUserCampaignGame() {
        GameCampaign gameCampaign = gameCampaignRepository.getReferenceById(1L);
        UserCampaignGame userCampaignGame = UserCampaignGame.builder()
                .userId(1L)
                .campaignGame(gameCampaign)
                .isCompleted(false)
                .build();
        UserCampaignGame savedUserCampaignGame = userCampaignGameRepository.save(userCampaignGame);

        assertThat(savedUserCampaignGame).isNotNull();
        assertThat(savedUserCampaignGame.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetUserCampaignGameById() {
        Optional<UserCampaignGame> found = userCampaignGameRepository.findById(5L);

        assertThat(found).isPresent();
        assertThat(found.get().getUserId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllUserCampaignGames() {
        List<UserCampaignGame> allUserCampaignGames = userCampaignGameRepository.findAll();

        assertThat(allUserCampaignGames).hasSize(1);
    }

    @Test
    public void testUpdateUserCampaignGame() {
        UserCampaignGame userCampaignGame = userCampaignGameRepository.findById(5L).get();
        userCampaignGame.setIsCompleted(true);
        UserCampaignGame updatedUserCampaignGame = userCampaignGameRepository.save(userCampaignGame);

        assertThat(updatedUserCampaignGame.getIsCompleted()).isTrue();
    }

    @Test
    public void testFindByUserId() {

        List<UserCampaignGame> found = userCampaignGameRepository.findByUserId(1L);

        assertThat(found).hasSize(1);
        assertThat(found).extracting(UserCampaignGame::getUserId).containsOnly(1L);
    }


    @Test
    public void testFindByUserIdAndCampaignGameId() {
        UserCampaignGame found = userCampaignGameRepository.findByUserIdAndCampaignGameId(1L, 1L);

        assertThat(found).isNotNull();
        assertThat(found.getUserId()).isEqualTo(1L);
        assertThat(found.getCampaignGame().getId()).isEqualTo(1L);
    }

    @Test
    public void testNotFindByUserIdAndCampaignGameId() {
        UserCampaignGame found = userCampaignGameRepository.findByUserIdAndCampaignGameId(1L, 2L);
        assertThat(found).isNull();
    }
}

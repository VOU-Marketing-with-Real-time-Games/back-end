package com.vou.backend.game.game_info;

import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.repository.GameCampaignRepository;
import com.vou.backend.game.game_info.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GameCampaignRepositoryTest {

    @Autowired
    private GameCampaignRepository gameCampaignRepository;
    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Test
    public void testCreateGameCampaign() {
        GameInfo gameInfo = gameInfoRepository.findByType(GameType.QUIZZ);
        GameCampaign gameCampaign = new GameCampaign();
        gameCampaign.setCampaignId(1L);
        gameCampaign.setGameInfo(gameInfo);
        gameCampaign.setGameId(1L);

        GameCampaign savedGameCampaign = gameCampaignRepository.save(gameCampaign);

        assertThat(savedGameCampaign).isNotNull();
        assertThat(savedGameCampaign.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindGameCampaignById() {
        Long id = 1L;
        Optional<GameCampaign> optionalGameCampaign = gameCampaignRepository.findById(id);

        assertThat(optionalGameCampaign).isPresent();
        GameCampaign gameCampaign = optionalGameCampaign.get();
        assertThat(gameCampaign.getCampaignId()).isEqualTo(1L);
    }

    @Test
    public void testFindByCampaignId() {
        Long campaignId = 1L;
        List<GameCampaign> gameCampaigns = gameCampaignRepository.findByCampaignId(campaignId);

        assertThat(gameCampaigns).isNotEmpty();
    }

    @Test
    public void testUpdateGameCampaign() {
        Long id = 1L;
        Optional<GameCampaign> optionalGameCampaign = gameCampaignRepository.findById(id);
        GameCampaign gameCampaign = optionalGameCampaign.get();
        gameCampaign.setCampaignId(2L);

        GameCampaign updatedGameCampaign = gameCampaignRepository.save(gameCampaign);

        assertThat(updatedGameCampaign.getCampaignId()).isEqualTo(2L);
    }

    @Test
    public void testDeleteGameCampaign() {
        Long id = 1L;
        gameCampaignRepository.deleteById(id);

        Optional<GameCampaign> optionalGameCampaign = gameCampaignRepository.findById(id);

        assertThat(optionalGameCampaign).isNotPresent();
    }
}

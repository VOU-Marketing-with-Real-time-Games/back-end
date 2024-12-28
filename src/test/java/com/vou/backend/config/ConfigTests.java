package com.vou.backend.config;

import com.vou.backend.game.game_info.dto.*;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.puzzle.dto.ItemRequestDto;
import com.vou.backend.game.puzzle.dto.ItemResponseDto;
import com.vou.backend.game.puzzle.dto.PuzzleRequestDto;
import com.vou.backend.game.puzzle.dto.PuzzleResponseDto;
import com.vou.backend.game.puzzle.model.Item;
import com.vou.backend.game.puzzle.model.Puzzle;
import com.vou.backend.game.quizz.dto.QuestionRequestDto;
import com.vou.backend.game.quizz.dto.QuestionResponseDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.Quizz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTests {
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        AppConfig appConfig = new AppConfig();
        modelMapper = appConfig.modelMapper();
    }

    @Test
    public void testGameInfoDtoToGameInfoMapping() {
        GameInfoDto gameInfoDto = new GameInfoDto();
        gameInfoDto.setId(1L);
        gameInfoDto.setName("Test Game");
        gameInfoDto.setImage("test_image.png");
        gameInfoDto.setType("QUIZZ");
        gameInfoDto.setManual("Test Manual");
        gameInfoDto.setEnable(true);

        GameInfo gameInfo = modelMapper.map(gameInfoDto, GameInfo.class);

        assertThat(gameInfo.getId()).isEqualTo(1L);
        assertThat(gameInfo.getName()).isEqualTo("Test Game");
        assertThat(gameInfo.getImage()).isEqualTo("test_image.png");
        assertThat(gameInfo.getType()).isEqualTo(GameType.QUIZZ);
        assertThat(gameInfo.getManual()).isEqualTo("Test Manual");
        assertThat(gameInfo.getEnable()).isEqualTo(true);
    }

    @Test
    public void testNullTypeMapping1() {
        GameInfoDto gameInfoDto = new GameInfoDto();
        gameInfoDto.setType(null);

        GameInfo gameInfo = modelMapper.map(gameInfoDto, GameInfo.class);

        assertThat(gameInfo.getType()).isNull();
    }

    @Test
    public void testGameInfoToGameInfoDtoMapping() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setId(1L);
        gameInfo.setName("Test Game");
        gameInfo.setImage("test_image.png");
        gameInfo.setType(GameType.QUIZZ);
        gameInfo.setManual("Test Manual");
        gameInfo.setEnable(true);

        GameInfoDto gameInfoDto = modelMapper.map(gameInfo, GameInfoDto.class);

        assertThat(gameInfoDto.getId()).isEqualTo(1L);
        assertThat(gameInfoDto.getName()).isEqualTo("Test Game");
        assertThat(gameInfoDto.getImage()).isEqualTo("test_image.png");
        assertThat(gameInfoDto.getType()).isEqualTo("QUIZZ");
        assertThat(gameInfoDto.getManual()).isEqualTo("Test Manual");
        assertThat(gameInfoDto.getEnable()).isEqualTo(true);
    }

    @Test
    public void testNullTypeMapping2() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setType(null);

        GameInfoDto gameInfoDto = modelMapper.map(gameInfo, GameInfoDto.class);

        assertThat(gameInfoDto.getType()).isNull();
    }

    @Test
    public void testConfigGameCampaignToResponseDtoConverter() {
        // Create GameInfo
        GameInfo gameInfo = GameInfo.builder()
                .id(1L)
                .name("Test Game")
                .image("test_image.png")
                .type(GameType.QUIZZ)
                .manual("Test Manual")
                .enable(true)
                .build();

        // Create GameCampaign
        GameCampaign gameCampaign = GameCampaign.builder()
                .id(1L)
                .campaignId(1L)
                .gameInfo(gameInfo)
                .gameId(1L)
                .build();

        // Convert GameCampaign to GameCampaignResponseDto
        GameCampaignResponseDto gameCampaignResponseDto = modelMapper.map(gameCampaign, GameCampaignResponseDto.class);

        // Verify the conversion
        assertThat(gameCampaignResponseDto).isNotNull();
        assertThat(gameCampaignResponseDto.getId()).isEqualTo(gameCampaign.getId());
        assertThat(gameCampaignResponseDto.getCampaignId()).isEqualTo(gameCampaign.getCampaignId());
        assertThat(gameCampaignResponseDto.getGameId()).isEqualTo(gameCampaign.getGameId());

        GameInfoDto gameInfoDto = gameCampaignResponseDto.getGameInfodto();
        assertThat(gameInfoDto).isNotNull();
        assertThat(gameInfoDto.getId()).isEqualTo(gameInfo.getId());
        assertThat(gameInfoDto.getName()).isEqualTo(gameInfo.getName());
        assertThat(gameInfoDto.getImage()).isEqualTo(gameInfo.getImage());
        assertThat(gameInfoDto.getType()).isEqualTo(gameInfo.getType().name());
        assertThat(gameInfoDto.getManual()).isEqualTo(gameInfo.getManual());
        assertThat(gameInfoDto.getEnable()).isEqualTo(gameInfo.getEnable());
    }


    @Test
    public void testConfigGameCampaignRequestDtoConverter() {
        // Create GameCampaignRequestDto
        GameCampaignRequestDto requestDto = GameCampaignRequestDto.builder()
                .campaignId(1L)
                .gameInfoId(2L)
                .gameId(3L)
                .build();

        // Convert GameCampaignRequestDto to GameCampaign
        GameCampaign gameCampaign = modelMapper.map(requestDto, GameCampaign.class);

        // Verify the conversion
        assertThat(gameCampaign).isNotNull();
        assertThat(gameCampaign.getCampaignId()).isEqualTo(requestDto.getCampaignId());
        assertThat(gameCampaign.getGameId()).isEqualTo(requestDto.getGameId());
        // Note: gameInfoId is not directly mapped to GameCampaign, so we don't check it here
    }

    @Test
    public void testUserCampaignGameRequestDtoToUserCampaignGameMapping() {
        UserCampaignGameRequestDto requestDto = UserCampaignGameRequestDto.builder()
                .userId(1L)
                .campaignGameId(2L)
                .build();

        UserCampaignGame userCampaignGame = modelMapper.map(requestDto, UserCampaignGame.class);

        assertThat(userCampaignGame).isNotNull();
        assertThat(userCampaignGame.getUserId()).isEqualTo(requestDto.getUserId());
        assertThat(userCampaignGame.getCampaignGame().getId()).isEqualTo(requestDto.getCampaignGameId());
    }

    @Test
    public void testUserCampaignGameToUserCampaignGameResponseDtoMapping() {
        GameCampaign gameCampaign = GameCampaign.builder()
                .id(2L)
                .campaignId(1L)
                .gameId(1L)
                .build();

        UserCampaignGame userCampaignGame = UserCampaignGame.builder()
                .id(1L)
                .userId(1L)
                .campaignGame(gameCampaign)
                .isCompleted(false)
                .build();

        UserCampaignGameResponseDto responseDto = modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(userCampaignGame.getId());
        assertThat(responseDto.getUserId()).isEqualTo(userCampaignGame.getUserId());
        assertThat(responseDto.getCampaignGameId()).isEqualTo(userCampaignGame.getCampaignGame().getId());
        assertThat(responseDto.getIsCompleted()).isEqualTo(userCampaignGame.getIsCompleted());
    }

    @Test
    public void testItemRequestDtoToItemMapping() {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setPosition(1);
        itemRequestDto.setDescription("Test Description");
        itemRequestDto.setTotal(10);

        Item item = modelMapper.map(itemRequestDto, Item.class);

        assertThat(item).isNotNull();
        assertThat(item.getPosition()).isEqualTo(itemRequestDto.getPosition());
        assertThat(item.getDescription()).isEqualTo(itemRequestDto.getDescription());
        assertThat(item.getTotal()).isEqualTo(itemRequestDto.getTotal());
        assertThat(item.getId()).isNull();
        assertThat(item.getRemainingNum()).isNull();
        assertThat(item.getPuzzle()).isNull();
        assertThat(item.getUserItems()).isEmpty();
    }

    @Test
    public void testItemToItemResponseDtoMapping() {
        Item item = new Item();
        item.setId(1L);
        item.setPosition(1);
        item.setDescription("Test Description");
        item.setTotal(10);
        item.setRemainingNum(5);

        ItemResponseDto itemResponseDto = modelMapper.map(item, ItemResponseDto.class);

        assertThat(itemResponseDto).isNotNull();
        assertThat(itemResponseDto.getId()).isEqualTo(item.getId());
        assertThat(itemResponseDto.getPosition()).isEqualTo(item.getPosition());
        assertThat(itemResponseDto.getDescription()).isEqualTo(item.getDescription());
        assertThat(itemResponseDto.getTotal()).isEqualTo(item.getTotal());
        assertThat(itemResponseDto.getRemainingNum()).isEqualTo(item.getRemainingNum());
    }

    @Test
    public void testPuzzleRequestDtoToPuzzleMapping() {
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .position(1)
                .description("Item Description")
                .total(10)
                .build();

        PuzzleRequestDto puzzleRequestDto = PuzzleRequestDto.builder()
                .name("Puzzle Name")
                .description("Puzzle Description")
                .itemNum(5)
                .campaignGameId(1L)
                .items(List.of(itemRequestDto))
                .build();

        Puzzle puzzle = modelMapper.map(puzzleRequestDto, Puzzle.class);

        assertThat(puzzle).isNotNull();
        assertThat(puzzle.getName()).isEqualTo(puzzleRequestDto.getName());
        assertThat(puzzle.getDescription()).isEqualTo(puzzleRequestDto.getDescription());
        assertThat(puzzle.getItemNum()).isEqualTo(puzzleRequestDto.getItemNum());
        assertThat(puzzle.getCampaignGameId()).isEqualTo(puzzleRequestDto.getCampaignGameId());
        assertThat(puzzle.getItems()).hasSize(1);

        Item item = puzzle.getItems().get(0);
        assertThat(item.getPosition()).isEqualTo(itemRequestDto.getPosition());
        assertThat(item.getDescription()).isEqualTo(itemRequestDto.getDescription());
        assertThat(item.getTotal()).isEqualTo(itemRequestDto.getTotal());
    }

    @Test
    public void testPuzzleToPuzzleResponseDtoMapping() {
        Item item = Item.builder()
                .id(1L)
                .position(1)
                .description("Item Description")
                .total(10)
                .remainingNum(5)
                .build();

        Puzzle puzzle = Puzzle.builder()
                .id(1L)
                .name("Puzzle Name")
                .description("Puzzle Description")
                .itemNum(5)
                .campaignGameId(1L)
                .items(List.of(item))
                .build();

        PuzzleResponseDto puzzleResponseDto = modelMapper.map(puzzle, PuzzleResponseDto.class);

        assertThat(puzzleResponseDto).isNotNull();
        assertThat(puzzleResponseDto.getId()).isEqualTo(puzzle.getId());
        assertThat(puzzleResponseDto.getName()).isEqualTo(puzzle.getName());
        assertThat(puzzleResponseDto.getDescription()).isEqualTo(puzzle.getDescription());
        assertThat(puzzleResponseDto.getItemNum()).isEqualTo(puzzle.getItemNum());
        assertThat(puzzleResponseDto.getCampaignGameId()).isEqualTo(puzzle.getCampaignGameId());
        assertThat(puzzleResponseDto.getItems()).hasSize(1);

        ItemResponseDto itemResponseDto = puzzleResponseDto.getItems().get(0);
        assertThat(itemResponseDto.getId()).isEqualTo(item.getId());
        assertThat(itemResponseDto.getPosition()).isEqualTo(item.getPosition());
        assertThat(itemResponseDto.getDescription()).isEqualTo(item.getDescription());
        assertThat(itemResponseDto.getTotal()).isEqualTo(item.getTotal());
        assertThat(itemResponseDto.getRemainingNum()).isEqualTo(item.getRemainingNum());
    }

    @Test
    public void testQuestionRequestDtoToQuestionMapping() {
        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .questionName("Sample Question")
                .option1("Option 1")
                .option2("Option 2")
                .option3("Option 3")
                .option4("Option 4")
                .answer("Option 1")
                .explaination("Sample Explanation")
                .quizzId(1L)
                .build();

        Question question = modelMapper.map(questionRequestDto, Question.class);

        assertThat(question).isNotNull();
        assertThat(question.getQuestionName()).isEqualTo(questionRequestDto.getQuestionName());
        assertThat(question.getOption1()).isEqualTo(questionRequestDto.getOption1());
        assertThat(question.getOption2()).isEqualTo(questionRequestDto.getOption2());
        assertThat(question.getOption3()).isEqualTo(questionRequestDto.getOption3());
        assertThat(question.getOption4()).isEqualTo(questionRequestDto.getOption4());
        assertThat(question.getAnswer()).isEqualTo(questionRequestDto.getAnswer());
        assertThat(question.getExplaination()).isEqualTo(questionRequestDto.getExplaination());
        assertThat(question.getQuizz().getId()).isEqualTo(questionRequestDto.getQuizzId());
    }

    @Test
    public void testQuestionToQuestionResponseDtoMapping() {
        Question question = Question.builder()
                .id(1L)
                .questionName("Sample Question")
                .image("sample_image.png")
                .option1("Option 1")
                .option2("Option 2")
                .option3("Option 3")
                .option4("Option 4")
                .answer("Option 1")
                .explaination("Sample Explanation")
                .quizz(Quizz.builder().id(1L).build())
                .build();

        QuestionResponseDto questionResponseDto = modelMapper.map(question, QuestionResponseDto.class);

        assertThat(questionResponseDto).isNotNull();
        assertThat(questionResponseDto.getId()).isEqualTo(question.getId());
        assertThat(questionResponseDto.getQuestionName()).isEqualTo(question.getQuestionName());
        assertThat(questionResponseDto.getImage()).isEqualTo(question.getImage());
        assertThat(questionResponseDto.getOption1()).isEqualTo(question.getOption1());
        assertThat(questionResponseDto.getOption2()).isEqualTo(question.getOption2());
        assertThat(questionResponseDto.getOption3()).isEqualTo(question.getOption3());
        assertThat(questionResponseDto.getOption4()).isEqualTo(question.getOption4());
        assertThat(questionResponseDto.getAnswer()).isEqualTo(question.getAnswer());
        assertThat(questionResponseDto.getExplaination()).isEqualTo(question.getExplaination());
        assertThat(questionResponseDto.getQuizzId()).isEqualTo(question.getQuizz().getId());
    }
}

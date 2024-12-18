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
import com.vou.backend.game.quizz.dto.QuizzRequestDto;
import com.vou.backend.game.quizz.dto.QuizzResponseDto;
import com.vou.backend.game.quizz.model.Question;
import com.vou.backend.game.quizz.model.Quizz;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class AppConfig {

    /**
     * Configures and returns a ModelMapper bean.
     *
     * @return the configured ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configGameInfoConverter(modelMapper);
        configGameCampaignConverters(modelMapper);
        configUserCampaignGameConverters(modelMapper);
        configItemConverters(modelMapper);
        configPuzzleConverters(modelMapper);
        configQuestionConverters(modelMapper);
        configQuizzConverters(modelMapper);
        return modelMapper;
    }


    /**
     * Configures the converter for GameInfo.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configGameInfoConverter(ModelMapper modelMapper) {
        Converter<String, GameType> stringToGameTypeConverter = context ->
                context.getSource() != null ? GameType.valueOf(context.getSource()) : null;
        modelMapper.addMappings(new PropertyMap<GameInfoDto, GameInfo>() {
            @Override
            protected void configure() {
                using(stringToGameTypeConverter).map(source.getType()).setType(null);
            }
        });
    }

    /**
     * Configures the converters for GameCampaign.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configGameCampaignConverters(ModelMapper modelMapper) {
        Converter<GameInfo, GameInfoDto> gameInfoToGameInfoDtoConverter = context ->
                modelMapper.map(context.getSource(), GameInfoDto.class);
        modelMapper.addMappings(new PropertyMap<GameCampaign, GameCampaignResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setCampaignId(source.getCampaignId());
                map().setGameId(source.getGameId());
                using(gameInfoToGameInfoDtoConverter).map(source.getGameInfo()).setGameInfodto(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<GameCampaignRequestDto, GameCampaign>() {
            @Override
            protected void configure() {
                map().setCampaignId(source.getCampaignId());
                map().setGameId(source.getGameId());
                skip(destination.getId());
            }
        });
    }

    /**
     * Configures the converters for UserCampaignGame.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configUserCampaignGameConverters(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<UserCampaignGameRequestDto, UserCampaignGame>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUserId());
                map().getCampaignGame().setId(source.getCampaignGameId());
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<UserCampaignGame, UserCampaignGameResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setUserId(source.getUserId());
                map().setCampaignGameId(source.getCampaignGame().getId());
                map().setIsCompleted(source.getIsCompleted());
            }
        });
    }


    /**
     * Configures the converters for Item.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configItemConverters(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<ItemRequestDto, Item>() {
            @Override
            protected void configure() {
                map().setPosition(source.getPosition());
                map().setDescription(source.getDescription());
                map().setTotal(source.getTotal());
                skip(destination.getId());
                skip(destination.getRemainingNum());
                skip(destination.getPuzzle());
                skip(destination.getUserItems());
            }
        });

        modelMapper.addMappings(new PropertyMap<Item, ItemResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setPosition(source.getPosition());
                map().setDescription(source.getDescription());
                map().setTotal(source.getTotal());
                map().setRemainingNum(source.getRemainingNum());
            }
        });
    }

    /**
     * Configures the converters for Puzzle.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configPuzzleConverters(ModelMapper modelMapper) {
        Converter<List<ItemRequestDto>, List<Item>> itemRequestDtoToItemConverter = context ->
                context.getSource().stream()
                        .map(itemRequestDto -> modelMapper.map(itemRequestDto, Item.class))
                        .collect(Collectors.toList());

        modelMapper.addMappings(new PropertyMap<PuzzleRequestDto, Puzzle>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setItemNum(source.getItemNum());
                map().setCampaignGameId(source.getCampaignGameId());
                using(itemRequestDtoToItemConverter).map(source.getItems()).setItems(null);
                skip(destination.getId());
                skip(destination.getImage());
            }
        });

        Converter<List<Item>, List<ItemResponseDto>> itemToItemResponseDtoConverter = context ->
                context.getSource().stream()
                        .map(item -> modelMapper.map(item, ItemResponseDto.class))
                        .collect(Collectors.toList());
        modelMapper.addMappings(new PropertyMap<Puzzle, PuzzleResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setItemNum(source.getItemNum());
                map().setCampaignGameId(source.getCampaignGameId());
                using(itemToItemResponseDtoConverter).map(source.getItems()).setItems(null);
                map().setImage(source.getImage());
            }
        });
    }

    /**
     * Configures the converters for Question.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configQuestionConverters(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<QuestionRequestDto, Question>() {
            @Override
            protected void configure() {
                map().setQuestionName(source.getQuestionName());
                map().setOption1(source.getOption1());
                map().setOption2(source.getOption2());
                map().setOption3(source.getOption3());
                map().setOption4(source.getOption4());
                map().setAnswer(source.getAnswer());
                map().setExplaination(source.getExplaination());
                map().getQuizz().setId(source.getQuizzId());
                skip(destination.getId());
                skip(destination.getImage());
                skip(destination.getUserAnswers());
            }
        });

        modelMapper.addMappings(new PropertyMap<Question, QuestionResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setQuestionName(source.getQuestionName());
                map().setImage(source.getImage());
                map().setOption1(source.getOption1());
                map().setOption2(source.getOption2());
                map().setOption3(source.getOption3());
                map().setOption4(source.getOption4());
                map().setAnswer(source.getAnswer());
                map().setExplaination(source.getExplaination());
                map().setQuizzId(source.getQuizz().getId());
            }
        });
    }

    /**
     * Configures the converters for Quizz.
     *
     * @param modelMapper the ModelMapper to configure
     */
    private void configQuizzConverters(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<QuizzRequestDto, Quizz>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setSecondPerQuestion(source.getSecondPerQuestion());
                map().setCampaignGameId(source.getCampaignGameId());
                skip(destination.getId());
                skip(destination.getCreatedAt());
                skip(destination.getQuestions());
            }
        });

        modelMapper.addMappings(new PropertyMap<Quizz, QuizzResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setCreatedAt(source.getCreatedAt());
                map().setSecondPerQuestion(source.getSecondPerQuestion());
                map().setCampaignGameId(source.getCampaignGameId());
            }
        });
    }

}

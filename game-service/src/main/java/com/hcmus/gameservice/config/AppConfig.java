package com.hcmus.gameservice.config;

import com.hcmus.gameservice.game_info.dto.*;
import com.hcmus.gameservice.game_info.model.GameCampaign;
import com.hcmus.gameservice.game_info.model.GameInfo;
import com.hcmus.gameservice.game_info.model.UserCampaignGame;
import com.hcmus.gameservice.puzzle.dto.*;
import com.hcmus.gameservice.puzzle.model.Item;
import com.hcmus.gameservice.puzzle.model.Puzzle;
import com.hcmus.gameservice.puzzle.model.UserItem;
import com.hcmus.gameservice.quizz.dto.*;
import com.hcmus.gameservice.quizz.model.Question;
import com.hcmus.gameservice.quizz.model.Quizz;
import com.hcmus.gameservice.quizz.model.UserAnswer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    /**
     * Configures and returns a ModelMapper bean.
     *
     * @return the configured ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configGameCampaignConverters(modelMapper);
        configUserCampaignGameConverters(modelMapper);
        configPuzzleConverters(modelMapper);
        configQuestionConverters(modelMapper);
        configUserItemConverters(modelMapper);
        configQuizzConverters(modelMapper);
        configUserAnswerConverters(modelMapper);
        return modelMapper;
    }
    private void configQuizzConverters(ModelMapper modelMapper) {
        // Map QuizzRequestDto to Quizz
        modelMapper.addMappings(new PropertyMap<QuizzRequestDto, Quizz>() {
            @Override
            protected void configure() {
                skip(destination.getId()); // Skip mapping ID
                skip(destination.getStartTime());
            }
        });

        // Map Quizz to QuizzResponseDto
        modelMapper.addMappings(new PropertyMap<Quizz, QuizzResponseDto>() {
            @Override
            protected void configure() {
                map().setCreatedAt(sourceToTargetDateConverter(source.getCreatedAt()));
                map().setStartTime(sourceToTargetDateConverter(source.getStartTime()));
            }
        });
    }

    // Helper for formatting Date to String
    private static String sourceToTargetDateConverter(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    private void configGameCampaignConverters(ModelMapper modelMapper) {
        Converter<GameInfo, GameInfoDto> gameInfoToGameInfoDtoConverter = context -> modelMapper.map(context.getSource(), GameInfoDto.class);
        modelMapper.addMappings(new PropertyMap<GameCampaign, GameCampaignResponseDto>() {
            @Override
            protected void configure() {
                using(gameInfoToGameInfoDtoConverter).map(source.getGameInfo()).setGameInfodto(null); // Example custom mapping
            }
        });
        modelMapper.addMappings(new PropertyMap<GameCampaignRequestDto, GameCampaign>() {
            @Override
            protected void configure() {
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
                skip(destination.getId());
            }
        });
        modelMapper.addMappings(new PropertyMap<UserCampaignGame, UserCampaignGameResponseDto>() {
            @Override
            protected void configure() {
                map().setCampaignGameId(source.getCampaignGame().getId());
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
                using(itemRequestDtoToItemConverter).map(source.getItems()).setItems(null);
            }
        });
        Converter<List<Item>, List<ItemResponseDto>> itemToItemResponseDtoConverter = context ->
                context.getSource().stream()
                        .map(item -> modelMapper.map(item, ItemResponseDto.class))
                        .collect(Collectors.toList());
        modelMapper.addMappings(new PropertyMap<Puzzle, PuzzleResponseDto>() {
            @Override
            protected void configure() {
                using(itemToItemResponseDtoConverter).map(source.getItems()).setItems(null);
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
                map().getQuizz().setId(source.getQuizzId());
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Question, QuestionResponseDto>() {
            @Override
            protected void configure() {
                map().setQuizzId(source.getQuizz().getId());
            }
        });
    }
    private void configUserItemConverters(ModelMapper modelMapper) {
        Converter<Item, ItemResponseDto> itemToItemResponseDtoConverter = context ->
                modelMapper.map(context.getSource(), ItemResponseDto.class);

        modelMapper.addMappings(new PropertyMap<UserItem, UserItemDto>() {
            @Override
            protected void configure() {
                using(itemToItemResponseDtoConverter).map(source.getItem()).setItem(null);
            }
        });
    }

    private void configUserAnswerConverters(ModelMapper modelMapper) {
        // DTO to Model mapping
        modelMapper.addMappings(new PropertyMap<UserAnswerRequestDto, UserAnswer>() {
            @Override
            protected void configure() {
                map().getQuestion().setId(source.getQuestionId());
                skip(destination.getId());
                skip(destination.getQuestion());
                skip(destination.getIsCorrect());
                skip(destination.getScore());
            }
        });
    }
}

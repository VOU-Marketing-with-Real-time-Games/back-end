package com.vou.backend.config;

import com.vou.backend.campaign.dto.CampaignDto;
import com.vou.backend.campaign.dto.CampaignResponseDto;
import com.vou.backend.campaign.model.Campaign;
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
        configGameCampaignConverters(modelMapper);
        configUserCampaignGameConverters(modelMapper);
        configPuzzleConverters(modelMapper);
        configQuestionConverters(modelMapper);
        configCampaignConverters(modelMapper);
        return modelMapper;
    }
    private void configCampaignConverters(ModelMapper modelMapper) {
        // DTO to Model mapping
        modelMapper.typeMap(CampaignDto.class, Campaign.class).addMappings(mapper -> {
            mapper.skip(Campaign::setId);
            mapper.skip(Campaign::setCreatedAt);
            mapper.skip(Campaign::setFavouriteCampaigns);
        }).setPostConverter(context -> {
            Campaign campaign = context.getDestination();
            if (campaign.getStatus() == null) {
                campaign.setStatus("Pending");
            }
            return campaign;
        });

        // Model to Response DTO mapping
        modelMapper.typeMap(Campaign.class, CampaignResponseDto.class).addMappings(mapper -> {
            mapper.map(Campaign::getName, CampaignResponseDto::setName);
            mapper.map(Campaign::getImage, CampaignResponseDto::setImage);
            mapper.map(Campaign::getFiledId, CampaignResponseDto::setFiledId);
            mapper.map(Campaign::getStartDate, CampaignResponseDto::setStartDate);
            mapper.map(Campaign::getEndDate, CampaignResponseDto::setEndDate);
            mapper.map(Campaign::getCreatedAt, CampaignResponseDto::setCreatedAt);
            mapper.map(Campaign::getStatus, CampaignResponseDto::setStatus);
        });

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
            }
        });
        modelMapper.addMappings(new PropertyMap<Question, QuestionResponseDto>() {
            @Override
            protected void configure() {
                map().setQuizzId(source.getQuizz().getId());
            }
        });
    }
}

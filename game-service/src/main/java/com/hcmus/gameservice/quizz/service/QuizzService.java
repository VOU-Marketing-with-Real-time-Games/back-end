package com.hcmus.gameservice.quizz.service;

import com.hcmus.gameservice.client.QuizzRealtimeClient;
import com.hcmus.gameservice.game_info.exception.GameCampaignNotFoundException;
import com.hcmus.gameservice.game_info.exception.Game_CampaignGameConflict;
import com.hcmus.gameservice.game_info.exception.QuizzNotFoundException;
import com.hcmus.gameservice.game_info.exception.SchedulingErrorException;
import com.hcmus.gameservice.game_info.model.GameCampaign;
import com.hcmus.gameservice.game_info.model.GameType;
import com.hcmus.gameservice.game_info.service.GameCampaignService;
import com.hcmus.gameservice.quizz.dto.QuestionResponseDto;
import com.hcmus.gameservice.quizz.dto.QuizzRequestDto;
import com.hcmus.gameservice.quizz.dto.QuizzResponseDto;
import com.hcmus.gameservice.quizz.dto.ScheduleRequestDto;
import com.hcmus.gameservice.quizz.model.Quizz;
import com.hcmus.gameservice.quizz.repository.QuizzRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizzService {

    private final QuizzRepository quizzRepository;
    private final GameCampaignService gameCampaignService;
    private final ModelMapper modelMapper;
    private  final QuizzRealtimeClient quizzRealtimeClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizzService.class);

    public List<QuizzResponseDto> getAllQuizzes() {
        return quizzRepository.findAll().stream()
                .map(quizz -> modelMapper.map(quizz, QuizzResponseDto.class))
                .collect(Collectors.toList());
    }

    public QuizzResponseDto getQuizzById(Long id) throws QuizzNotFoundException {
        Quizz quizz =  quizzRepository.findById(id).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + id));
        return modelMapper.map(quizz, QuizzResponseDto.class);
    }

    public Quizz findQuizzById(Long id) throws QuizzNotFoundException {
        return quizzRepository.findById(id).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + id));
    }


    public QuizzResponseDto createQuizz(QuizzRequestDto quizzRequestDto) throws Game_CampaignGameConflict, GameCampaignNotFoundException, SchedulingErrorException {
        Quizz quizz = modelMapper.map(quizzRequestDto, Quizz.class);

        GameCampaign gameCampaign = gameCampaignService.getById(quizz.getCampaignGameId());
        if(gameCampaign.getGameInfo().getType() != GameType.QUIZZ)
        {
            throw new Game_CampaignGameConflict("Game Campaign does not match with the game type");
        }

        quizz.setCreatedAt(new Date());
        quizz.setStartTime(convertDate(quizzRequestDto.getStartTime()));

        Quizz savedQuizz = quizzRepository.save(quizz);

        gameCampaign.setGameId(savedQuizz.getId());
        gameCampaignService.updateGameCampaign(gameCampaign);

        List<QuestionResponseDto> questionResponseDtos = quizz.getQuestions().stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
        boolean isSuccessSchedule =  quizzRealtimeClient.scheduleQuiz(new ScheduleRequestDto(modelMapper.map(savedQuizz,QuizzResponseDto.class), questionResponseDtos));
        if(!isSuccessSchedule)
        {
            LOGGER.error("Error scheduling quiz");
            throw new SchedulingErrorException("Error scheduling quiz");
        }
        return modelMapper.map(savedQuizz, QuizzResponseDto.class);
    }

    public QuizzResponseDto updateQuizz(Long id, QuizzRequestDto quizzRequestDto) throws QuizzNotFoundException, ParseException, SchedulingErrorException {
        Quizz quizzDetails = modelMapper.map(quizzRequestDto, Quizz.class);
        quizzDetails.setStartTime(convertDate(quizzRequestDto.getStartTime()));
        Quizz quizz = findQuizzById(id);
        quizz.copy(quizzDetails);
        Quizz updatedQuizz = quizzRepository.save(quizz);

        List<QuestionResponseDto> questionResponseDtos = quizz.getQuestions().stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .collect(Collectors.toList());
        boolean isSuccessSchedule =  quizzRealtimeClient.scheduleQuiz(new ScheduleRequestDto(modelMapper.map(updatedQuizz,QuizzResponseDto.class), questionResponseDtos));
        if(!isSuccessSchedule)
        {
            LOGGER.error("Error scheduling quiz");
            throw new SchedulingErrorException("Error scheduling quiz");
        }
        return modelMapper.map(updatedQuizz, QuizzResponseDto.class);
    }

    private Date convertDate(String date)
    {
        try {
            // Parse the input as UTC
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = isoFormat.parse(date);

            // Adjust the date by the hour offset
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.add(Calendar.HOUR_OF_DAY, -7);
            return calendar.getTime();
        }
        catch (ParseException e) {
            LOGGER.error("Error parsing date", e);
            return null;
        }
    }

    public void deleteQuizz(Long id) throws QuizzNotFoundException, GameCampaignNotFoundException {
        Quizz quizz = findQuizzById(id);
        GameCampaign gameCampaign = gameCampaignService.getById(quizz.getCampaignGameId());
        gameCampaign.setGameId(null);
        quizzRepository.deleteById(id);
    }
}

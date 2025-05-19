package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.dto.ChoiceDto;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity.Choice;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.repository.ChoiceRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizDto;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity.Quiz;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ChoiceRepository choiceRepository;

    @Transactional
    public void addQuiz(QuizRequest request) {
        Quiz quiz = Quiz.builder()
                .question(request.getQuestion())
                .build();

        List<Choice> choiceList = request.getChoices().stream()
                .map(c -> Choice.builder()
                        .content(c.getContent())
                        .quiz(quiz)
                        .build())
                .toList();

        choiceList.forEach(quiz::addChoice);
        quizRepository.save(quiz);
        choiceRepository.saveAll(choiceList);

        Long correctChoiceId = choiceList.get(request.getCorrectIndex()).getId();
        quiz.setCorrectChoiceId(correctChoiceId);
    }

    @Transactional(readOnly = true)
    public QuizResponse getQuiz() {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime start = LocalDate.now(zoneId).atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        List<Quiz> todayQuizzes = quizRepository.findByCreatedAtBetween(start, end);

        List<QuizDto> quizDtos = todayQuizzes.stream()
                .map(quiz -> {
                    List<ChoiceDto> choiceDtos = quiz.getChoices().stream()
                            .map(choice -> new ChoiceDto(choice.getId(), choice.getContent()))
                            .toList();

                    return new QuizDto(quiz.getId(), quiz.getQuestion(), quiz.getCorrectChoiceId(), choiceDtos);
                })
                .toList();

        return new QuizResponse(quizDtos);
    }

    @Transactional
    public void updateQuiz(Long quizId, QuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈 없음"));

        quiz.setQuestion(request.getQuestion());
        quiz.getChoices().clear();

        List<Choice> newChoices = request.getChoices().stream()
                .map(c -> Choice.builder()
                        .content(c.getContent())
                        .quiz(quiz)
                        .build())
                .toList();

        choiceRepository.saveAll(newChoices);

        if (request.getCorrectIndex() < 0 || request.getCorrectIndex() >= newChoices.size()) {
            throw new IllegalArgumentException("정답 인덱스 오류");
        }

        Long correctChoiceId = newChoices.get(request.getCorrectIndex()).getId();
        quiz.setCorrectChoiceId(correctChoiceId);

        quizRepository.save(quiz);
    }
}

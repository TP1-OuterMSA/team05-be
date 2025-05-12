package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity.Choice;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity.Quiz;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.respository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

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

        Long correctChoiceId = choiceList.get(request.getCorrectIndex()).getId();
        quiz.setCorrectChoiceId(correctChoiceId);
    }
}

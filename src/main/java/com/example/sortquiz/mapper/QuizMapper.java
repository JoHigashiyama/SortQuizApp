package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.viewmodel.QuizDetailViewModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT quiz_id,content,happen_year FROM quizzes ORDER BY RAND() LIMIT 40")
//    @Select("SELECT * FROM quizzes")
    List<Quiz> getQuizlist();

    @Select("SELECT quiz_id AS id, content, happen_year, description FROM quizzes WHERE quiz_id = #{quiz_id}")
    QuizDetailViewModel getDetailsByQuizId(long quizId);

    @Select("SELECT * FROM quizzes ORDER BY happen_year")
    List<Quiz> getAllQuizzesSortHappenYear();

    @Select("""
            SELECT * FROM quizzes 
            WHERE content LIKE CONCAT('%', #{keyword}, '%')
            AND happen_year BETWEEN #{yearMin} AND #{yearMax}
            ORDER BY happen_year
            """)
    List<Quiz> getQuizzesByKeywordAndYear(String keyword, long yearMin, long yearMax);
}

package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.Quiz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT quiz_id,content,happen_year FROM quizzes ORDER BY RAND() LIMIT 40")
//    @Select("SELECT * FROM quizzes")
    List<Quiz> getQuizlist();
}

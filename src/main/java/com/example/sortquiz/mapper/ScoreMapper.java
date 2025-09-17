package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoreMapper {
    @Select("""
            SELECT users.username, scores.score, scores.correct_count, scores.created_at, RANK() OVER(ORDER BY scores.score DESC) AS rank
            FROM scores
            INNER JOIN users ON scores.user_id = users.user_id
            WHERE scores.user_id = #{userId}
            ORDER BY scores.created_at DESC
            """)
    List<ScoreHistoryViewModel> selectScoresByUserId(long userId);

    @Select("""
            SELECT users.username, scores.score, scores.correct_count, scores.created_at, RANK() OVER(ORDER BY scores.score DESC) AS rank
            FROM scores
            INNER JOIN users ON scores.user_id = users.user_id
            WHERE scores.user_id = #{userId}
            ORDER BY scores.score DESC
            """)
    List<ScoreHistoryViewModel> selectScoresScoreByUserId(long userId);

    @Insert("INSERT INTO scores (user_id, score, correct_count) VALUES (#{userId}, #{score}, #{correctCount})")
    @Options(useGeneratedKeys = true, keyProperty = "scoreId")
    void createScore(Score score);

    @Select("SELECT MAX(score) FROM scores WHERE user_id = #{userId} LIMIT 1")
    long selectBestScoreByUserId(long userId);

    @Select("SELECT SUM(score) FROM scores WHERE user_id = #{userId}")
    long selectTotalScoreByUserId(long userId);

    @Select("SELECT COUNT(score_id) FROM scores WHERE user_id = #{userId} AND correct_count = 10")
    long selectPerfectCountByUserId(long userId);

    @Select("SELECT COALESCE(SUM(correct_count), 0) FROM scores WHERE user_id = #{userId}")
    long selectTotalCorrectCountByUserId(long userId);
}

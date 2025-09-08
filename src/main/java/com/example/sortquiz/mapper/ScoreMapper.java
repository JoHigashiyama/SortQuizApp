package com.example.sortquiz.mapper;

import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoreMapper {
    @Select("""
            SELECT users.username, scores.score, scores.created_at, RANK() OVER(ORDER BY scores.score DESC) AS rank
            FROM scores
            INNER JOIN users ON scores.user_id = users.user_id
            WHERE scores.user_id = #{userId}
            ORDER BY scores.created_at DESC
            """)
    List<ScoreHistoryViewModel> selectScoresByUserId(long userId);
}

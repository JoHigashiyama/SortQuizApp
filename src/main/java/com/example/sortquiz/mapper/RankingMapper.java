package com.example.sortquiz.mapper;

import com.example.sortquiz.viewModel.UserViewModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RankingMapper {

//    //    トップ画面
//    @Select("SELECT * FROM users WHERE user_id = #{userId}")
//    List<UserViewModel> getUserInformation();


//    @Select("SELECT * FROM users ORDER BY best_score DESC LIMIT 10")
//    ベストスコア
    @Select("SELECT username, best_score AS score, created_at, RANK() OVER(ORDER BY best_score DESC) AS rank FROM users ORDER BY rank LIMIT 10")
    List<UserViewModel> getTop10ByBestScore();


}

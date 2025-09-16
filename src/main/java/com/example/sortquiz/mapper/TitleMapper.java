package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import org.apache.ibatis.annotations.Mapper;
import com.example.sortquiz.viewmodel.AchievedTitleViewModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TitleMapper {
    @Select("Select * FROM titles")
    List<Title> getTitles();

    @Select("Select title_id From user_titles Where user_id = #{userId}")
    List<UserTitle> getUserTitles(long userId);

//    自分の取得している称号をすべて取得する
    @Select("""
            SELECT t.title_id, t.title,
            CASE WHEN u.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS isAchieved
            FROM titles t 
            LEFT OUTER JOIN user_titles u ON t.title_id = u.title_id AND u.user_id = #{useId}
            ORDER BY t.title_id
            """)
    List<AchievedTitleViewModel> selectAchievedTitlesByUserId(long userId);

    @Insert("INSERT INTO user_titles (user_id, title_id) VALUES (#{userId}, #{titleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createUserTitle(UserTitle userTitle);

    @Select("SELECT * FROM titles WHERE title_id = #{titleId}")
    Title selectTitleByTitleId(long titleId);
}

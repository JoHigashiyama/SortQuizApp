package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TitleMapper {
    @Select("Select * FROM titles")
    List<Title> getTitles();

    @Select("Select title_id From user_titles Where user_id = #{userId}")
    List<UserTitle> getUserTitles(long userId);

}

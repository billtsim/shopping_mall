package com.a88.mapper;

import com.a88.Pojo.gameLibrary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface gameLibraryMapper {

    List<gameLibrary> getGameLibraryByUserId(int userId);

    void insertGameLibrary(gameLibrary gl);

    void updateGameLibrary(gameLibrary gl);
}

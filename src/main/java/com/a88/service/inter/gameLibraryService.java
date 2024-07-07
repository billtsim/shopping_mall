package com.a88.service.inter;

import com.a88.Pojo.gameLibrary;

import java.util.List;

public interface gameLibraryService {

    List<gameLibrary> getGameLibraryByUserId(int userId);

    void addGameLibrary(gameLibrary GL);

    void updateGameLibrary(gameLibrary gameLibrary);
}

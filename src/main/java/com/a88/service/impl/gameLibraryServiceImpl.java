package com.a88.service.impl;

import com.a88.Pojo.gameLibrary;
import com.a88.mapper.gameLibraryMapper;
import com.a88.service.inter.gameLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class gameLibraryServiceImpl implements gameLibraryService {

    @Autowired
    private gameLibraryMapper GLM;

    @Override
    public List<gameLibrary> getGameLibraryByUserId(int userId) {
        return GLM.getGameLibraryByUserId(userId);
    }

    @Override
    public void addGameLibrary(gameLibrary GL) {
        GL.setCreateTime(LocalDateTime.now());
        GL.setUpdateTime(LocalDateTime.now());
        GLM.insertGameLibrary(GL);
    }

    @Override
    public void updateGameLibrary(gameLibrary gameLibrary) {
        gameLibrary.setUpdateTime(LocalDateTime.now());
        gameLibrary.setCreateTime(LocalDateTime.now());
        GLM.updateGameLibrary(gameLibrary);
    }


}

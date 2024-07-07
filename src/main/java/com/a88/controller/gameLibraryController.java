package com.a88.controller;

import com.a88.Pojo.gameLibrary;
import com.a88.Pojo.result;
import com.a88.service.inter.gameLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-library")
@Slf4j
public class gameLibraryController {

    @Autowired
    private gameLibraryService GLS;

    @GetMapping("/user/{userId}")
    public result getGameLibraryByUserId(@PathVariable int userId) {
        return result.success(GLS.getGameLibraryByUserId(userId)) ;
    }

    @PutMapping
    public result updateGameLibrary(@RequestBody gameLibrary gameLibrary) {
        GLS.updateGameLibrary(gameLibrary);
        return result.success();
    }
}

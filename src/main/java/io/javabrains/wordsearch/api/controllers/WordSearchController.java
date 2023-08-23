package io.javabrains.wordsearch.api.controllers;

import io.javabrains.wordsearch.api.services.WordgridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController("/")
public class WordSearchController {
    @Autowired
    WordgridService wordgridService;

    @GetMapping("/wordgrid")
    public String createWordGrid(@RequestParam("gridsize") int gridsize, @RequestParam String  wordList){
         List<String> words= Arrays.asList(wordList.split(","));
         char[][] grid=wordgridService.generateGrid(gridsize,words);
         String gridToString="";
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                gridToString+=(grid[i][j] + " ");
            }
            gridToString+="\r\n";
        }
        return gridToString;

    }



}

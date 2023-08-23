package io.javabrains.wordsearch.api.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
@Service

public class WordgridService {



        private enum Direction {
            HORIZONTAL,
            VERTICAL,
            DIAGONAL,
            HORIZONTAL_INVERSE,
            VERTICAL_INVERSE,
            DIAGONAL_INVERSE



        }

        private class Coordinate {
            int x;
            int y;

            Coordinate(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }


        public char[][] generateGrid(int gridsize, List<String> words) {
             List<Coordinate> coordinates = new ArrayList<>();

            char [][] contents = new char[gridsize][gridsize];
            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    coordinates.add(new Coordinate(i, j));
                    contents[i][j] = '_';
                }
            }
            Collections.shuffle(coordinates);
            for (String word : words) {
                for (Coordinate coordinate : coordinates) {
                    int x = coordinate.x;
                    int y = coordinate.y;
                    Direction selecteddirection=getDirectionForFit(contents,word,coordinate);
                    if (selecteddirection!=null) {
                        switch(selecteddirection) {
                            case HORIZONTAL:
                                for (char c : word.toCharArray()) {
                                    contents[x][y++] = c;
                                }
                                break;

                            case VERTICAL:
                                for (char c : word.toCharArray()) {
                                    contents[x++][y] = c;
                                }
                                break;
                            case DIAGONAL:
                                for (char c : word.toCharArray()) {
                                    contents[x++][y++] = c;
                                }
                                break;
                            case HORIZONTAL_INVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x][y--] = c;
                                }
                                break;

                            case VERTICAL_INVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x--][y] = c;
                                }
                                break;
                            case DIAGONAL_INVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x--][y--] = c;
                                }
                                break;
                        }
                        break;


                    }
                }


            }
            randomFillGrid(contents);
            return contents;
        }

        public void displaygrid(char[][] contents) {
            int gridsize=contents[0].length;
            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    System.out.print(contents[i][j] + " ");
                }
                System.out.println(" ");
            }

        }


        private void randomFillGrid(char[][] contents) {
            int gridsize=contents[0].length;
            String allCapLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    if (contents[i][j] == '_') {
                        int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                        contents[i][j] = allCapLetters.charAt(randomIndex);
                    }

                }
            }
        }


        public Direction getDirectionForFit(char[][] contents,String word, Coordinate coordinate) {
            List<Direction> directions = Arrays.asList(Direction.values());
            Collections.shuffle(directions);
            for (Direction direction : directions) {
                if(doesfit(contents,word,coordinate,direction)){
                    return direction;
                }

            }
            return null;

        }
        private boolean doesfit(char[][] contents,String word, Coordinate coordinate,Direction direction){
            int gridsize=contents[0].length;
            int wordLength=word.length();
            switch (direction){
                case HORIZONTAL:
                    if(coordinate.y+wordLength>gridsize) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x][coordinate.y + i]!='_') return false;

                    }
                    break;

                case VERTICAL:
                    if(coordinate.x+wordLength>gridsize) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x+i][coordinate.y]!='_') return false;

                    }
                    break;

                case DIAGONAL:
                    if(  coordinate.x+wordLength>gridsize || coordinate.y+wordLength>gridsize) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x+i][coordinate.y+i]!='_') return false;

                    }
                    break;
                case HORIZONTAL_INVERSE:
                    if(coordinate.y<wordLength) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x][coordinate.y-i]!='_') return false;

                    }
                    break;

                case VERTICAL_INVERSE:
                    if(coordinate.x<wordLength) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x-i][coordinate.y]!='_') return false;

                    }
                    break;

                case DIAGONAL_INVERSE:
                    if(  coordinate.x<wordLength || coordinate.y<wordLength) return false;
                    for(int i=0;i<wordLength;i++){
                        if (contents[coordinate.x-i][coordinate.y-i]!='_') return false;
                    }
                    break;

            }
            return true;

        }

    }









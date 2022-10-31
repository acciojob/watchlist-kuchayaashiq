package com.driver;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MovieRepository {

   List<Movie> movies = new ArrayList<>();

    List<Director> directors =new ArrayList<>();

    void addMovie(Movie movie){
        movies.add(movie);
    }
    List<Movie> findAllMovies(){
        return  movies;
    }
    Movie getMovieByName(String name){
        for(Movie movie: movies){
            if(movie.getName().equals(name)){
                return movie;
            }
        }
        return null;
    }
    void deleteMovie(String name){
        for(int i = 0; i < movies.size(); i++){
            for(Movie movie: movies){
                if(movies.get(i).getName().equals(name)){
                    movies.remove(i);
                }
            }
        }
    }
    void addDirector(Director director){
        directors.add(director);
    }
    Director getDirectorByName(String name){
        for(Director director: directors){
            if(director.getName().equals(name)){
                return director;
            }
        }
        return null;
    }
    void deleteDirector(String name){
        for(int i = 0; i < directors.size(); i++){
            for(Director director: directors){
                if(directors.get(i).getName().equals(name)){
                    directors.remove(i);
                }
            }
        }
    }
    void deleteAllDirectors(){
        directors = new ArrayList<>();
        return;
    }


}

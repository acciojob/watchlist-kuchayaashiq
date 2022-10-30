package com.driver;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MovieRepository {

   List<Movie> movies = new ArrayList<>();

    void addMovie(Movie movie){
        movies.add(movie);
    }
    List<Movie> getAllMovies(){
        return  movies;
    }
    Movie getMovieByName(String name){
        for(Movie movie: movies){
            if(movie.getName() == name){
                return movie;
            }
        }
        return null;
    }
    void deleteMovie(String name){
        for(Movie movie: movies){
            if(movie.getName() == name){
                movies.remove(movie);
            }
        }
    }

}

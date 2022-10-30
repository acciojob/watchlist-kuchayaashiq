package com.driver;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MovieRepository {

    HashMap<Integer,Movie> movies = new HashMap<>();

    void addMovie(Movie movie){
        movies.put(movie.getId(), movie);
    }
    List<Movie> getAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        for (Movie movie:movies.values()) {
             movieList.add(movie);
        }
        return  movieList;
    }
    Movie getMovieByName(String name){
        for(Movie movie: movies.values()){
            if(movie.getName() == name){
                return movie;
            }
        }
        return null;
    }
    void deleteMovie(String name){
        for(Movie movie: movies.values()){
            if(movie.getName() == name){
                movies.remove(movie.getId());
            }
        }
    }

}

package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    List<Movie> getAllMovie(){
        return movieRepository.getAllMovies();
    }

    void addMovies(Movie movie){
        movieRepository.addMovie(movie);
    }
    Movie getMovieByName(String name){
        return movieRepository.getMovieByName(name);
    }
    void deleteMovie(String name){
        movieRepository.deleteMovie(name);
    }
}

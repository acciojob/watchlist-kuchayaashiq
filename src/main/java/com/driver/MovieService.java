package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired

    MovieRepository movieRepository;
    List<String> findAllMovie(){
        List<String> moviesName = new ArrayList<>();
        List<Movie>  movieList = movieRepository.findAllMovies();
        for (Movie movie : movieList){
            moviesName.add(movie.getName());
        }
        return moviesName;
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
    void addDirector(Director director){
        movieRepository.addDirector(director);
    }

    Director getDirectorByName(String name){
        return movieRepository.getDirectorByName(name);
    }
    void deleteDirector(String name){
        movieRepository.deleteDirector(name);
    }
    void deleteAllDirectors(){
        movieRepository.deleteAllDirectors();
    }
}

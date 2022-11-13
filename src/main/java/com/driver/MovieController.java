package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@RestController
@RequestMapping("movies")
@EnableSwagger2
@EnableWebMvc
public class MovieController {

    HashMap<String, List<Movie>> pair =  new HashMap<>();
    @Autowired
    MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody() Movie movie) {
        movieService.addMovies(movie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/add-director")
    public ResponseEntity addDirector(@RequestBody() Director director){
        movieService.addDirector(director);
        pair.put(director.getName(),new ArrayList<>());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/add-movie-director-pair")
    public ResponseEntity addMovieDirectorPair(@RequestParam(value="mName") String mName,@RequestParam(value="dName") String dName){
        Movie movie = movieService.getMovieByName(mName);
        for(Map.Entry<String, List<Movie>> entry:pair.entrySet()){
            if(entry.getKey().equals(dName)){
                pair.get(dName).add(movie);
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //GET /movies/get-movie-by-name/{name}
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable("name") String name) {
        Movie movie = movieService.getMovieByName(name);
        return new ResponseEntity<>( movie, HttpStatus.OK);
    }

    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable("name") String name) {
        Director director = movieService.getDirectorByName(name);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @GetMapping("/get-movies-by-director-name/{director}")
    public  ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable("director") String director){
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String , List<Movie>> entry: pair.entrySet()){
            if(entry.getKey().equals(director)){
                listMovies = entry.getValue();

            }
        }
        List<String> movieNamesList =new ArrayList<>();
        for(Movie movie: listMovies){
            movieNamesList.add(movie.getName());
        }

        return new ResponseEntity<>(movieNamesList, HttpStatus.OK);

    }
    @GetMapping("/get-all-movies")
    public  ResponseEntity<List<String>> findAllMovies(){
        List<String> movieList = movieService.findAllMovie();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }
    @DeleteMapping("/delete-movie-by-name")
    public ResponseEntity deleteMovieByName(@RequestParam(value ="name") String name){
        movieService.deleteMovie(name);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    

    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity deleteDirectorByName(@RequestParam("name") String name ){
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String , List<Movie>> entry: pair.entrySet()){
            if(entry.getKey().equals(name)){
                movieService.deleteDirector(name);
                listMovies = entry.getValue();
                for (Movie movie: listMovies){
                    movieService.deleteMovie(movie.getName());
                }
                pair.remove(entry.getKey());
            }

        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @DeleteMapping("/delete-all-directors")
    public ResponseEntity deleteAllDirectors(){
        movieService.deleteAllDirectors();
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String , List<Movie>> entry: pair.entrySet()){
            listMovies = entry.getValue();
            for (Movie movie: listMovies){
                movieService.deleteMovie(movie.getName());
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}

package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("movies")
public class MovieController {



    HashMap<String, List<Movie>> pair =  new HashMap<>();
    @Autowired
    MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        movieService.addMovies(movie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //GET /movies/get-movie-by-name/{name}
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity getMovieByName(@PathVariable("name") String name) {
        Movie movie = movieService.getMovieByName(name);
        return new ResponseEntity<>( movie, HttpStatus.OK);
    }

    @GetMapping("/get-all-movies")
    public  ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovie(), HttpStatus.OK);
    }
    @DeleteMapping("/delete-movie-by-name/{name}")
    public ResponseEntity deleteMovieByName(@PathVariable("name") String name){
        movieService.deleteMovie(name);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
    @PostMapping("/add-director")
    public ResponseEntity addDirector(@RequestBody Director director){
        movieService.addDirector(director);
        pair.put(director.getName(),new ArrayList<>());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable("name") String name) {
        return new ResponseEntity(movieService.getDirectorByName(name), HttpStatus.OK);
    }
    @PutMapping("/add-movie-director-pair")
    public ResponseEntity addMovieDirectorPair(@RequestParam String mName, String dName){
        Movie movie = movieService.getMovieByName(mName);
        for(Map.Entry<String, List<Movie>> entry:pair.entrySet()){
            if(entry.getKey().equals(dName)){
                pair.get(dName).add(movie);
            }
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/get-movies-by-director-name/{director}")
    public  ResponseEntity<List<Movie>> getMoviesByDirectorName(@PathVariable("director") String director){
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String , List<Movie>> entry: pair.entrySet()){
            if(entry.getKey().equals(director)){
                listMovies = entry.getValue();

            }
        }

        return new ResponseEntity<>(listMovies, HttpStatus.OK);

    }
    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity deleteDirectorByName(@RequestParam String name ){
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String , List<Movie>> entry: pair.entrySet()){
            if(entry.getKey().equals(name)){
                movieService.deleteDirector(name);
                listMovies = entry.getValue();
                for (Movie movie: listMovies){
                    movieService.deleteMovie(movie.getName());
                }

            }
            pair.remove(entry);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @DeleteMapping("/delete-all-directors")
    public ResponseEntity deleteAllDirectors(){
        movieService.deleteAllDirectors();
        pair = new HashMap<>();
        return new ResponseEntity("success", HttpStatus.OK);
    }

}

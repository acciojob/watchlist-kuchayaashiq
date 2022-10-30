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


    List<Director> directors =new ArrayList<>();
    HashMap<String, String> pair =  new HashMap<>();
    @Autowired
    MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        movieService.addMovies(movie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //GET /movies/get-movie-by-name/{name}
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity getMOvieByName(@PathVariable("name") String name) {

        return new ResponseEntity<>(movieService.getMovieByName(name), HttpStatus.OK);
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
        directors.add(director);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity getdirectorByName(@PathVariable("name") String name) {
        for(Director director: directors){
            if(director.getName().equals(name)){
                return new ResponseEntity(director, HttpStatus.OK);
            }
        }
        return new ResponseEntity("No director found",HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/add-movie-director-pair")
    public ResponseEntity addMovieDirectorPair(@RequestParam String mName, String dName){
        pair.put(mName, dName);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/get-movies-by-director-name/{director}")
    public  ResponseEntity<List<Movie>> getMoviesByDirectorName(@PathVariable("director") String director){
        List<Movie> listMovies = new ArrayList<>();
        for(Map.Entry<String, String> e : pair.entrySet()){
            if(e.getValue() == director){
                listMovies.add(movieService.getMovieByName(e.getKey()));
            }
        }

        return new ResponseEntity<>(listMovies, HttpStatus.OK);
    }
    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity deleteDirectorByName(@RequestParam String name ){
        Iterator<Map.Entry<String, String> >iterator = pair.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> e =  iterator.next();
            if(name == e.getValue()){
                movieService.deleteMovie(name);

            }
            pair.remove(e.getKey());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @DeleteMapping("/delete-all-directors")
    public ResponseEntity deleteAllDirectors(){
        directors = new ArrayList<>();
        Iterator<Map.Entry<String, String> >iterator = pair.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> e =  iterator.next();
            movieService.deleteMovie(e.getKey());
            pair.remove(e.getKey());
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }

}

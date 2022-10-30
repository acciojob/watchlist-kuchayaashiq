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
    private int id;
    private int dId;

    public MovieController() {
        this.id = 1;
        this.dId = 1;
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    HashMap<Integer, Director> directors = new HashMap<>();
    HashMap<String, String> pair =  new HashMap<>();
    @Autowired
    MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody Movie movie) {

        movie.setId(this.id);
        this.id = this.id + 1;
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

    @PostMapping("/add-director")
    public ResponseEntity addDirector(@RequestBody Director director){
        director.setId(this.dId);
        this.dId = this.dId+1;
        directors.put(director.getId(),director);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity getdirectorByName(@PathVariable("name") String name) {
        for(Director director: directors.values()){
            if(director.getName() == name){
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

}

package com.bootcampjava.starwars.controller;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class JediController {

    private final JediService jediService;

    public JediController(JediService jediService) {
        this.jediService = jediService;
    }

    @GetMapping("/jedi/{id}")
    public ResponseEntity<?> getJedi(@PathVariable Integer id) {
        return jediService.findById(id)
                .map(jedi -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(jedi.getVersion()))
                                .location(new URI("/jedi/" + jedi.getVersion()))
                                .body(jedi);

                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/jedi")
    public ResponseEntity<Jedi> saveJedi(@RequestBody Jedi jedi) {
        Jedi newJedi = jediService.save(jedi);

        try {
            return ResponseEntity
                    .created(new URI("/jedi/" + jedi.getId()))
                    .eTag(Integer.toString(jedi.getVersion()))
                    .body(newJedi);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/jedis")
    public ResponseEntity<?> getAllJedis() {
        List jedis = this.jediService.findAll();

        try {
            return ResponseEntity
                    .created(new URI("/jedi/"))
                    .body(jedis);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/update")
    public ResponseEntity updateJedi(@RequestBody Jedi jedi) {
        Jedi newJedi =this.jediService.update(jedi);
        return ResponseEntity.status(HttpStatus.OK).body(newJedi);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteJedi(@PathVariable Integer id) {
        this.jediService.delete(id);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}

package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JediTestService {

    @Autowired
    private JediService jediService;

    @Autowired
    private JediRepositoryImpl jediRepository;

    @Test
    @DisplayName("Should return Jedi with Succes")
    public void testFindBySucces() {

        //cenario
        Jedi mockJedi = new Jedi(1, "HanSolo", 50, 1);

        //execução
        Optional<Jedi> returnedJedi = this.jediService.findById(1);

        //assert
        Assertions.assertTrue(returnedJedi.isPresent(), "Jedi was not found");
        Assertions.assertEquals(returnedJedi.get().getName(), mockJedi.getName(), "Jedi must be the same");
    }

    @Test
    @DisplayName("Should return fail")
    public void testFindFail() {
        //cenario
        Jedi mockJedi = new Jedi(2, "HanSolo", 50, 1);

        //execução
        Optional<Jedi> returnedJedi = this.jediService.findById(2);

        //assert
        Assertions.assertEquals(this.jediService.findById(2), Optional.empty());
    }

}

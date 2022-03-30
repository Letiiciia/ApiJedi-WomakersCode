package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JediService {
    private static final Logger logger = LogManager.getLogger(JediService.class);


    private JediRepositoryImpl jediRepository;

    public JediService(JediRepositoryImpl jediRepository) {
        this.jediRepository = jediRepository;
    }

    public Optional<Jedi> findById(Integer id) {
        logger.info("Find Jedi with id: {}", id);
        return this.jediRepository.findById(id);
    }

    public List<Jedi> findAll() {
        logger.info("Bring all the Jedis from the Galaxy");
        return this.jediRepository.findAll();
    }

    public Jedi save(Jedi jedi) {
        logger.info("Update Jedi from system");
        return this.jediRepository.save(jedi);
    }

    public Optional<Jedi> update(Jedi jedi, Integer id) {
        boolean updated = false;

        Optional<Jedi> jediUpdate = this.jediRepository.findById(id);

        jediUpdate = Optional.ofNullable(this.jediRepository.save(jedi));

        if(jediUpdate != null) {
            updated = true;
        }
        return jediUpdate;
    }

    public boolean delete() {
        return true;
    }
}

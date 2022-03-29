package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class JediRepositoryImpl implements JediRepository {

    private static final Logger logger = LogManager.getLogger(JediService.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JediRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withCatalogName("jedis")
                .usingGeneratedKeyColumns("id");
    }




    @Override
    public Optional<Jedi> findById(Integer id) {
        try {
            Jedi jedi = this.jdbcTemplate.queryForObject("SELECT * FROM jedis WHERE id=?", new Object[]{id},
                    (rs, rowNum) -> {
                        Jedi j = new Jedi();
                        j.setId(rs.getInt("id"));
                        j.setName(rs.getString("name"));
                        j.setStrength(rs.getInt("strength"));
                        j.setVersion(rs.getInt("version"));
                        return j;
                    });
            return Optional.of(jedi);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Jedi> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM jedis",
                (rs, rowNumber) -> {
                    Jedi jedi = new Jedi();
                    jedi.setId(rs.getInt("id"));
                    jedi.setName(rs.getString("name"));
                    jedi.setStrength(rs.getInt("strength"));
                    jedi.setVersion(rs.getInt("version"));
                    return jedi;
                });
    }

    @Override
    public boolean update(Jedi jedi) {
        return this.jdbcTemplate.update("UPDATE jedis SET nome=?, strength=?, version=? WHERE id=?",
                jedi.getName(),
                jedi.getStrength(),
                jedi.getVersion(),
                jedi.getId()) == 1;
    }

    @Override
    public Jedi save(Jedi jedi) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("id", jedi.getId());
        parameters.put("name", jedi.getName());
        parameters.put("strength", jedi.getStrength());
        parameters.put("version", jedi.getVersion());

       this.simpleJdbcInsert.withTableName("jedis").execute(parameters);


        return jedi;
    }

    @Override
    public boolean delete(Integer id) {
        return this.jdbcTemplate.update("DELETE FROM jedis WHERE id=?", id) == 1;

    }
}

package ru.spb.idu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.layer.Layer;

import java.util.List;

@Repository
public interface LayerRepository extends AbstractEntityRepository<Layer> {
    @Query(value = "SELECT * FROM layers WHERE lower(name) LIKE lower(?1) || '%' LIMIT ?2", nativeQuery = true)
    List<Layer> findByNameStartingWith(String startsWith, Integer maxRows);

}

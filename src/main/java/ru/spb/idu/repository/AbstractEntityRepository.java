package ru.spb.idu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.spb.idu.domain.AbstractEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractEntityRepository<E extends AbstractEntity> extends CrudRepository<E, Long> {
    Optional<E> findById(Long id);
}

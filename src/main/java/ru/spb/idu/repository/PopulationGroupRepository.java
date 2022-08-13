package ru.spb.idu.repository;

import org.springframework.data.repository.NoRepositoryBean;
import ru.spb.idu.domain.population.PopulationGroup;

@NoRepositoryBean
public interface PopulationGroupRepository<E extends PopulationGroup> extends AbstractEntityRepository<E> {
}

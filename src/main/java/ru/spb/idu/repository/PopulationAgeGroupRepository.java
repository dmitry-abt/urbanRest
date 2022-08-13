package ru.spb.idu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.population.PopulationAgeGroup;

import java.util.List;

@Repository
public interface PopulationAgeGroupRepository extends PopulationGroupRepository<PopulationAgeGroup> {
    @Query(value="select group_id as id, sum(value) as value, " +
                    "min(description\\:\\:int) || '-' || max(description\\:\\:int) as description " +
                 "from population_groups_age " +
                 "where char_length(description) <= 2 " +
                 "group by group_id " +
                 "union " +
                 "select group_id as id, value, description " +
                 "from population_groups_age " +
                 "where char_length(description) > 2" , nativeQuery = true)
    List<PopulationAgeGroup> getGrops();

}

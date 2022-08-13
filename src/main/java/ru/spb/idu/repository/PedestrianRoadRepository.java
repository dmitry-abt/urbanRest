package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.pedestrianroad.PedestrianRoad;

@Repository
public interface PedestrianRoadRepository extends GeometricEntityRepository<PedestrianRoad> {
}

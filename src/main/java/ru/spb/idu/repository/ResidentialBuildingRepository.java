package ru.spb.idu.repository;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.building.ResidentialBuilding;

import java.util.List;

@Repository
public interface ResidentialBuildingRepository extends AbstractEntityRepository<ResidentialBuilding> {
    @Query("select c from ResidentialBuilding c where intersects(c.geometry, ST_SetSRID(?1, 4326)) = true")
    List<ResidentialBuilding> findIntersect(Geometry filter);

    @Query("select c from ResidentialBuilding c where dwithin(c.geometry, ST_SetSRID(?1, 4326), ?2, true) = true")
    List<ResidentialBuilding> findNear(Geometry filter, Integer radius);
}

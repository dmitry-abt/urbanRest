package ru.spb.idu.repository;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.spb.idu.domain.GeometricEntity;
import ru.spb.idu.domain.pedestrianroad.PedestrianRoad;
import ru.spb.idu.domain.carroad.CarRoad;

import java.util.List;

@NoRepositoryBean
public interface GeometricEntityRepository<E extends GeometricEntity> extends AbstractEntityRepository<E> {
    @Query("select c from #{#entityName} c where intersects(c.geometry, ST_SetSRID(?1, 4326)) = true")
    List<E> findIntersect(Geometry filter);

    //@Query("select c from PedestrianRoad c where ___pedestrian_graph(c.id, ST_SetSRID(?1, 4326)) = true and dwithin(c.geometry, ST_SetSRID(?1, 4326), 500, true) = true")
    @Query(value = "select * from ___pedestrian_graph(ST_SetSRID(?1, 4326), ?2)", nativeQuery = true)
    List<E> findPedestrianGraph(Geometry filter, Integer radius);

    @Query(value = "select * from ___pedestrian_concave_hull(ST_SetSRID(?1, 4326), ?2)", nativeQuery = true)
    List<E> findPedestrianConcaveHull(Geometry filter, Integer radius);

    @Query(value = "select * from ___car_graph(ST_SetSRID(?1, 4326), ?2)", nativeQuery = true)
    List<E> findCarGraph(Geometry filter, Integer radius);

    @Query(value = "select * from ___car_concave_hull(ST_SetSRID(?1, 4326), ?2)", nativeQuery = true)
    List<E> findCarConcaveHull(Geometry filter, Integer radius);

    @Query("select c from #{#entityName} c where within(c.geometry, ST_SetSRID(?1, 4326)) = true")
    List<E> findWithin(Geometry filter);

    @Query("select c from #{#entityName} c where dwithin(c.geometry, ST_SetSRID(?1, 4326), ?2, true) = true")
    List<E> findNear(Geometry filter, Integer radius);

}

interface PedestrianProjection {
    Geometry getGeometry();
}
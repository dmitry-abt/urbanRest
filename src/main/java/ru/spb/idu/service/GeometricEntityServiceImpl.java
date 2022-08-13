package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.idu.domain.GeometricEntity;
import ru.spb.idu.repository.CarRoadRepository;
import ru.spb.idu.repository.GeometricEntityRepository;
import ru.spb.idu.repository.PedestrianRoadRepository;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
@Service
public class GeometricEntityServiceImpl implements GeometricEntityService {

    @Autowired
    private List<GeometricEntityRepository> geometricEntityRepositories;

    @Override
    public List<GeometricEntity> findWithin(Geometry filter) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository:
                geometricEntityRepositories) {
            result.addAll(repository.findWithin(filter));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findIntersect(Geometry filter) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository:
                geometricEntityRepositories) {
            result.addAll(repository.findIntersect(filter));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findPedestrianGraph(Geometry filter, Integer radius) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository: geometricEntityRepositories) {
            if (repository instanceof PedestrianRoadRepository)
                result.addAll(repository.findPedestrianGraph(filter, radius));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findCarGraph(Geometry filter, Integer radius) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository: geometricEntityRepositories) {
            if (repository instanceof CarRoadRepository)
                result.addAll(repository.findCarGraph(filter, radius));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findPedestrianConcaveHull(Geometry filter, Integer radius) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository: geometricEntityRepositories) {
            if (repository instanceof PedestrianRoadRepository)
                result.addAll(repository.findPedestrianConcaveHull(filter, radius));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findCarConcaveHull(Geometry filter, Integer radius) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository: geometricEntityRepositories) {
            if (repository instanceof CarRoadRepository)
                result.addAll(repository.findCarConcaveHull(filter, radius));
        }

        return result;
    }

    @Override
    public List<GeometricEntity> findNear(Geometry filter, Integer radius) {
        List<GeometricEntity> result = new LinkedList<>();

        for (GeometricEntityRepository repository:
                geometricEntityRepositories) {
            result.addAll(repository.findNear(filter, radius));
        }

        return result;
    }
}

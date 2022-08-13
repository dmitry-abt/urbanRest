package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.idu.domain.building.ResidentialBuilding;
import ru.spb.idu.domain.population.PopulationAgeGroup;
import ru.spb.idu.domain.population.PopulationGroup;
import ru.spb.idu.repository.PopulationAgeGroupRepository;
import ru.spb.idu.repository.PopulationGroupRepository;
import ru.spb.idu.repository.ResidentialBuildingRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PopulationServiceImpl implements PopulationService {

    @Autowired
    private List<PopulationGroupRepository> populationGroupRepositories;

    @Autowired
    private PopulationAgeGroupRepository ageGroupRepository;

    @Autowired
    private ResidentialBuildingRepository buildingRepository;

    private List<PopulationGroup> populationGroups = new LinkedList<>();

    @PostConstruct
    private void postConstruct() {
        for (PopulationGroupRepository repository:
                populationGroupRepositories) {
            populationGroups.addAll(ageGroupRepository.getGrops());
        }
    }

    @Override
    public Map<String, Long> getDistribution(Geometry filter) {
        Float totalPopulation = 0.0f;
        Map<String, Long> result = new HashMap<>();

        for (ResidentialBuilding building:
                buildingRepository.findIntersect(filter)) {
            totalPopulation += building.getResidentNumber();
        }

        totalPopulation *= 0.01f;

        for (PopulationGroup group:
                populationGroups) {
            result.put(group.getDescription(), Math.round(totalPopulation * group.getValue()));
        }

        return result;
    }

}

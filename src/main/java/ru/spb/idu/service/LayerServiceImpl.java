package ru.spb.idu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.idu.domain.layer.Layer;
import ru.spb.idu.repository.LayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LayerServiceImpl implements LayerService {
    @Autowired
    private LayerRepository layerRepository;

    @Override
    public Optional<Layer> findById(Long id) { return layerRepository.findById(id); }

    @Override
    public List<Layer> findByNameStartingWith(String startsWith, Integer maxRows) { return layerRepository.findByNameStartingWith(startsWith, maxRows); }
}

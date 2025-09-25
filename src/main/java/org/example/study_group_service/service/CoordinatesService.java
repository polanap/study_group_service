package org.example.study_group_service.service;

import org.example.study_group_service.models.dto.incomming.Coordinates;
import org.example.study_group_service.models.entity.CoordinatesEntity;
import org.example.study_group_service.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesService {
    @Autowired
    CoordinatesRepository coordinatesRepository;

    public CoordinatesEntity save(Coordinates coordinates) {
        return coordinatesRepository.save(new CoordinatesEntity(coordinates));
    }

    public CoordinatesEntity findById(Long id) {
        return coordinatesRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        coordinatesRepository.deleteById(id);
    }

    public Page<CoordinatesEntity> getAll(
            PageRequest pageRequest
    ) {
        return coordinatesRepository.findAll(pageRequest);
    }
}

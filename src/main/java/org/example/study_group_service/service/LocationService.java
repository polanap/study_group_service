package org.example.study_group_service.service;

import org.example.study_group_service.models.dto.incomming.Location;
import org.example.study_group_service.models.entity.LocationEntity;
import org.example.study_group_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public LocationEntity save(Location location) {
        return locationRepository.save(new LocationEntity(location));
    }

    public LocationEntity findById(Long id) {
        return locationRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public Page<LocationEntity> getAllFiltered(
            String name,
            PageRequest pageRequest
    ) {
        return locationRepository.getPageFiltered(
                name == null ? "" : name,
                pageRequest
        );
    }
}

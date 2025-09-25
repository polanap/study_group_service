package org.example.study_group_service.web;

import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.Location;
import org.example.study_group_service.models.entity.LocationEntity;
import org.example.study_group_service.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;


@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        locationService.deleteById(id);
    }

    @PostMapping
    public void save(@RequestBody Location location){
        locationService.save(location);
    }

    @GetMapping
    public LocationEntity findById(@RequestParam Long id){
        return locationService.findById(id);
    }

    @GetMapping("/filtered")
    public Page<LocationEntity> getAllFiltered(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) SortOrder byName,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return locationService.getAllFiltered(
                name,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                listOf(
                                        byName != null ? byName.toSpringSortOrder("name") : null
                                ).stream().filter(Objects::nonNull).toList()
                        )
                )
        );
    }
}
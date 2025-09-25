package org.example.study_group_service.web;

import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.Coordinates;
import org.example.study_group_service.models.entity.CoordinatesEntity;
import org.example.study_group_service.service.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;


@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {
    @Autowired
    private CoordinatesService coordinatesService;

    @DeleteMapping
    public void deleteById(@RequestParam Long id) {
        coordinatesService.deleteById(id);
    }

    @PostMapping
    public void save(@RequestBody Coordinates coordinates) {
        coordinatesService.save(coordinates);
    }

    @GetMapping
    public CoordinatesEntity findById(@RequestParam Long id) {
        return coordinatesService.findById(id);
    }

    @GetMapping("/filtered")
    public Page<CoordinatesEntity> getAllFiltered(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) SortOrder byName,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return coordinatesService.getAll( PageRequest.of(page, size) );
    }
}
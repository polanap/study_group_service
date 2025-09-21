package org.example.study_group_service.web;

import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        personService.deleteById(id);
    }

    @PostMapping
    public void save(@RequestBody Person person){
        personService.save(person);
    }

    @GetMapping
    public PersonEntity findById(@RequestParam Long id){
        return personService.findById(id);
    }

    @GetMapping("/filtered")
    public Page<PersonEntity> getAllFiltered(
            @RequestParam String name,
            @RequestParam String eyeColor,
            @RequestParam String hairColor,
            @RequestParam String nationality,
            @RequestParam SortOrder byName,
            @RequestParam SortOrder byEyeColor,
            @RequestParam SortOrder byHairColor,
            @RequestParam SortOrder byNationality,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return personService.getAllFiltered(
                name,
                eyeColor,
                hairColor,
                nationality,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                listOf(
                                        byName.toSpringSortOrder("name"),
                                        byEyeColor.toSpringSortOrder("eyeColor"),
                                        byHairColor.toSpringSortOrder("hairColor"),
                                        byNationality.toSpringSortOrder("nationality")
                                ).stream().filter(Objects::nonNull).toList()
                        )
                )
        );
    }
}

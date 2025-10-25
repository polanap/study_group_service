package org.example.study_group_service.web;

import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.service.PersonService;
import org.example.study_group_service.service.handler.PersonWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@RestController
@RequestMapping("api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonWebSocketHandler personWebSocketHandler;

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        personService.deleteById(id);
        personWebSocketHandler.sendPeopleUpdate();
    }

    @PostMapping
    public void save(@RequestBody Person person){
        personService.save(person);
        personWebSocketHandler.sendPeopleUpdate();

    }

    @GetMapping("/{id}")
    public PersonEntity findById(@PathVariable("id") Long id){
        return personService.findById(id);
    }

    @GetMapping
    public Page<PersonEntity> getAllFiltered(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String eyeColor,
            @RequestParam(required=false) String hairColor,
            @RequestParam(required=false) String nationality,
            @RequestParam(required=false) SortOrder byName,
            @RequestParam(required=false) SortOrder byEyeColor,
            @RequestParam(required=false) SortOrder byHairColor,
            @RequestParam(required=false) SortOrder byNationality,
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
                                        byName != null ? byName.toSpringSortOrder("name") : null,
                                        byEyeColor != null ? byEyeColor.toSpringSortOrder("eyeColor") : null,
                                        byHairColor != null ? byHairColor.toSpringSortOrder("hairColor") : null,
                                        byNationality != null ? byNationality.toSpringSortOrder("nationality") : null
                                ).stream().filter(Objects::nonNull).toList()
                        )
                )
        );
    }
}

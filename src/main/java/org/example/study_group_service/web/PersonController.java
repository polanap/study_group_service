package org.example.study_group_service.web;

import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.service.MessagingService;
import org.example.study_group_service.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@RestController
@RequestMapping("api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final MessagingService messagingService;

    @DeleteMapping
    public void deleteById(@RequestParam Long id) {
        personService.deleteById(id);
        messagingService.sendMessageToTopic("/topic/people", "313");
    }

    @PostMapping
    public void save(@RequestBody Person person) {
        personService.save(person);
        messagingService.sendMessageToTopic("/topic/people", "313");
    }

    @GetMapping("/{id}")
    public PersonEntity findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping
    public Page<PersonEntity> getAllFiltered(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "eyeColor", required = false) String eyeColor,
            @RequestParam(name = "hairColor", required = false) String hairColor,
            @RequestParam(name = "nationality", required = false) String nationality,
            @RequestParam(name = "byName", required = false) SortOrder byName,
            @RequestParam(name = "byEyeColor", required = false) SortOrder byEyeColor,
            @RequestParam(name = "byHairColor", required = false) SortOrder byHairColor,
            @RequestParam(name = "byNationality", required = false) SortOrder byNationality,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
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

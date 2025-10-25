package org.example.study_group_service.web;

import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.service.PersonService;
import org.example.study_group_service.service.handler.PersonWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Controller
@RequestMapping("api/people")
public class PersonWebController {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonWebSocketHandler personWebSocketHandler;

    @GetMapping
    public String viewPeople(Model model,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String eyeColor,
                             @RequestParam(required = false) String hairColor,
                             @RequestParam(required = false) String nationality,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "4") int size,
                             @RequestParam(required = false) SortOrder byName,
                             @RequestParam(required = false) SortOrder byEyeColor,
                             @RequestParam(required = false) SortOrder byHairColor,
                             @RequestParam(required = false) SortOrder byNationality) {

        Page<PersonEntity> personPage = personService.getAllFiltered(
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

        model.addAttribute("people", personPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());

        if (personPage.isEmpty()) {
            model.addAttribute("noResults", true);
        }

        model.addAttribute("filterName", name);
        model.addAttribute("filterEyeColor", eyeColor);
        model.addAttribute("filterHairColor", hairColor);
        model.addAttribute("filterNationality", nationality);

        return "people";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable Long id, Model model) {
        PersonEntity person = personService.findById(id);
        model.addAttribute("person", person);
        return "personDetail";
    }

    @PostMapping
    public String savePerson(@Valid @ModelAttribute Person person) {
        personService.save(person);
        personWebSocketHandler.sendPeopleUpdate();
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
        personWebSocketHandler.sendPeopleUpdate();
        return ResponseEntity.noContent().build();
    }
}

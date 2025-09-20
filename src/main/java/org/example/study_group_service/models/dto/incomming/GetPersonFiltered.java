package org.example.study_group_service.models.dto.incomming;

import org.example.study_group_service.models.SortOrder;
import org.springframework.data.domain.Sort;
import java.util.List;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

public class GetPersonFiltered {
    Filters filters;
    Sorts sorts;

    static class Filters {
        String firstName;
        String lastName;
    }

    static class Sorts {
        SortOrder byName;
        List<Sort.Order> toSpringSorts() {
            // TODO: проверить, чтобы не было null
            return listOf(byName.toSpringSortOrder("name"));
        }
    }
}
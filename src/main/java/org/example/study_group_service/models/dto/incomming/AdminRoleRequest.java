package org.example.study_group_service.models.dto.incomming;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

public class AdminRoleRequest {
    @NotNull
    @Size(min = 2, max = 20)
    private String username; // Не может быть null, размер не меньше 3 и не больше 20 символов
    @NotNull
    private OffsetDateTime requestDateTime;
}

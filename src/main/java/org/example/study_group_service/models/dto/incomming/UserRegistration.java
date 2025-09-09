package org.example.study_group_service.models.dto.incomming;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * UserRegistration
 */
@Data
public class UserRegistration {
  @NotNull
  @Size(min = 2, max = 20)
  private String username; // Не может быть null, размер не меньше 3 и не больше 20 символов
  @NotNull
  @Size(min = 6, max = 30)
  private String password; // Не может быть null, размер не меньше 6 и не больше 30 символов
  @NotNull
  @Size(min = 6, max = 30)
  private String confirmPassword; // Не может быть null, размер не меньше 6 и не больше 30 символов

}

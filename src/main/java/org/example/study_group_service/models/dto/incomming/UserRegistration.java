package org.example.study_group_service.models.dto.incomming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * UserRegistration
 */
@Data
public class UserRegistration {
  @NotNull(message = "Username is required")
  @Size(min = 2, max = 20, message = "Username size must be between 2 and 20")
  private String username; // Не может быть null, размер не меньше 3 и не больше 20 символов
  @NotNull(message = "Password is required")
  @Size(min = 6, max = 30, message = "Password size must be between 6 and 30")
  private String password; // Не может быть null, размер не меньше 6 и не больше 30 символов
  @NotNull(message = "Password confirmation is required")
  @Size(min = 6, max = 30, message = "Password confirmation size must be between 6 and 30")
  private String confirmPassword; // Не может быть null, размер не меньше 6 и не больше 30 символов

}

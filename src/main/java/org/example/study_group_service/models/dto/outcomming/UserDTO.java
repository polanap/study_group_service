package org.example.study_group_service.models.dto.outcomming;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import org.example.study_group_service.models.entity.UserEntity;


/**
 * User
 */
@Data
public class UserDTO {
  @JsonSetter("id")
  private Long id = null;

  @JsonSetter("username")
  private String username = null;

  public UserDTO id(Long id) {
    this.id = id;
    return this;
  }

  public UserDTO(UserEntity user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
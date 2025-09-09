package org.example.study_group_service.models.dto.outcomming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessageDTO {
    private String errorMessage = null;

    public ErrorMessageDTO (String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
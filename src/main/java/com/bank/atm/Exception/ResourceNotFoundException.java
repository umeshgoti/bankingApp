package com.bank.atm.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7243969836115913729L;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private Boolean isForDelConflict;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found.", resourceName));
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String resourceName, Boolean isForDelConflict) {
        super(String.format("%s not found.", resourceName));
        this.resourceName = resourceName;
        this.isForDelConflict = isForDelConflict;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {

        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

package com.andela.irrigationsystem.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Brume
 **/
@AllArgsConstructor
public enum StatusType {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    UNSUCCESSFUL("UNSUCCESSFUL");
    @Getter
    private final String status;
}

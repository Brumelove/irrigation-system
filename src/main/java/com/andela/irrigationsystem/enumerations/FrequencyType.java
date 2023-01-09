package com.andela.irrigationsystem.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Brume
 **/
@AllArgsConstructor
public enum FrequencyType {
    MINUTE("MINUTE"),
    HOURLY("HOURLY"),
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY"),
    YEARLY("YEARLY");

    @Getter
    private final String frequency;
}

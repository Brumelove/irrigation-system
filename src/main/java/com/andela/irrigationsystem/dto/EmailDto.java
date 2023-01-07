package com.andela.irrigationsystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Brume
 **/
@Getter
@Setter
public class EmailDto {
    private String sendersAddress;
    private String recipientsAddress;

    private String message;
    private String messageId;
}

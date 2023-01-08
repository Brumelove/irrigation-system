package com.andela.irrigationsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Brume
 **/
@Getter
@Setter
@Builder
public class EmailDto {
    private String sendersAddress;
    private String recipientsAddress;

    private String message;
    private String messageId;
}

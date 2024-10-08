package com.apex.picloud.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionRepresentation {
    private String errorMessage;
    private String errorSource;
    private Set<String> validationErros;
}

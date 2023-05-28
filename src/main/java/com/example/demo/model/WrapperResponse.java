package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrapperResponse<T> {

    @JsonInclude(Include.NON_NULL)
    private Status status;

    @JsonInclude(Include.NON_NULL)
    private T data;
}

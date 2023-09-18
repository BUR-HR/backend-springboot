package com.bubblebubble.hr.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDTO {

    private int status;

    private String message;

    private Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(HttpStatus status, String message, Object data){
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

}

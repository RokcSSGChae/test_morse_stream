package com.morse.streaming.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonMessage {
    private String message;
    private Object data=null;

    public CommonMessage() {}

    public CommonMessage( String message ) {
        this.message=message;
    }

    public CommonMessage( Object result ) {
        this.message="success";
        this.data=result;
    }

    public CommonMessage( String message, Object result ){
        this.message = message;
        this.data = result;
    }
}

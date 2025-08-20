package com.sdl.auth.exception;

public class SDLException extends RuntimeException {
    
    private final String code;
    private final String message;
    
    public SDLException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

package com.reddit.clone.exception;

public class SpringRedditException extends RuntimeException{

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
    
    public SpringRedditException(String exMessage,Exception exception)
    {
        super(exMessage,exception);
    }
}

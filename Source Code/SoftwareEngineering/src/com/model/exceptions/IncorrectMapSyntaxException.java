package com.model.exceptions;

/**
 * Created by junho on 15. 4. 13..
 */
public class IncorrectMapSyntaxException extends Exception {

    /**
     * This exception will be called when user want to load up
     * but the map have violate generating map rule.
     * @param msg
     */
    public IncorrectMapSyntaxException(String msg){super(msg);}
}

package com.model.exceptions;

/**
 * Created by hao-linliang on 24/03/15.
 */
public class BrainSyntaxIncorrectException extends Exception{

    /**
     * This exception is cauesed error when Brain have syntax error.
     * @param msg
     */
    public BrainSyntaxIncorrectException(String msg){super(msg);}
}

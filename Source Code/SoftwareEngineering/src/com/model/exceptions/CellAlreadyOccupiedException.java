package com.model.exceptions;

public class CellAlreadyOccupiedException extends Exception{

    /**
     * This exception is called when ant try to move to some place where
     * already another ant takes.
     * @param msg
     */
    public CellAlreadyOccupiedException(String msg){super(msg);}
}
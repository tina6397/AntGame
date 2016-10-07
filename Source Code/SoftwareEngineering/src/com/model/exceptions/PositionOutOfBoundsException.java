package com.model.exceptions;

public class PositionOutOfBoundsException extends Exception {

    /**
     * This method is called when something try to access out of bound of map
     * @param msg
     */
    public PositionOutOfBoundsException(String msg){
        super(msg);
    }

}

package com.model.exceptions;


public class AntNotFoundException extends Exception {

    /**
     * This method will return when colony make command to ant
     * but if there is no ant which has specific id.
     * @param msg
     */
    public AntNotFoundException(String msg){
        super(msg);
    }
}


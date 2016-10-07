package com.model.exceptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvalidContentCharacterException extends Exception {

    /**
     * When map class update their contents.
     * This method will be called, if map try to change unavailable contents
     * @param msg
     */
    public InvalidContentCharacterException(String msg){
        super(msg);
    }

}

package com.model;

/**
 * Created by junho on 15. 4. 2..
 */
public enum Content {



    REDHILL,BLACKHILL,ROCKY,EMPTY,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE;


    /**
     * Transforms the enum value to an integer value
     * @param value
     * @return The integer value of the enum representing the number of foods in a cell
     */
    public static int getFoodValue(Content value){
        switch(value){
            case ONE:
                return 1;

            case TWO:
                return 2;

            case THREE:
                return 3;

            case FOUR:
                return 4;

            case FIVE:
                return 5;

            case SIX:
                return 6;

            case SEVEN:
                return 7;

            case EIGHT:
                return 8;

            case NINE:
                return 9;

            default:
                return -1;

        }
    }

    /**
     * Converts an integer value back to its enum value
     * @param i
     * @return The enum value of the integer value representing the number of foods in a cell
     */
    public static Content getFoodEnumValue(int i){

        switch(i){
            case 1:

                return ONE;

            case 2:
                return TWO;

            case 3:
                return THREE;

            case 4:
                return FOUR;

            case 5:
                return FIVE;

            case 6:
                return SIX;

            case 7:
                return SEVEN;

            case 8:
                return EIGHT;

            case 9:
                return NINE;

            default:
                return EMPTY;


        }


    }
}

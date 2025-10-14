package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    // TODO: fill me in!

    private byte [] data;

    public Hash (byte [] data){

        this.data = data;

    }

    public byte [] getData (){
        return this.data;
    }

    public boolean isValid (){
        if (data [0] == 0 && data [1] == 0 && data [2] == 0){
            return true;
        }
        return false;
    }

    public boolean equals(Object other){
        if (other instanceof Hash){
            Hash o = (Hash) other;
            if (Arrays.equals (data, o.data)){
                return true;
            }
        }
        return false;
    }

    public String toString (){
        String str = "";
        int bit;
        for (int i = 0; i < data.length; i++){
            bit = Byte.toUnsignedInt(data [i]);
            if (bit <10){
                str += ("0" + Integer.toString (bit));
            }
            else{
                int temp;
                str += bit/16;
                temp = bit%16;

                if (temp < 10){
                    str += temp;
                }
                else if (temp == 10){
                    str += "A";
                }
                else if (temp == 11){
                    str += "B";
                }
                else if (temp == 12){
                    str += "C";
                }
                else if (temp == 13){
                    str += "D";
                }
                else if (temp == 14){
                    str += "E";
                }
                else if (temp == 15){
                    str += "F";
                }
            }
        }
        return str;
    }

}

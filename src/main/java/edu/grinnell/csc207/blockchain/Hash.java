package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] data;

    /**
     * Constructs a new hash based on the given data.
     * @param data is stored via this hash object.
     */
    public Hash (byte[] data) {
        this.data = data;
    }

    /**
     * Gets the data field from the hash.
     * @return the data of this hash.
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Checks to see if the hash is valid based on our hashing constraints.
     * @return a boolean representing the hashes' validity.
     */
    public boolean isValid() {
        return data[0] == 0 && data[1] == 0 && data[2] == 0;
    }

    /**
     * Checks if one hash equals another.
     * @return a boolean representing the two hashes equality.
     * @param other is the other has being compared
     */
    public boolean equals(Object other) {
        if (other instanceof Hash){
            Hash o = (Hash) other;
            if (Arrays.equals(data, o.data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gives a string representation of the hash by converting to hexidecimal.
     * @return a string that represents the hash.
     */
    @Override
    public String toString() {
        String str = "";
        int bit;
        for (int i = 0; i < data.length; i++) {
            bit = Byte.toUnsignedInt(data[i]);
            if (bit < 10) {
                str += ("0" + Integer.toString(bit));
            } else {
                int temp;
                str += bit / 16;
                temp = bit % 16;
                if (temp < 10){
                    str += temp;
                } else if (temp == 10) {
                    str += "A";
                } else if (temp == 11) {
                    str += "B";
                } else if (temp == 12) {
                    str += "C";
                } else if (temp == 13) {
                    str += "D";
                } else if (temp == 14) {
                    str += "E";
                } else if (temp == 15) {
                    str += "F";
                }
            }
        }
        return str;
    }

}

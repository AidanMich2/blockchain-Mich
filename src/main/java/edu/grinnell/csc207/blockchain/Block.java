package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {
    private int num;
    private int amount;
    private Hash prevHash;
    private long nonce;

    Block(int num, int amount, Hash prevHash){
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
    }

    Block(int num, int amount, Hash prevHash, long nonce){
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
    }

    public int getNum(){
        return this.num;
    }

    public int getAmount(){
        return this.amount;
    }

    public long getNonce(){
        return this.nonce;
    }

    public Hash getPrevHash(){
        return prevHash;
    }

    public Hash getHash () throws NoSuchAlgorithmException{ //should hash be a variable in the constructor, cause currently it is not saved anywhere
        MessageDigest md = MessageDigest.getInstance("sha-256");
        byte [] hash = null;
        md.update (ByteBuffer.allocate(4).putInt(num));// for first block
        md.update (ByteBuffer.allocate(4).putInt(amount)); //for the amount or data in the block dont know how much to allocate
        md.update (getPrevHash ().getData());//previous blocks hash
        md.update (ByteBuffer.allocate(4).putLong(nonce));
        hash = md.digest();
        Hash h = new Hash(hash);
        return h;
    }

    // public String toString(){
       //return "Block <" + num + "> (Amount: <" + amount + ">, Nonce: <" + nonce + ">, prevHash: <" + prevHash + ">, hash: <" + hash +" >)" ;
        // arent currently saving hash so hash errors check comment above.
    // }

}


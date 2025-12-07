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

    /**
     * Constructs a single block without a nonce.
     * @param num is the number contained in this block.
     * @param amount is the amount contained in this block.
     * @param prevHash is the hash of the previous block.
     */
    Block(int num, int amount, Hash prevHash) {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
    }

    /**
     * Constructs a single block without a nonce.
     * @param num is the number contained in this block.
     * @param amount is the amount contained in this block.
     * @param prevHash is the hash of the previous block.
     * @param nonce is the nonce of this block.
     */
    Block(int num, int amount, Hash prevHash, long nonce) {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
    }

    /**
     * Gets the number of this block.
     * @return the number of this block.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Gets the amount of this block.
     * @return the amount of this block.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Gets the nonce of this block.
     * @return the nonce of this block.
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * Gets the previous hash of this block.
     * @return the previous hash of this block.
     */
    public Hash getPrevHash() {
        return prevHash;
    }

    /**
     * Sets the previous hash of this block.
     * @param prevHash is what we would like to set the previous hash to.
     */
    public void setPrevHash(Hash prevHash) {
        this.prevHash = prevHash;
    }

    /**
     * Gets the hash for this block.
     * @return the hash of this block.
     * @throws NoSuchAlgorithmException if there is an error in this calculation process.
     */
    public Hash getHash() throws NoSuchAlgorithmException { //should hash be a variable in the constructor, cause currently it is not saved anywhere
        MessageDigest md = MessageDigest.getInstance("sha-256");
        byte [] hash = {1,1,1};
        md.reset();
        md.update(ByteBuffer.allocate(4).putInt(num).array());// for first block
        md.update(ByteBuffer.allocate(4).putInt(amount).array()); //for the amount or data in the block dont know how much to allocate
        md.update(getPrevHash().getData());//previous blocks hash
        md.update(ByteBuffer.allocate(8).putLong(nonce).array());
        hash = md.digest();
        Hash h = new Hash(hash);
        return h;
    }

    /**
     * Calculates the hash for blocks without a previous hash.
     * @return the hash of this block.
     * @throws NoSuchAlgorithmException if there is an error in the calculation process.
     */
    public Hash getHashNoPrev() throws NoSuchAlgorithmException { //should hash be a variable in the constructor, cause currently it is not saved anywhere
        MessageDigest md = MessageDigest.getInstance("sha-256");
        byte [] hash = {1,1,1};
        md.reset();
        md.update(ByteBuffer.allocate(4).putInt(0).array());// for first block
        md.update(ByteBuffer.allocate(4).putInt(amount).array()); //for the amount or data in the block dont know how much to allocate
        md.update(ByteBuffer.allocate(8).putLong(nonce).array());
        hash = md.digest();
        Hash h = new Hash(hash);
        return h;
    }
}


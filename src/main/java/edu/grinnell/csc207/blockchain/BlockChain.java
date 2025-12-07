package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {
    public static class Node {
        public Block b;
        public Node next;

        /**
         * Constructs a node of a block and the next node.
         * @param b is a block.
         * @param next is another node.
         */
        public Node(Block b, Node next) {
            this.b = b;
            this.next = next;
        }
    }

    public Node first;
    public Node last;
    private int size = 0;
    public int amount;

    /**
     * Constructs a block chain for the specified amount.
     * @param amount the variable that it being stored in this transaction.
     * @throws NoSuchAlgorithmException if there is an issue in the mining process.
     */
    public BlockChain(int amount) throws NoSuchAlgorithmException {
        this.first = new Node(mine(amount), null);
        this.last = first;
        this.size++;
        this.amount = amount;
    }

    /**
     * Generates a block based on the specified amount by using the sha-256 algorithm.
     * @param amount is the number we are generating the block for.
     * @return the block we generated.
     * @throws NoSuchAlgorithmException if there is an error in the mining operation.
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long nonce = 0;
        if (size == 0) {
            byte[] bitArr = {1, 1, 1};
            Hash h = new Hash(bitArr);
            while (bitArr[0] != 0 || bitArr[1] != 0 || bitArr[2] != 0) {
                md.reset();
                md.update(ByteBuffer.allocate(4).putInt(0).array());
                md.update(ByteBuffer.allocate(4).putInt(amount).array());
                md.update(ByteBuffer.allocate(8).putLong(nonce).array());
                bitArr = md.digest();
                nonce++;
                h = new Hash(bitArr);
                
            }
            nonce--;
            Block nBlock = new Block(0, amount, null, nonce);
            nBlock.setPrevHash(h);
            return nBlock;
        } else {
            byte[] bitArr = {1, 1, 1};
            Hash h = new Hash(bitArr);
            while (bitArr[0] != 0 || bitArr[1] != 0 || bitArr[2] != 0) {
                md.reset();
                md.update(ByteBuffer.allocate(4).putInt(getSize()).array());
                md.update(ByteBuffer.allocate(4).putInt(amount).array()); 
                md.update(last.b.getHash().getData());
                md.update(ByteBuffer.allocate(8).putLong(nonce).array());
                bitArr = md.digest();
                nonce++;
                h = new Hash(bitArr);
            }
            System.out.println("Non zero case: " + h);
            nonce--;
            Block nBlock = new Block(getSize(), amount, null, nonce);
                nBlock = new Block(getSize(), amount, last.b.getHash(), nonce);
                nBlock.setPrevHash(last.b.getHash());
            return nBlock;
        }
    }

    /**
     * Checks if all of the blocks of the chain are valid hashes.
     * @return A boolean that represents if the chain is valid or not.
     * @throws NoSuchAlgorithmException if there is an error in getHash.
     */
    public boolean isValidBlockChain() throws NoSuchAlgorithmException {
        Node temp = first;
        int sum = 0;
        while (temp.next != null) {
            if (temp.b.getHash().getData()[0] != 0 || 
                temp.b.getHash().getData()[1] != 0 || 
                temp.b.getHash().getData()[2] != 0) {
                return false;
            }
            sum += temp.b.getAmount();
            if (sum < 0) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    /**
     * Prints to the system the balances for Anna and Bob.
     * @throws NoSuchAlgorithmException if there is an issue in mining. 
     */
    public void printBalances() throws NoSuchAlgorithmException {
        int Bob = 0;
        int Anna = 300;
        if (isValidBlockChain()) {
            Node temp = first;
            while (temp.next != null) {
                if (temp.b.getAmount() < 0) {
                    Anna -= temp.b.getAmount();
                    Bob += temp.b.getAmount();
                } else {
                    Bob -= temp.b.getAmount();
                    Anna += temp.b.getAmount();
                }
            }
        }
        System.out.println("Anna: " + Anna + ",  Bob: " + Bob);
    }

    /**
     * Adds another block onto the end of the block chain.
     * @param blk is the block being added.
     */
    public void append(Block blk) {
        Node Temp = new Node(blk, null);
        last.next = Temp;
        last = Temp;
        size++;
    }

    /**
     * Gets the size of the entire block chain.
     * @return size of block chain.
     */
    public int getSize() {
        return size;
    }

    /**
     * Removes the last block of the chain.
     * @return a boolean representing if the block chain is long enough to successfully remove the
     * last block.
     */
    public boolean removeLast() {
        if (getSize() == 1) {
            return false;
        } else if (getSize() == 2) {
            first.next = null;
            last = first;
            return true;
        }
        Node temp = first;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        last = temp;
        last.next = null;
        return true;
    }

    /**
     * Gets the last hash of the chain.
     * @return the hash of the last block of chain.
     * @throws NoSuchAlgorithmException if there is an error thrown in the hasing process.
     */
    public Hash getHash() throws NoSuchAlgorithmException {
        return last.b.getHash();
    }

    /**
     * Converts the entire block chain to a string representation.
     * @return the block chain as a string.
     */
    public String toString() {
        Node temp = this.first;
        String str = "";
        int link = 0;
        if (temp.next == null) {
            try {
                str += ("Block " + 0 + " (Amount: " + temp.b.getAmount() + ", Nonce: " + temp.b.getNonce() + ", prevHash: " + null + ", hash: " + temp.b.getHashNoPrev() + ")\n");
            } catch(NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return str;
        } else {
            while (link < getSize()) {
                if (link == 0) {
                    try {
                        str += ("Block " + 0 + " (Amount: " + temp.b.getAmount() + ", Nonce: " + temp.b.getNonce() + ", prevHash: " + null + ", hash: " + temp.b.getHashNoPrev() + ")\n");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    link++;
                    temp = temp.next;
                } else {
                    try {
                        str += ("Block " + link + " (Amount: " + temp.b.getAmount() + ", Nonce: " + temp.b.getNonce() + ", prevHash: " + temp.b.getPrevHash() + ", hash: " + temp.b.getHash() + ")\n");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    temp = temp.next;
                    link++;
                }
            }
            return str;
        }
    }

}

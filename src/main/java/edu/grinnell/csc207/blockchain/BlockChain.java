package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {
    // private Block first;
    // private Block last;

    public static class Node {
        public Block b;
        public Node next;

        public Node(Block b, Node next){
            this.b = b;
            this.next = next;
        }
    }

    public Node first;
    public Node last;
    private int size = 0;
    public int amount;

    public BlockChain (int amount) throws NoSuchAlgorithmException{//not sure what is necesary inside this constuctor
        this.first = new Node (mine (amount), null);
        this.last = first;
        this.size++;
        this.amount = amount;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long nonce = 0;
        if (size == 0){
            byte [] bitArr = {1,1,1};
            Hash h = new Hash (bitArr);
            while (bitArr [0] != 0 || bitArr [1] != 0 || bitArr [2] != 0){
                md.reset ();
                md.update (ByteBuffer.allocate(4).putInt(0).array ());// for first block
                md.update (ByteBuffer.allocate(4).putInt(amount).array ()); //for the amount or data in the block dont know how much to allocate
                md.update (ByteBuffer.allocate(8).putLong(nonce).array ());//for the nonce
                bitArr = md.digest();
                nonce++;
                h = new Hash (bitArr);
                
            }
            // System.out.println("base case: " + h);
            nonce--;
            Block nBlock = new Block (0, amount, null, nonce);
            nBlock.setPrevHash (h);
            return nBlock;
        }
        else{
            byte [] bitArr = {1,1,1};
            Hash h = new Hash (bitArr);
            while (bitArr [0] != 0 || bitArr [1] != 0 || bitArr [2] != 0){
                md.reset ();
                md.update (ByteBuffer.allocate(4).putInt(getSize ()).array ());// for first block
                md.update (ByteBuffer.allocate(4).putInt(amount).array ()); //for the amount or data in the block dont know how much to allocate
                md.update (last.b.getHash ().getData());//previous blocks hash
                md.update (ByteBuffer.allocate(8).putLong(nonce).array ());
                bitArr = md.digest();
                nonce++;
                h = new Hash (bitArr);
                // System.out.println(h);
            }
            System.out.println("Non zero case: " + h);
            nonce--;
            Block nBlock = new Block (getSize (), amount, null, nonce);
            // if (getSize () < 2){
            //     nBlock = new Block (getSize (), amount, last.b.getPrevHash (), nonce);
            // }
            // else{
                nBlock = new Block (getSize (), amount, last.b.getHash (), nonce);
                nBlock.setPrevHash (last.b.getHash ());
            
            
            // nBlock.setPrevHash (h);
            return nBlock;
        }
    }

    public boolean isValidBlockChain() throws NoSuchAlgorithmException{
        Node temp = first;
        int sum = 0;
        while (temp.next != null){
            if (temp.b.getHash ().getData () [0]!= 0 || temp.b.getHash ().getData () [1]!= 0 || temp.b.getHash ().getData () [2]!= 0){
                return false;
            }
            sum += temp.b.getAmount ();
            if (sum < 0){
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    public void printBalances() throws NoSuchAlgorithmException{
        int Bob = 0;
        int Anna = 300;
        if (isValidBlockChain ()){
            Node temp = first;
            while (temp.next != null){
                if (temp.b.getAmount () < 0){
                    Anna -= temp.b.getAmount ();
                    Bob += temp.b.getAmount ();
                }
                else{
                    Bob -= temp.b.getAmount ();
                    Anna += temp.b.getAmount ();
                }
            }
        }
        System.out.println("Anna: " + Anna + ",  Bob: " + Bob);
    }

    public void append(Block blk){
        Node Temp = new Node (blk, null);
        last.next = Temp;
        last = Temp;
        size++;
    }

    public int getSize (){
        return size;
    }

    public boolean removeLast(){
        if (getSize () == 1){
            return false;
        }
        if (getSize () == 2){
            first.next = null;
            last = first;
            return true;
        }
        Node temp = first;
        while (temp.next.next != null){
            temp = temp.next;
        }
        last = temp;
        last.next = null;
        return true;
    }

    public Hash getHash() throws NoSuchAlgorithmException{
        return last.b.getHash ();
    }

    public String toString(){
        Node temp = this.first;
        String str = "";
        int link = 0;
        if (temp.next == null){
            try {
                str += ("Block " + 0 + " (Amount: " + temp.b.getAmount () + ", Nonce: " + temp.b.getNonce () + ", prevHash: " + null + ", hash: " + temp.b.getHashNoPrev () + ")\n");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return str;
        }
        else{
            // while (temp.next != null ){
            while (link < getSize()){
                if (link == 0){
                    try {
                        str += ("Block " + 0 + " (Amount: " + temp.b.getAmount () + ", Nonce: " + temp.b.getNonce () + ", prevHash: " + null + ", hash: " + temp.b.getHashNoPrev () + ")\n");
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    link++;
                    temp = temp.next;
                }
                else{
                    try {
                        str += ("Block " + link + " (Amount: " + temp.b.getAmount () + ", Nonce: " + temp.b.getNonce () + ", prevHash: " + temp.b.getPrevHash () + ", hash: " + temp.b.getHash () + ")\n");
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
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

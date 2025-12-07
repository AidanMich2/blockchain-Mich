package edu.grinnell.csc207.blockchain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

public class Tests {   
    @Test
    @DisplayName("Placeholder Test")
    public void placeholderTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("Block constructor")
    public void blockConstructorTest() throws NoSuchAlgorithmException {
        Hash hash = new Hash(new byte[]{0, 0, 0});
        Block block = new Block(1, 50, hash);
        assertEquals(1, block.getNum());
        assertEquals(50, block.getAmount());
        assertEquals(hash, block.getPrevHash());
    }
    
    @Test
    @DisplayName("setPrevHash")
    public void setPrevHashTest() throws NoSuchAlgorithmException {
        Hash hash1 = new Hash(new byte[]{0, 0, 0});
        Hash hash2 = new Hash(new byte[]{1, 1, 1});
        Block block = new Block(1, 50, hash1);
        block.setPrevHash(hash2);
        assertEquals(hash2, block.getPrevHash());
    }
    
    @Test
    @DisplayName("getHashNoPrev")
    public void GetHashNoPrevTest() throws NoSuchAlgorithmException {
        Block block = new Block(0, 50, null, 50);
        assertNotNull(block.getHashNoPrev());
        assertNotNull(block.getHashNoPrev().getData());
    }
    
    @Test
    @DisplayName("Hash isValid")
    public void hashIsValidTrueTest() {
        byte[] data = {0, 0, 0, 1, 1, 1};
        Hash hash = new Hash(data);
        assertTrue(hash.isValid());
    }
    
    @Test
    @DisplayName("Hash isnotValid")
    public void hashIsValidFalseTest() {
        byte[] data = {1, 0, 0, 1, 1, 1};
        Hash hash = new Hash(data);
        assertFalse(hash.isValid());
    }
    
    @Test
    @DisplayName("Hash equals true")
    public void hashEqualsTest() {
        byte[] data = {0, 0, 0, 1, 1, 1};
        Hash hash1 = new Hash(data);
        Hash hash2 = new Hash(data);
        assertTrue(hash1.equals(hash2));
    }
    
    @Test
    @DisplayName("Hash equals false")
    public void hashNotEqualsTest() {
        Hash hash1 = new Hash(new byte[]{0, 0, 0, 1});
        Hash hash2 = new Hash(new byte[]{0, 0, 0, 0});
        assertFalse(hash1.equals(hash2));
    }
    
    @Test
    @DisplayName("BlockChain first block size")
    public void blockChainConstructorSizeTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(50);
        assertEquals(1, bc.getSize());
    }
    
    @Test
    @DisplayName("BlockChain amount")
    public void blockChainAmount() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        Block minedBlock = bc.mine(50);
        assertEquals(50, minedBlock.getAmount());
    }
    
    @Test
    @DisplayName("append increases size")
    public void AppendSizeTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        Block newBlock = bc.mine(50);
        bc.append(newBlock);
        assertEquals(2, bc.getSize());
    }
    
    @Test
    @DisplayName("BlockChain getSize")
    public void blockChainGetSizeTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        bc.append(bc.mine(50));
        bc.append(bc.mine(50));
        assertEquals(3, bc.getSize());
    }
    
    @Test
    @DisplayName("removeLast false")
    public void removeLastfalseTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        assertFalse(bc.removeLast());
    }
    
    @Test
    @DisplayName("removeLast returns true")
    public void removeLasttrueTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        bc.append(bc.mine(50));
        assertTrue(bc.removeLast());
    }
    
    @Test
    @DisplayName("getHash last block")
    public void getHashlastTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        Hash hash = bc.getHash();
        assertEquals(bc.last.b.getHash(), hash);
    }
    
    @Test
    @DisplayName("BlockChain isValidBlockChain true")
    public void blockChainIsValidSingleBlockTest() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        assertTrue(bc.isValidBlockChain());
    }
}

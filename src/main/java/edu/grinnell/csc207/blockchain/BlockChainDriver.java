package edu.grinnell.csc207.blockchain;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException 
     * @throws NumberFormatException 
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        Scanner scan = new Scanner(System.in);
        // System.out.print("Command?");   
        int n = scan.nextInt ();
        System.out.println(n);
        BlockChain bc = new BlockChain ((n));
        boolean running = true;
        System.out.print("HA");
        while (running){
            System.out.print("LOL");
            bc.toString ();
            System.out.print("Command?");
            if (scan.next ().equals ("mine")){
                System.out.println("mine");
                System.out.print ("Amount transferred?");
                int num = 0;
                String str = scan.next ();
                num = Integer.parseInt (str);
                System.out.println(num);
                System.out.println("amount = " + num + ", nonce = " + bc.last.b.getNonce ());
            }
        }
    }  
}

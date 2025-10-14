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
        BlockChain bc = new BlockChain ((n));
        boolean running = true;
        // Block b = new Block (0,0, null, 0);
        while (running){
            System.out.println (bc.toString ());
            System.out.print("Command? ");
            if (scan.next ().equals ("mine")){
                // System.out.println("mine");
                System.out.print ("Amount transferred? ");
                int num = 0;
                String str = scan.next ();
                num = Integer.parseInt (str);
                bc.mine (num);
                // System.out.println(num);
                System.out.println("amount = " + num + ", nonce = " + bc.last.b.getNonce ());
            }
            if (scan.next ().equals ("append")){
                System.out.print ("Amount transferred? ");
                int num = 0;
                String str = scan.next ();
                num = Integer.parseInt (str);
                bc.append (bc.mine (num));
                 System.out.println("Nonce? ");
            }
            if (scan.next ().equals ("remove")){
                if(bc.removeLast()){
                    return;
                }
            }
            if (scan.next ().equals ("check")){
                if(bc.isValidBlockChain()){
                    System.out.println ("Chain is valid!");
                } else{
                    System.out.println ("Chain is invalid!");
                }
            }
            if (scan.next ().equals ("report")){
                bc.printBalances();
            }
            if (scan.next ().equals ("help")){
                System.out.println ("Valid commands:");
                System.out.println ("   mine: discovers the nonce for a given transaction");
                System.out.println ("   append: appends a new block onto the end of the chain");
                System.out.println ("   remove: removes the last block from the end of the chain");
                System.out.println ("   check: checks that the block chain is valid");
                System.out.println ("   report: reports the balances of Alice and Bob");
                System.out.println ("   help: prints this list of commands");
                System.out.println ("   quit: quits the program");
            }
            if (scan.next ().equals ("quit")){
                running = false;
            }
        }
    }  
}

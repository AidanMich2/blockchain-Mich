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
        // int n = scan.nextInt ();
        String firstinput = scan.next();
        BlockChain bc = new BlockChain (Integer.parseInt(firstinput));
        boolean running = true;
        // Block b = new Block (0,0, null, 0);
        while (running){
            System.out.println (bc.toString ());
            System.out.print("Command? ");
            String input = scan.next();
            if (input.equals ("mine")){
                // System.out.println("mine");
                System.out.print ("Amount transferred? ");
                int num = 0;
                String str = scan.next ();
                num = Integer.parseInt (str);
                bc.mine (num);
                // System.out.println(num);
                System.out.println("amount = " + num + ", nonce = " + bc.last.b.getNonce ());
            }
            if (input.equals ("append")){
                System.out.print ("Amount transferred? ");
                int num = 0;
                String str = scan.next ();
                num = Integer.parseInt (str);
                bc.append (bc.mine (num));
                 System.out.println("Nonce? ");
            }
            if (input.equals ("remove")){
                if(bc.removeLast() == false){
                    return;
                }
            }
            if (input.equals ("check")){
                if(bc.isValidBlockChain()){
                    System.out.println ("Chain is valid!");
                } else{
                    System.out.println ("Chain is invalid!");
                }
            }
            if (input.equals ("report")){
                bc.printBalances();
            }
            if (input.equals ("help")){
                System.out.println ("Valid commands:");
                System.out.println ("   mine: discovers the nonce for a given transaction");
                System.out.println ("   append: appends a new block onto the end of the chain");
                System.out.println ("   remove: removes the last block from the end of the chain");
                System.out.println ("   check: checks that the block chain is valid");
                System.out.println ("   report: reports the balances of Alice and Bob");
                System.out.println ("   help: prints this list of commands");
                System.out.println ("   quit: quits the program");
            }
            if (input.equals ("quit")){
                running = false;
            }
        }
    }  
}

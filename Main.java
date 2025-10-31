import java.util.Scanner;
import java.io.File;
import Domain.Parser;

public class Main {
    public static void main(String[] args){
        boolean exit = false;
        boolean error = true;
        Scanner sc = new Scanner(System.in);
        String filename = "";
        while(!exit){
            error = true;
            while(error){
                System.out.println("\nOnly .txt file will work. Do not include a file extension!\n");
                System.out.print("Input name of file to parse (Enter \"exit\" to exit): ");

               /*************************************
                * supposed to get filename from command line
                * Reply: I am a little confused about this feedback. The current implementation gets 
                *           the filename from the command line - specifically, on line 23.
                */
               try{
                    filename = sc.nextLine().trim();
                    error = false;

                } catch(Exception e){
                    System.err.print(e);
                    error = true;
                }
            }

            if(filename.toLowerCase().trim().equals("exit")){
                        exit = true;
                        continue;
                    }
            
            File f = new File(filename+".txt");
            if(!f.exists()){
                System.err.println("\nFile does not exist.\n Make sure to not include the \".txt\" part.");
                continue;
            }

            if(exit){
                break;
            }

            Parser p = new Parser(filename+".txt");
            p.parse();
        }
        System.out.println("Exiting...");
        sc.close();
    }
}
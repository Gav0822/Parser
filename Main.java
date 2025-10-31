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
                try{
                    filename = sc.nextLine().trim();

                    if(filename.toLowerCase().trim().equals("exit"))
                        exit = true;

                    error = false;

                } catch(Exception e){
                    System.err.print(e);
                    error = true;
                }
            }
            
            File f = new File(filename+".txt");
            if(!f.exists()){
                System.err.println("\nFile does not exist.\n Make sure to not include the \".txt\" part.");
                continue;
            }

            if(exit){
                break;
            }
            //Parser p = new Parser("tst.txt");
            Parser p = new Parser(filename+".txt");
            p.parse();
        }
        System.out.println("Exiting...");
        sc.close();
    }
}

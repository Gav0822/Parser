package FileIO;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class FileIO {
    
    private String file_name;
    private String contents;
    private ArrayList<String> lines;
    private int num_of_lines = 0;

    public FileIO() { };

    public FileIO(String newFileName){

        try {
            
            Scanner sc = new Scanner(new File(newFileName));
            StringBuilder rawContent = new StringBuilder();
            lines = new ArrayList<>();

            for(this.num_of_lines = 0; sc.hasNextLine(); this.num_of_lines++){

                String tmp = sc.nextLine();
                this.lines.add(tmp);
                rawContent.append(tmp);
            }

            this.setFileName(newFileName);
            this.contents = rawContent.toString();
            sc.close();

        } catch(Exception e) {
            System.err.println("File not found: " + e);
        }
    }

    public String getContents() {
        return this.contents;
    }

    public String[] getSentences(){
        return this.lines.toArray(new String[]{});
    }

    private void setFileName(String newFileName){
        this.file_name = newFileName;
    }

    public String getFileName(){
        return this.file_name;
    }

    public int numberOfLines(){
        return this.num_of_lines;
    }

    public String getLine(int lineNumber){
        if(this.num_of_lines == 0){
            System.err.println("File does not exist.");
            return null;
        }

        return this.lines.get(lineNumber);
    }

    public int getMaxLineNum(){
        return this.num_of_lines;
    }
}

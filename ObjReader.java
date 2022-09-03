import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ObjReader {
    public static String file;
    public ObjReader(String f){
        this.file = f;
    }

    public static double[][] readVerts(String[] args) {
        try {
            File f = new File(file);
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                System.out.println(data);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return new double[1][1];
    }
}
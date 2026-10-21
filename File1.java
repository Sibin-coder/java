import java.util.*;
import java.io.*;
public class File1 {
    public static void main(String []args)
    {
        int wordcount=0;
        int vowels=0;
        int linecount=0;
        int ch=0;
        Scanner sc = new Scanner(System.in);
        try{
            BufferedReader br=new BufferedReader(new FileReader("b.txt"));
            String line;
            while((line=br.readLine())!=null)
            {
                 line=line.trim();
                 if(line.isEmpty()) continue;
                 linecount++;
                 wordcount+=line.trim().split("\\s+").length;
                 char c;
                 for(int i=0;i<line.length();i++)
                 {
                    if(Character.isLetterOrDigit(line.charAt(i)))
                    {
                        ch++;
                        line=line.toLowerCase();
                        c=line.charAt(i);
                        
                        if(c=='a'||c=='e'||c=='i'||c=='o'||c=='u')
                        {
                            vowels++;
                        }
                    }

                 }
            }
               
                 
            System.out.println("Number of lines: "+linecount);
            System.out.println("Number of words: "+wordcount);
            System.out.println("Number of characters: "+ch);
            System.out.println("Number of vowels: "+vowels);
            BufferedWriter bw=new BufferedWriter(new FileWriter("c.txt"));
            bw.write("Number of lines: "+linecount);
            bw.write("\nNumber of words: "+wordcount);
            bw.write("\nNumber of characters: "+ch);
            bw.write("\nNumber of vowels: "+vowels);
            bw.close();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

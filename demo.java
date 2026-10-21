import java.io.*;

class Student implements Serializable
{
    int i;
    String s;
    private static final long serialVersionUID = 1L;
    Student(int i, String s)
    {
        this.i=i;
        this.s=s;
    }
}
public class demo
{
    public static void main(String args[])
    {
        Student a1 =new Student(1,"sibin");
        Student a2 =new Student(2,"asdsdf");
        try{
            FileOutputStream fo =new FileOutputStream("a.txt");
            ObjectOutputStream out =new ObjectOutputStream(fo);
            out.writeObject(a1);
            out.writeObject(a2);
            out.close();
            fo.close();
        }
        catch(IOException exception)
        {
            System.out.println("eoor");

        }
    }
}
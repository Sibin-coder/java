import java.util.Scanner;
import java.io.*;

public class File {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of employees: ");
        int n = sc.nextInt();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("employee.txt"));
            for (int i = 0; i < n; i++) {
                System.out.print("Enter the name of the employee: ");
                bw.write(sc.next() + " ");
                System.out.print("Basic salary of the employee: ");
                bw.write(sc.next());
                bw.newLine();
            }
            bw.close();
            BufferedReader br = new BufferedReader(new FileReader("employee.txt"));
            String line;
            int i = 0;
            String[][] emp = new String[n][5];
            double max = Double.MIN_VALUE;
            int no = 0;

            while ((line = br.readLine()) != null) {
                String[] words = line.trim().split("\\s+");
                double basic = Double.parseDouble(words[1]);
                double hra = basic * 0.2;
                double da = basic * 0.1;
                double total = basic + hra + da;

                emp[i][0] = words[0];
                emp[i][1] = String.valueOf(basic);
                emp[i][2] = String.valueOf(hra);
                emp[i][3] = String.valueOf(da);
                emp[i][4] = String.valueOf(total);

                if (total > max) {
                    max = total;
                    no = i;
                }
                i++;
            }
            br.close();
            BufferedWriter pw = new BufferedWriter(new FileWriter("processed_employee.txt"));
            String header = String.format("%-20s%15s%15s%15s%15s",
                    "Name", "Basic Salary", "HRA", "DA", "Total Salary");
            pw.write(header);
            pw.newLine();

            System.out.println();
            System.out.println(header);

            for (int j = 0; j < emp.length; j++) {
                String row = String.format("%-20s%15s%15s%15s%15s",
                        emp[j][0], emp[j][1], emp[j][2], emp[j][3], emp[j][4]);
                pw.write(row);
                pw.newLine();
                System.out.println(row);
            }
            
            String result = "Employee with the highest total salary: " + emp[no][0];
            pw.newLine();
            pw.write(result);
            pw.newLine();
            pw.close();

            System.out.println(result);

        } catch (Exception e) {
            System.out.println(e);
        }
        sc.close();
    }
}
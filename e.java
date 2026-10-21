public import java.sql.*;
import java.util.Scanner; 
 
public class e { 
 
 private static final String URL = 
 "jdbc:mysql://localhost:3306/employee_db";  private static final String USER = "root";  private static final String PASSWORD = "breakingbad"; 
 public static void main(String[] args) { 
 Scanner sc = new Scanner(System.in); 
 try { 
 Class.forName("com.mysql.cj.jdbc.Driver"); 
 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) { 
 System.out.println("Connected to employee_db successfully."); 
 createTable(con); 
 insertSampleEmployees(con); 
int choice; 
do { 
System.out.println("\n========== EMPLOYEE PAYROLL SYSTEM =========="); 
System.out.println("1. Insert Employee"); 
System.out.println("2. Display Employees"); 
System.out.println("3. Display Employees Earning More Than ₹50,000"); 
System.out.println("4. Increase IT Employees' Salary by 10%"); 
System.out.println("5. Delete Employees Without Department"); 
 System.out.println("6. Update Employee"); 
 System.out.println("7. Delete Employee"); 
 System.out.println("8. Exit"); 
 System.out.print("Enter your choice: "); 
 
 choice = sc.nextInt();  sc.nextLine(); 
 
 switch (choice) {  case 1:  insertEmployee(con, sc);  break;  case 2: 
 displayEmployees(con);  break;  case 3: 
 displayHighSalaryEmployees(con);  break;  case 4:  increaseITSalary(con);  break; case 5: 
deleteUnassignedEmployees(con); break; case 6: 
updateEmployee(con, sc); 
break; case 7: deleteEmployee(con, sc); break; case 8: 
 System.out.println("Exiting program...");  break;  default: 
 System.out.println("Invalid choice!"); 
 } 
 
 } while (choice != 8); 
 } 
 
 } catch (ClassNotFoundException e) { 
 System.out.println("MySQL JDBC Driver not found."); 
 e.printStackTrace(); 
 
 } catch (SQLException e) { 
 System.out.println("Database error occurred."); 
 e.printStackTrace(); 
 
 } finally {  sc.close(); 
 } 
 } 
private static void createTable(Connection con) throws SQLException { 
String sql = "CREATE TABLE IF NOT EXISTS employee (" + 
"emp_id INT PRIMARY KEY, " + 
"emp_name VARCHAR(100) NOT NULL, " + 
"designation VARCHAR(100), " + 
"salary DECIMAL(10,2), " + 
"department VARCHAR(100))";   
 try (PreparedStatement ps = con.prepareStatement(sql)) {  ps.executeUpdate(); 
 } 
 } 
 
 private static void insertSampleEmployees(Connection con)  throws SQLException { 
 
 String sql = "INSERT IGNORE INTO employee " + 
 "(emp_id, emp_name, designation, salary, department) " + 
 "VALUES (?, ?, ?, ?, ?)"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
 Object[][] employees = { 
 {101, "Arun Kumar", "Software Engineer", 65000.0, "IT"}, 
 {102, "Priya Singh", "HR Manager", 55000.0, "HR"}, 
 {103, "Ravi Kumar", "Developer", 48000.0, "IT"}, 
 {104, "Meena Devi", "Accountant", 60000.0, "Finance"}, 
 {105, "Karthik Raj", "Intern", 30000.0, null} 
 }; 
for (Object[] emp : employees) { ps.setInt(1, (Integer) emp[0]); ps.setString(2, (String) emp[1]); ps.setString(3, (String) emp[2]); ps.setDouble(4, (Double) emp[3]); ps.setString(5, (String) emp[4]); ps.executeUpdate(); 
} 
} 
 } 
 
 private static void insertEmployee(Connection con, Scanner sc)  throws SQLException { 
 
 String sql = "INSERT INTO employee " + 
 "(emp_id, emp_name, designation, salary, department) " + 
 "VALUES (?, ?, ?, ?, ?)"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
 System.out.print("Enter Employee ID: ");  int id = sc.nextInt();  sc.nextLine(); 
 
 System.out.print("Enter Employee Name: "); 
 String name = sc.nextLine(); 
 
 System.out.print("Enter Designation: "); 
 String designation = sc.nextLine(); 
 
System.out.print("Enter Salary: "); double salary = sc.nextDouble(); sc.nextLine(); 
System.out.print("Enter Department: "); 
String department = sc.nextLine(); 
ps.setInt(1, id); ps.setString(2, name); ps.setString(3, designation);  ps.setDouble(4, salary);  ps.setString(5, department); 
 
 int r=ps.executeUpdate(); 
 if(r>0)System.out.println("Employee inserted successfully.");
 else System.out.println("Failed to insert employee."); 
 } 
 } 
 private static void displayEmployees(Connection con)  throws SQLException { 
 
 String sql = "SELECT emp_id, emp_name, designation, salary, department " + 
 "FROM employee"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql); 
 ResultSet rs = ps.executeQuery()) { 
 
 System.out.println("\n---------------------------------------------------------------"); 
 System.out.printf("%-8s %-20s %-18s %-12s %-15s%n", 
 "ID", "Name", "Designation", "Salary", "Department"); 
System.out.println("---------------------------------------------------------------"); 
while (rs.next()) { 
System.out.printf("%-8d %-20s %-18s %-12.2f %-15s%n", rs.getInt("emp_id"), 
rs.getString("emp_name"), rs.getString("designation"), rs.getDouble("salary"), rs.getString("department")); 
} 
 
 System.out.println("---------------------------------------------------------------"); 
 } 
 } 
 
 private static void displayHighSalaryEmployees(Connection con)  throws SQLException { 
 
 String sql = "SELECT emp_id, emp_name, designation, salary, department " + 
 "FROM employee WHERE salary >50000"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
//  ps.setDouble(1, 50000); 
 
 try (ResultSet rs = ps.executeQuery()) { 
 
 System.out.println("\nEmployees earning more than ₹50,000:"); 
 
 while (rs.next()) { 
 System.out.printf( 
"ID: %d | Name: %s | Designation: %s | " + "Salary: ₹%.2f | Department: %s%n", rs.getInt("emp_id"), rs.getString("emp_name"), rs.getString("designation"), rs.getDouble("salary"), rs.getString("department")); 
} 
} 
} 
 } 
 
 private static void increaseITSalary(Connection con)  throws SQLException { 
 
 String sql = "UPDATE employee SET salary = salary * ? " + 
 "WHERE department = ?"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
 ps.setDouble(1, 1.10);  ps.setString(2, "IT"); 
 
 int rows = ps.executeUpdate(); 
 
 System.out.println(rows + 
 " IT employee(s) received a 10% salary increase."); 
 } 
 } 
 
 private static void deleteUnassignedEmployees(Connection con) throws SQLException { 
String sql = "DELETE FROM employee " + 
"WHERE department IS NULL OR TRIM(department) = ''"; 
try (PreparedStatement ps = con.prepareStatement(sql)) { 
int rows = ps.executeUpdate(); 
System.out.println(rows + 
 " employee(s) without a department deleted."); 
 } 
 } 
 
 private static void updateEmployee(Connection con, Scanner sc)  throws SQLException { 
 
 String sql = "UPDATE employee SET emp_name = ?, " + 
 "designation = ?, salary = ?, department = ? " + 
 "WHERE emp_id = ?"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
 System.out.print("Enter Employee ID to update: ");  int id = sc.nextInt();  sc.nextLine(); 
 
 System.out.print("Enter new Employee Name: "); 
 String name = sc.nextLine(); 
 
 System.out.print("Enter new Designation: "); 
String designation = sc.nextLine(); 
System.out.print("Enter new Salary: "); double salary = sc.nextDouble(); sc.nextLine(); 
System.out.print("Enter new Department: "); String department = sc.nextLine(); 
ps.setString(1, name);  ps.setString(2, designation);  ps.setDouble(3, salary);  ps.setString(4, department);  ps.setInt(5, id); 
 
 int rows = ps.executeUpdate(); 
 
 if (rows > 0) 
 System.out.println("Employee updated successfully."); 
 else 
 System.out.println("Employee not found."); 
 } 
 } 
 
 private static void deleteEmployee(Connection con, Scanner sc)  throws SQLException { 
 
 String sql = "DELETE FROM employee WHERE emp_id = ?"; 
 
 try (PreparedStatement ps = con.prepareStatement(sql)) { 
 
System.out.print("Enter Employee ID to delete: "); int id = sc.nextInt(); 
ps.setInt(1, id); 
int rows = ps.executeUpdate(); 
if (rows > 0) 
System.out.println("Employee deleted successfully."); else 
 System.out.println("Employee not found."); 
 } 
 } 
} {
    
}

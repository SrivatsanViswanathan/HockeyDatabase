import java.sql.*;
import java.util.*;

public class App {

  public static void main(String[] args) throws Exception {
    FrontEnd test = new FrontEnd();
    test.CommandGUI();
    /* 
    System.out.println("Welcome to Hockey Database");
    Scanner sc = new Scanner(System.in);  
    String command = "";
    int option = 0;
    Connection c = null;
    Statement stmt = null;
    while (option != 100) {
      System.out.println("\nHere are the list of things you can do: (Select a number, 100 to quit)");
      System.out.println("1. See Tables");
      System.out.println("2. Update Tables (INSERT, ADD, DELETE)");
      try {
        option = sc.nextInt();
        sc.nextLine();
        if (option == 100) {
          System.out.println("\nExiting...");
          break;
        }
        if (option != 1 && option != 2 ) {
            throw new InputMismatchException();
        }
      } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please try again.");
          sc.nextLine();
      }
      try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
      }
      catch ( Exception e ) {
        System.err.println("Problem Encountered");
      }
      if (option == 1) {
        while (true) {
          System.out.println("\nList of tables are below\n");
          try {
            stmt = c.createStatement();
            ResultSet rs = c.getMetaData().getTables(null, null, "%", null);
            for (int i = 1; rs.next(); i++) {
              String tableName = rs.getString("TABLE_NAME");
              if (!tableName.equals("sqlite_autoindex_Team_1") && !tableName.equals("sqlite_schema") && !tableName.equals("sqlite_sequence")) {
              System.out.println((i - 3) + ". " + tableName);
              }
            }
          }
          catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
          System.out.println("Enter the command to see the table (q to quit)");
          command = sc.nextLine();
          //display(command);
          if (command.equals("q")) {
            break;
          }
          seeTables(command);
          option = 0;
        }
      }
      else if (option == 2) {
        while (true) {
          System.out.println("\nList of tables are below\n");
          try {
            stmt = c.createStatement();
            ResultSet rs = c.getMetaData().getTables(null, null, "%", null);
            for (int i = 1; rs.next(); i++) {
              String tableName = rs.getString("TABLE_NAME");
              if (!tableName.equals("sqlite_autoindex_Team_1") && !tableName.equals("sqlite_schema") && !tableName.equals("sqlite_sequence")) {
              System.out.println((i - 3) + ". " + tableName);
              }
            }
          }
          catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
          System.out.println("Enter the command to update the table (q to quit)");
          command = sc.nextLine();
          //display(command);
          if (command.equals("q")) {
            break;
          }
          updateTables(command);
          option = 0;
        }
      }
    stmt.close();
    c.close();
    }
    sc.close();
    */
  }
  /* 
  public static void seeTables (String command) {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    }
    catch ( Exception e ) {
      System.err.println("Problem Encountered");
    }
    System.out.println("\nOpened database successfully\n");
  
    try {
      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery(command);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numColumns = rsmd.getColumnCount();
  
      String format = "%-19s";
      for (int i = 1; i <= numColumns; i++) {
        System.out.format(format, rsmd.getColumnName(i));
      }
      System.out.println();
  
      while (rs.next()) {
        for (int i = 1; i <= numColumns; i++) {
          System.out.format(format, rs.getString(i));
        }
        System.out.println();
      }
    stmt.close();
    c.close();
    System.out.println("Table loaded successfully");
    }
    catch (Exception e) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage());
      System.out.println("Try Again:");
    }
  }
  */
  /* 
  public static void updateTables (String command) {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    }
    catch ( Exception e ) {
      System.err.println("Problem Encountered");
    }
    System.out.println("\nOpened database successfully\n");

    try {
      String rs = command;
      stmt = c.createStatement();
      stmt.executeUpdate(rs);
      stmt.close();
      c.close();
      System.out.println("Table updated successfully");
    }
    catch (Exception e) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage());
      System.out.println("Try Again:");
    }
  }
  */
}

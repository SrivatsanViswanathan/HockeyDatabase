import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class FrontEnd extends JFrame {

  public void CommandGUI() {
    JFrame frame = new JFrame("Hockey Database");
    frame.setSize(400, 266);

    ImageIcon imageIcon = new ImageIcon(
      "JavaTestVSCode\\src\\images\\leafs.png"
    );
    JLabel background = new JLabel(imageIcon);
    JLabel header = new JLabel("Welcome to Hockey Database");
    JLabel title = new JLabel("Here are the list of things you can do:");
    JLabel option1 = new JLabel("1. See Tables");
    JLabel option2 = new JLabel("2. Update Tables (INSERT, ADD, DELETE)");
    JLabel error = new JLabel("A mistake may have been made. Try Again.");

    JTextField textField = new JTextField();

    JButton submit = new JButton("Submit");

    header.setFont(new Font("Arial", Font.BOLD, 16));

    header.setBounds(80, 10, 300, 20);
    title.setBounds(80, 40, 300, 20);
    option1.setBounds(80, 70, 300, 20);
    option2.setBounds(80, 100, 300, 20);
    textField.setBounds(125, 125, 150, 20);
    error.setBounds(80, 145, 300, 20);
    submit.setBounds(150, 170, 100, 20);

    error.setForeground(Color.RED);

    submit.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String input = textField.getText();
          if (input.equals("1") || input.equalsIgnoreCase("See Tables")) {
            frame.remove(error);
            listTables("1");
          } else if (
            input.equals("2") || input.equalsIgnoreCase("Update Tables")
          ) {
            frame.remove(error);
            listTables("2");
          } else {
            frame.add(error);
            frame.revalidate();
            frame.repaint();
          }
        }
      }
    );

    frame.setContentPane(background);
    frame.add(header);
    frame.add(title);
    frame.add(option1);
    frame.add(option2);
    frame.add(textField);
    frame.add(submit);

    frame.setLayout(null);
    frame.setVisible(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void listTables(String command) {
    Connection c = null;
    Statement stmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    } catch (Exception e) {
      System.err.println("Problem Encountered");
    }

    JFrame frame = new JFrame();

    if (command.equals("1")) {
      frame.setTitle("See Tables");
    }
    if (command.equals("2")) {
      frame.setTitle("Update Tables");
    }
    JLabel header = new JLabel("List of Tables:");
    JLabel[] labels = new JLabel[100];
    JLabel error = new JLabel("");

    JTextArea textArea = new JTextArea(5, 20);
    JScrollPane scrollPane = new JScrollPane(textArea);
    frame.getContentPane().add(scrollPane);

    JButton submit = new JButton("Submit");
    JButton back = new JButton("Back");

    int count = 0;
    try {
      stmt = c.createStatement();
      ResultSet rs = c.getMetaData().getTables(null, null, "%", null);
      for (int i = 1; rs.next(); i++) {
        count++;
        String tableName = rs.getString("TABLE_NAME");
        if (
          !tableName.equals("sqlite_autoindex_Team_1") &&
          !tableName.equals("sqlite_schema") &&
          !tableName.equals("sqlite_sequence")
        ) {
          labels[i] = new JLabel((i - 3) + ". " + tableName);
          labels[i].setBounds(80, i * 20 - 20, 150, 20);
          frame.add(labels[i]);
        }
      }
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    frame.setSize(700, count * 30);

    header.setBounds(80, 20, 150, 20);
    textArea.setBounds(250, 65, 300, 100);
    error.setBounds(185, 170, 500, 20);
    submit.setBounds(350, 195, 100, 20);
    back.setBounds(565, 20, 100, 20);

    header.setFont(new Font("Arial", Font.BOLD, 16));

    error.setForeground(Color.RED);

    submit.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String input = textArea.getText();
          System.out.println(input);
          String result = checkError(input);
          if (
            !result.equals("Success") &&
            !result.equals("query does not return ResultSet")
          ) {
            error.setText(result);
            System.out.println(result);
            frame.add(error);
            frame.revalidate();
            frame.repaint();
          } else {
            if (command.equals("1")) {
              displayTable(input);
            } else if (command.equals("2")) {
              updateTable(input);
              frame.setVisible(false);
              listTables(command);
            }
          }
        }
      }
    );

    back.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          frame.setVisible(false);
        }
      }
    );

    //frame.setContentPane(background);
    frame.add(header);
    frame.add(textArea);
    frame.add(submit);
    frame.add(back);

    frame.setLayout(null);
    frame.setVisible(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void displayTable(String input) {
    Connection c = null;
    Statement stmt = null;
    JFrame frame = new JFrame("Table");
    JLabel[] labels = new JLabel[100];

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    } catch (Exception e) {
      System.err.println("Problem Encountered");
    }

    try {
      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery(input);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numColumns = rsmd.getColumnCount();

      String format = "%-19s";
      for (int i = 1; i <= numColumns; i++) {
        labels[i] = new JLabel(rsmd.getColumnName(i));
        labels[i].setBounds((i - 1) * 128 + 10, 15, 150, 20);
        frame.add(labels[i]);
        System.out.format(format, rsmd.getColumnName(i));
      }
      System.out.println();

      int j = 35;
      int count = 0;
      while (rs.next()) {
        for (int i = 1; i <= numColumns; i++) {
          labels[i] = new JLabel(rs.getString(i));
          if (rs.getString(i) == null) {
            labels[i].setText("N/A");
          }
          labels[i].setBounds((i - 1) * 128 + 10, j, 150, 20);
          frame.add(labels[i]);
          System.out.format(format, rs.getString(i));
        }
        j = j + 20;
        count++;
        System.out.println();
      }

      frame.setSize(numColumns * 128 + 20, count * 20 + 75);
      stmt.close();
      c.close();
      System.out.println("Table loaded successfully");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.out.println("Try Again:");
    }

    frame.setLayout(null);
    frame.setVisible(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void updateTable(String input) {
    Connection c = null;
    Statement stmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    } catch (Exception e) {
      System.err.println("Problem Encountered");
    }

    try {
      String rs = input;
      stmt = c.createStatement();
      stmt.executeUpdate(rs);
      stmt.close();
      c.close();
      System.out.println("Table updated successfully");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.out.println("Try Again:");
    }
  }

  public String checkError(String input) {
    Connection c = null;
    Statement stmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:HockeyDatabase.db");
    } catch (Exception e) {
      System.err.println("Problem Encountered");
    }

    try {
      stmt = c.createStatement();
      stmt.close();
      c.close();
      System.out.println("Table loaded successfully");
      return "Success";
    } catch (Exception e) {
      String error = (e.getMessage());
      if (!error.equals("query does not return ResultSet")) {
        System.err.println(error);
      }
      return error;
    }
  }
}

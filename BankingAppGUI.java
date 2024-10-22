
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class BankingAppGUI extends JFrame {

    private static Connection mydb;



    public static class RoundedButton extends JButton {

        private static final Color BUTTON_COLOR = new Color(0xFFBC59); // #ffbc59 color

        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(Color.BLACK); // Set text color
            setFont(new Font("Arial", Font.BOLD, 16)); // Set font size if desired
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded background
            g2.setColor(BUTTON_COLOR);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 30 is the corner radius

            // Draw the button text
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
        }
    }

    public class BackgroundPanel extends JPanel {

        private BufferedImage backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                // Convert the file path to a URL
                URL url = new URL(imagePath);
                // Load the image from the URL
                backgroundImage = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Use high-quality rendering hints
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Draw the image scaled to the panel size
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }

    public BankingAppGUI() {

        // Establish database connection
        try {
            mydb = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankproject", "root", "1234567890");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Start of Graphics Portion
        {
            // Set up the main frame
            setTitle("Swift Bank");
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
            setUndecorated(true); // Optional: Hide window decorations for a fullscreen effect
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Create a BackgroundPanel with the URL to your image
            BackgroundPanel backgroundPanel = new BackgroundPanel("file:/C:/Users/aryan/IdeaProjects/bank management system/Bank_Project_Background_Image.png");

            // Create a panel with GridBagLayout to align buttons
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setOpaque(false); // Make the panel transparent

            // Create rounded buttons
            RoundedButton btnCreateAccount = new RoundedButton("Open a New Account");
            RoundedButton btnModifyAccount = new RoundedButton("Modify Account");
            RoundedButton btnViewAccount = new RoundedButton("View Account Details");
            RoundedButton btnDeposit = new RoundedButton("Deposit");
            RoundedButton btnWithdraw = new RoundedButton("Withdraw");
            RoundedButton btnExit = new RoundedButton("Exit");

            // Set preferred size for buttons
            Dimension buttonSize = new Dimension(250, 50); // Increased button size
            btnCreateAccount.setPreferredSize(buttonSize);
            btnModifyAccount.setPreferredSize(buttonSize);
            btnViewAccount.setPreferredSize(buttonSize);
            btnDeposit.setPreferredSize(buttonSize);
            btnWithdraw.setPreferredSize(buttonSize);
            btnExit.setPreferredSize(buttonSize);

            // Configure GridBagConstraints for button positioning
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.CENTER; // Center vertically
            gbc.anchor = GridBagConstraints.WEST; // Align to the left
            gbc.insets = new Insets(10, 70, 10, 10); // Padding around buttons

            // Add buttons to the panel
            panel.add(btnCreateAccount, gbc);
            gbc.gridy++;
            panel.add(btnModifyAccount, gbc);
            gbc.gridy++;
            panel.add(btnViewAccount, gbc);
            gbc.gridy++;
            panel.add(btnDeposit, gbc);
            gbc.gridy++;
            panel.add(btnWithdraw, gbc);
            gbc.gridy++;
            panel.add(btnExit, gbc);

            // Add panel to the background panel
            backgroundPanel.setLayout(new BorderLayout());
            backgroundPanel.add(panel, BorderLayout.WEST); // Align panel to the left
            // Add background panel to the frame
            add(backgroundPanel);
            // Add action listeners
            btnCreateAccount.addActionListener(e -> createAccount());
            btnModifyAccount.addActionListener(e -> modifyAccount());
            btnViewAccount.addActionListener(e -> viewAccountDetails());
            btnDeposit.addActionListener(e -> deposit());
            btnWithdraw.addActionListener(e -> withdraw());
            btnExit.addActionListener(e -> System.exit(0));
        }
    }//End Brackets of Graphics Portion


    private static final int INTEGER_ENCRYPTION_KEY = 1234;  // Example key for integer encryption

    // Encrypt string data by increasing ASCII value
    private String encryptData (String input){
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            encrypted.append((char) (c + 1));  // Increase ASCII value by 1
        }
        return encrypted.toString();
    }

    // Decrypt string data by decreasing ASCII value
    private String decryptData (String input){
        StringBuilder decrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            decrypted.append((char) (c - 1));  // Decrease ASCII value by 1
        }
        return decrypted.toString();
    }

    // Encrypt integer data
    private int encryptInt ( int value){
        return value + INTEGER_ENCRYPTION_KEY;  // Add a constant value for encryption
    }

    // Decrypt integer data
    private int decryptInt ( int value){
        return value - INTEGER_ENCRYPTION_KEY;  // Subtract the constant value for decryption
    }

    private void createAccount() {
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField aadharField = new JTextField(20);
        JTextField balanceField = new JTextField(20);
        JTextField pinField = new JTextField(20);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(8, 2, 10, 10));  // GridLayout with gaps between fields
        myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around the panel
        myPanel.add(new JLabel("Name:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Address:"));
        myPanel.add(addressField);
        myPanel.add(new JLabel("Phone:"));
        myPanel.add(phoneField);
        myPanel.add(new JLabel("Email:"));
        myPanel.add(emailField);
        myPanel.add(new JLabel("Aadhar No:"));
        myPanel.add(aadharField);
        myPanel.add(new JLabel("Opening Balance:"));
        myPanel.add(balanceField);
        myPanel.add(new JLabel("PIN:"));
        myPanel.add(pinField);

        // Add key listener to handle Enter key
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JButton okButton = (JButton) ((JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, myPanel)).getRootPane().getDefaultButton();
                    if (okButton != null) {
                        okButton.doClick();
                    }
                }
            }
        };

        nameField.addKeyListener(keyAdapter);
        addressField.addKeyListener(keyAdapter);
        phoneField.addKeyListener(keyAdapter);
        emailField.addKeyListener(keyAdapter);
        aadharField.addKeyListener(keyAdapter);
        balanceField.addKeyListener(keyAdapter);
        pinField.addKeyListener(keyAdapter);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter Account Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = encryptData(nameField.getText());
                String address = encryptData(addressField.getText());
                String phone = encryptData(phoneField.getText());
                String email = encryptData(emailField.getText());
                String aadhar = encryptData(aadharField.getText());
                int balance = Integer.parseInt(balanceField.getText());
                int pin = Integer.parseInt(pinField.getText());

                // Encrypt the integer values
                int encryptedBalance = encryptInt(balance);
                int encryptedPin = encryptInt(pin);

                String sql = "INSERT INTO customer(name, address, phone, email, aadhar_no, status, balance, pin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = mydb.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setString(2, address);
                pstmt.setString(3, phone);
                pstmt.setString(4, email);
                pstmt.setString(5, aadhar);
                //pstmt.setString(6, accountType);
                pstmt.setString(6, "active");
                pstmt.setInt(7, encryptedBalance);
                pstmt.setInt(8, encryptedPin);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int acno = rs.getInt(1);
                    JOptionPane.showMessageDialog(this, "Account created successfully!\nYour Account number is: " + acno);
                }
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating account.");
            }
        }
    }

    private void modifyAccount() {
        String acno = JOptionPane.showInputDialog(this, "Enter Account Number:");

        if (acno != null && !acno.isEmpty()) {
            try {
                String sql2 = "SELECT * FROM customer WHERE acno=?";
                PreparedStatement pstmt2 = mydb.prepareStatement(sql2);
                pstmt2.setInt(1, Integer.parseInt(acno));
                ResultSet rs = pstmt2.executeQuery();

                if (rs.next()) {
                    // Account exists, proceed with PIN verification
                    String pinInput = JOptionPane.showInputDialog(this, "Enter PIN:");
                    int enteredPin = Integer.parseInt(pinInput);

                    int storedPin = rs.getInt("pin");
                    int decryptedPin = decryptInt(storedPin);  // Decrypt the stored PIN

                    // Step 2: Check if the PIN is correct
                    if (enteredPin == decryptedPin) {
                        String[] options = {"Name", "Address", "Phone", "Email", "PIN"};
                        String field = (String) JOptionPane.showInputDialog(this, "What do you want to change?", "Modify Account", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (field != null) {
                            String newValue = JOptionPane.showInputDialog(this, "Enter new " + field + ":");

                            if (newValue != null && !newValue.isEmpty()) {
                                if (field.equals("PIN")) {
                                    // Encrypt the new PIN before updating
                                    int newPin = Integer.parseInt(newValue);
                                    newValue = String.valueOf(encryptInt(newPin));
                                } else {
                                    // Encrypt other fields
                                    newValue = encryptData(newValue);
                                }

                                String sql = "UPDATE customer SET " + field.toLowerCase() + "=? WHERE acno=?";
                                PreparedStatement pstmt = mydb.prepareStatement(sql);
                                pstmt.setString(1, newValue);
                                pstmt.setInt(2, Integer.parseInt(acno));
                                pstmt.executeUpdate();
                                JOptionPane.showMessageDialog(this, "Account modified successfully!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect PIN. Transaction canceled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found.");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error modifying account.");
            }
        }
    }

    private void viewAccountDetails() {
        String acno = JOptionPane.showInputDialog(this, "Enter Account Number:");

        if (acno != null && !acno.isEmpty()) {
            try {
                String sql = "SELECT * FROM customer WHERE acno=?";
                PreparedStatement pstmt = mydb.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(acno));
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Account exists, proceed with PIN verification
                    String pinInput = JOptionPane.showInputDialog(this, "Enter PIN:");
                    int enteredPin = Integer.parseInt(pinInput);

                    int storedPin = rs.getInt("pin");
                    int decryptedPin = decryptInt(storedPin);  // Decrypt the stored PIN

                    // Step 2: Check if the PIN is correct
                    if (enteredPin == decryptedPin) {
                        // Decrypt other details
                        String details = "Account No: " + rs.getInt("acno") + "\n" +
                                "Name: " + decryptData(rs.getString("name")) + "\n" +
                                "Address: " + decryptData(rs.getString("address")) + "\n" +
                                "Phone: " + decryptData(rs.getString("phone")) + "\n" +
                                "Email: " + decryptData(rs.getString("email")) + "\n" +
                                "Aadhar No: " + decryptData(rs.getString("aadhar_no")) + "\n" +
                                "Account Status: " + rs.getString("status") + "\n" +
                                "Current Balance: " + decryptInt(rs.getInt("balance"));  // Decrypt the balance

                        JOptionPane.showMessageDialog(this, details);
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect PIN. Transaction canceled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching account details.");
            }
        }
    }

    private void deposit() {
        String acno = JOptionPane.showInputDialog(this, "Enter Account Number:");

        // Step 1: Check if the account exists
        if (acno != null && !acno.isEmpty()) {
            try {
                String checkAccountSQL = "SELECT * FROM customer WHERE acno=?";
                PreparedStatement checkAccountStmt = mydb.prepareStatement(checkAccountSQL);
                checkAccountStmt.setInt(1, Integer.parseInt(acno));
                ResultSet rs = checkAccountStmt.executeQuery();

                if (rs.next()) {
                    // Account exists, proceed with PIN verification
                    String pinInput = JOptionPane.showInputDialog(this, "Enter PIN:");
                    int enteredPin = Integer.parseInt(pinInput);

                    int storedPin = rs.getInt("pin");
                    int decryptedPin = decryptInt(storedPin);  // Decrypt the stored PIN

                    // Step 2: Check if the PIN is correct
                    if (enteredPin == decryptedPin) {
                        String depositAmountStr = JOptionPane.showInputDialog(this, "Enter Deposit Amount:");

                        // Step 3: Validate and process the deposit amount
                        if (depositAmountStr != null && !depositAmountStr.isEmpty()) {
                            int depositAmount = Integer.parseInt(depositAmountStr);

                            String sql = "UPDATE customer SET balance = balance + ? WHERE acno=?";
                            PreparedStatement pstmt = mydb.prepareStatement(sql);
                            pstmt.setInt(1, depositAmount);
                            pstmt.setInt(2, Integer.parseInt(acno));
                            pstmt.executeUpdate();

                            // Step 4: Fetch and display updated balance
                            String balanceSQL = "SELECT balance FROM customer WHERE acno=?";
                            PreparedStatement balanceStmt = mydb.prepareStatement(balanceSQL);
                            balanceStmt.setInt(1, Integer.parseInt(acno));
                            ResultSet balanceRs = balanceStmt.executeQuery();

                            if (balanceRs.next()) {
                                int encryptedBalance = balanceRs.getInt("balance");
                                int decryptedBalance = decryptInt(encryptedBalance);  // Decrypt the balance
                                JOptionPane.showMessageDialog(this, "Amount deposited successfully! Updated balance: " + decryptedBalance);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect PIN. Transaction canceled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found. Please re-enter a valid account number.");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error processing deposit.");
            }
        }
    }

    private void withdraw() {
        String acno = JOptionPane.showInputDialog(this, "Enter Account Number:");

        // Step 1: Check if the account exists
        if (acno != null && !acno.isEmpty()) {
            try {
                String checkAccountSQL = "SELECT * FROM customer WHERE acno=?";
                PreparedStatement checkAccountStmt = mydb.prepareStatement(checkAccountSQL);
                checkAccountStmt.setInt(1, Integer.parseInt(acno));
                ResultSet rs = checkAccountStmt.executeQuery();

                if (rs.next()) {
                    // Account exists, proceed with PIN verification
                    String pinInput = JOptionPane.showInputDialog(this, "Enter PIN:");
                    int enteredPin = Integer.parseInt(pinInput);

                    int storedPin = rs.getInt("pin");
                    int decryptedPin = decryptInt(storedPin);  // Decrypt the stored PIN

                    // Step 2: Check if the PIN is correct
                    if (enteredPin == decryptedPin) {
                        String withdrawAmountStr = JOptionPane.showInputDialog(this, "Enter Withdrawal Amount:");

                        // Step 3: Validate the withdrawal amount
                        if (withdrawAmountStr != null && !withdrawAmountStr.isEmpty()) {
                            int withdrawAmount = Integer.parseInt(withdrawAmountStr);

                            int encryptedBalance = rs.getInt("balance");
                            int currentBalance = decryptInt(encryptedBalance);  // Decrypt the current balance

                            // Step 4: Ensure there is enough balance
                            if (currentBalance >= withdrawAmount) {
                                String sql = "UPDATE customer SET balance = balance - ? WHERE acno=?";
                                PreparedStatement pstmt = mydb.prepareStatement(sql);
                                pstmt.setInt(1, withdrawAmount);
                                pstmt.setInt(2, Integer.parseInt(acno));
                                pstmt.executeUpdate();
                                JOptionPane.showMessageDialog(this, "Amount withdrawn successfully!");

                                // Fetch and display updated balance
                                String balanceSQL = "SELECT balance FROM customer WHERE acno=?";
                                PreparedStatement balanceStmt = mydb.prepareStatement(balanceSQL);
                                balanceStmt.setInt(1, Integer.parseInt(acno));
                                ResultSet balanceRs = balanceStmt.executeQuery();

                                if (balanceRs.next()) {
                                    int updatedEncryptedBalance = balanceRs.getInt("balance");
                                    int updatedBalance = decryptInt(updatedEncryptedBalance);  // Decrypt the updated balance
                                    JOptionPane.showMessageDialog(this, "Current Balance: " + updatedBalance);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Insufficient balance. Transaction canceled.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect PIN. Transaction canceled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found. Please re-enter a valid account number.");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error processing withdrawal.");
            }
        }
    }


    public static void main(String[] args) {


        // After the splash screen, show the main application window
        SwingUtilities.invokeLater(() -> {
            BankingAppGUI mainApp = new BankingAppGUI();
            mainApp.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full screen
            mainApp.setVisible(true);
        });
    }
}




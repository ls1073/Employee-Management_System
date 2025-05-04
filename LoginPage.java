import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {
    JTextField tfUsername;
    JPasswordField pfPassword;
    JButton btnLogin;
    JLabel lblError;

    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "1234";

    public LoginPage() {
        setTitle("Login Page");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title label
        JLabel lblTitle = new JLabel("Employee Management Login", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(34, 40, 49));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Center panel with form fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(232, 240, 254));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfUsername = new JTextField(12);
        tfUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfUsername.setPreferredSize(new Dimension(180, 28));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        pfPassword = new JPasswordField(12);
        pfPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        pfPassword.setPreferredSize(new Dimension(180, 28));

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 123, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(120, 30));

        lblError = new JLabel("", JLabel.CENTER);
        lblError.setFont(new Font("Arial", Font.BOLD, 13));
        lblError.setForeground(Color.RED);

        // Add components to the grid
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        centerPanel.add(tfUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        centerPanel.add(pfPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(btnLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        centerPanel.add(lblError, gbc);

        add(centerPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(this);
        getContentPane().setBackground(new Color(207, 226, 243));
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = tfUsername.getText();
        String password = new String(pfPassword.getPassword());

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            new EmployeeManagementSystem();
            dispose();
        } else {
            lblError.setText("Invalid username or password!");
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

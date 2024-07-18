import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrationForm extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JTextField addressField;
    private JCheckBox agreeCheckBox;
    private JButton submitButton;
    private JButton resetButton;
    private JTable registeredTable;
    private DefaultTableModel tableModel;

    private List<Person> registeredPersons;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        registeredPersons = new ArrayList<>();

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleCheckBox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Date of Birth:"));
        JPanel dobPanel = new JPanel();
        dayComboBox = new JComboBox<>(getDays());
        monthComboBox = new JComboBox<>(getMonths());
        yearComboBox = new JComboBox<>(getYears());
        dobPanel.add(dayComboBox);
        dobPanel.add(monthComboBox);
        dobPanel.add(yearComboBox);
        formPanel.add(dobPanel);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        agreeCheckBox = new JCheckBox("Agree to terms and conditions");
        formPanel.add(agreeCheckBox);

        // Buttons
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");
        formPanel.add(submitButton);
        formPanel.add(resetButton);

        add(formPanel, BorderLayout.NORTH);

        // Table for registered persons
        String[] columnNames = {"ID", "Name", "Gender", "Address", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        registeredTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(registeredTable);
        add(scrollPane, BorderLayout.CENTER);

        // Exit and Register buttons
        JPanel bottomPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        JButton registerButton = new JButton("Register");
        bottomPanel.add(exitButton);
        bottomPanel.add(registerButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        submitButton.addActionListener(new SubmitAction());
        resetButton.addActionListener(new ResetAction());
        exitButton.addActionListener(e -> System.exit(0));
        registerButton.addActionListener(e -> new RegistrationForm().setVisible(true));

        setVisible(true);
    }

    private String[] getDays() {
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        return days;
    }

    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = String.valueOf(i);
        }
        return months;
    }

    private String[] getYears() {
        String[] years = new String[100];
        for (int i = 1920; i <= 2019; i++) {
            years[i - 1920] = String.valueOf(i);
        }
        return years;
    }

    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String gender = maleCheckBox.isSelected() ? "Male" : (femaleCheckBox.isSelected() ? "Female" : "");
            String dob = dayComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + yearComboBox.getSelectedItem();
            String address = addressField.getText();

            if (name.isEmpty() || phone.isEmpty() || gender.isEmpty() || address.isEmpty() || !agreeCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields and agree to the terms and conditions.");
                return;
            }

            String id = UUID.randomUUID().toString();
            Person person = new Person(id, name, gender, address, phone);
            registeredPersons.add(person);

            tableModel.addRow(new Object[]{id, name, gender, address, phone});

            JOptionPane.showMessageDialog(null, "Registration successful! \nID: " + id + "\nName: " + name + "\nGender: " + gender + "\nAddress: " + address + "\nContact: " + phone);

            resetForm();
        }
    }

    private class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetForm();
        }
    }

    private void resetForm() {
        nameField.setText("");
        phoneField.setText("");
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
        addressField.setText("");
        agreeCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}

class Person {
    private String id;
    private String name;
    private String gender;
    private String address;
    private String contact;

    public Person(String id, String name, String gender, String address, String contact) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
    }

    // Getters and Setters
}

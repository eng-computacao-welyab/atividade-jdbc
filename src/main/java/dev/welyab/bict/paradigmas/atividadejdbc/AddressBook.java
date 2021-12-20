package dev.welyab.bict.paradigmas.atividadejdbc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AddressBook {

    private JPanel pnlMain;

    public AddressBook() {
        pnlMain = new JPanel();
        pnlMain.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblName = new JLabel("Name");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        pnlMain.add(lblName, gbc);

        JTextField txtName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 10);
        gbc.weightx = 1;
        pnlMain.add(txtName, gbc);

        JLabel lblPhone = new JLabel("Phone");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        pnlMain.add(lblPhone, gbc);

        JTextField txtPhone = new JTextField();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 10);
        gbc.weightx = 1;
        pnlMain.add(txtPhone, gbc);

        JLabel lblEmail = new JLabel("Email");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        pnlMain.add(lblEmail, gbc);

        JTextField txtEmail = new JTextField();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 0, 10);
        pnlMain.add(txtEmail, gbc);

        JLabel lblAddress = new JLabel("Address");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        pnlMain.add(lblAddress, gbc);

        JTextArea txtAreaAddress = new JTextArea(10, 20);
        JScrollPane pane = new JScrollPane(txtAreaAddress);
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 10);
        gbc.weightx = 1;
        pnlMain.add(pane, gbc);

        JButton btnSave = new JButton("Save");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 0);
        gbc.weightx = 1;
        pnlMain.add(btnSave, gbc);

        JButton btnCancel = new JButton("Cancel");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.weightx = 1;
        pnlMain.add(btnCancel, gbc);

    }

    public JPanel getUI() {
        return pnlMain;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Address Book");
                frame.getContentPane().add(new AddressBook().getUI());
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}

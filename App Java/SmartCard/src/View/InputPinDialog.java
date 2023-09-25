/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author HP
 */
public class InputPinDialog extends JDialog implements ActionListener{
    private JComboBox<String> comboBox;
    private JPasswordField passField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isOkClicked;

    public InputPinDialog(Frame parent) {
        super(parent, "Input Dialog", true);

        passField = new JPasswordField(4);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã Pin:"));
        panel.add(passField);
        panel.add(okButton);
        panel.add(cancelButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean showDialog() {
        setVisible(true);
        return isOkClicked;
    }


    public byte[] getPass() {
        char[] pass1 = passField.getPassword();
        byte[] pass = null;

        pass = new byte[pass1.length];
        for (int i = 0; i < pass1.length; i++) {
            if (Character.isDigit(pass1[i])) {
                pass[i] = (byte) pass1[i];
            } else {
                JOptionPane.showMessageDialog(this, "Pin Phải là dãy số");
                return null;
            }
        }
        return pass;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            isOkClicked = true;
            dispose();
        } else if (e.getSource() == cancelButton) {
            isOkClicked = false;
            dispose();
        }
    }
}

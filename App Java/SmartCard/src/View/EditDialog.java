/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author HP
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDialog extends JDialog implements ActionListener {
    private JComboBox<String> comboBox;
    private JTextField textField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isOkClicked;

    public EditDialog(Frame parent) {
        super(parent, "Edit Dialog", true);

        comboBox = new JComboBox<>(new String[]{"Ô Tô", "Xe Máy", "Xe Đap Điện", "Xe Máy Điện"});

        textField = new JTextField(20);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Loại Xe:"));
        panel.add(comboBox);
        panel.add(new JLabel("Biển Số:"));
        panel.add(textField);
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

    public String getSelectedOption() {
        return (String) comboBox.getSelectedItem();
    }

    public String getTextValue() {
        return textField.getText();
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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JButton showDialogButton = new JButton("Show Dialog");
        showDialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                EditDialog dialog = new EditDialog(frame);
                boolean isOkClicked = dialog.showDialog();
                if (isOkClicked) {
                    // Lấy thông tin đã sửa
                    String selectedOption = dialog.getSelectedOption();
                    String newTextValue = dialog.getTextValue();

                    // Cập nhật thông tin trong bảng
                    System.out.println("Selected Option: " + selectedOption);
                    System.out.println("New Text Value: " + newTextValue);
                }
            }
        });

        frame.getContentPane().add(showDialogButton);
        frame.setVisible(true);
    }
}

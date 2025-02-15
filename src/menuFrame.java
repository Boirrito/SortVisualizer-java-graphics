import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.*;

public class menuFrame extends JFrame implements ActionListener {

    GridBagConstraints c;
    JLabel label;


    JPanel arraySizePanel;

    JTextField arrLengthField;
    JLabel arraySizeLabel;

    JPanel normalRadioButtonPanel;
    JRadioButton bubbleSortButton;
    JRadioButton selectionSortButton;
    JRadioButton insertionSortButton;
    JRadioButton quickSortButton;

    JButton startButton;

    public menuFrame() {

        c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("Sort Visualizer");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        add(label, c);

        arraySizePanel = new JPanel();
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1,2));
        arraySizePanel.setLayout(new GridLayout(2, 0));
        arraySizePanel.setBorder(new TitledBorder("Array"));

        
        arrLengthField = new JTextField();
        // Use default length of 10
        setArrayLength("10");
        arrLengthField.setSize(100, 100);
        arraySizeLabel = new JLabel("Array Size");
        top.add(arrLengthField);
        top.add(arraySizeLabel);
        
        arraySizePanel.add(top);
        // Add array size buttons
        arraySizePanel.add(this.createArraySizeBtn(10));
        arraySizePanel.add(this.createArraySizeBtn(50));
        arraySizePanel.add(this.createArraySizeBtn(100));

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        add(arraySizePanel, c);


       /*  arrLengthField.setText("10");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        add(arrLengthField, c);
        c.gridx = 2;
        c.gridwidth = 1;
        add(arraySizeLabel, c);
 */
       

        /* c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(size10Button, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        add(size50Button, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        add(size100Button, c);
 */
        normalRadioButtonPanel = new JPanel();
        normalRadioButtonPanel.setLayout(new FlowLayout());
        normalRadioButtonPanel.setBorder(new TitledBorder("Standard"));
        bubbleSortButton = new JRadioButton("Bubble Sort");
        // Set bubble sort as default active algorithm
        bubbleSortButton.setSelected(true);
        selectionSortButton = new JRadioButton("Selction Sort");
        insertionSortButton = new JRadioButton("Insertion Sort");
        quickSortButton = new JRadioButton("Quick Sort");
        bubbleSortButton.addActionListener(this);
        selectionSortButton.addActionListener(this);
        insertionSortButton.addActionListener(this);
        quickSortButton.addActionListener(this);
        normalRadioButtonPanel.add(bubbleSortButton);
        normalRadioButtonPanel.add(selectionSortButton);
        normalRadioButtonPanel.add(insertionSortButton);
        normalRadioButtonPanel.add(quickSortButton);


        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        add(normalRadioButtonPanel, c);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        add(startButton, c);

        pack();
    }

    /**
     * Creates a Button which, when clicked, updates the array to the given size.
     * @param size Size which will be applied to arrLengthField
     * @return Button instance
     */
    private JButton createArraySizeBtn(int size) {
        String sizeText = Integer.valueOf(size).toString();
        JButton btn = new JButton(sizeText);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // On click, update array length
                setArrayLength(sizeText);
            }
        });

        return btn;
    }

    private void setArrayLength(String len) {
        arrLengthField.setText(len);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bubbleSortButton) {
            selectionSortButton.setSelected(false);
            insertionSortButton.setSelected(false);
            quickSortButton.setSelected(false);

        }

        if (e.getSource() == selectionSortButton) {
            bubbleSortButton.setSelected(false);
            insertionSortButton.setSelected(false);
            quickSortButton.setSelected(false);

        }

        if(e.getSource() == insertionSortButton){
            bubbleSortButton.setSelected(false);
            selectionSortButton.setSelected(false);
            quickSortButton.setSelected(false);
        }

        if(e.getSource() == quickSortButton){
            bubbleSortButton.setSelected(false);
            selectionSortButton.setSelected(false);
            insertionSortButton.setSelected(false);
        }

        if (e.getSource() == startButton && bubbleSortButton.isSelected()) {

            try {
                int size = Integer.parseInt(arrLengthField.getText());
                new SortFrame(size, "bubble");
            } catch (Exception eee) {
                eee.printStackTrace();
            }

        } else if (e.getSource() == startButton && selectionSortButton.isSelected()) {

            try {
                int size = Integer.parseInt(arrLengthField.getText());
                new SortFrame(size, "selection");
            } catch (Exception eee) {
                eee.printStackTrace();
            }

        } else if (e.getSource() == startButton && insertionSortButton.isSelected()) {

            try {
                int size = Integer.parseInt(arrLengthField.getText());
                new SortFrame(size, "insertion");
            } catch (Exception eee) {
                eee.printStackTrace();
            }

        } else if (e.getSource() == startButton){
            if(insertionSortButton.isSelected() == false && selectionSortButton.isSelected() == false && bubbleSortButton.isSelected() == false){
                JOptionPane.showMessageDialog(this, "Please select an alogrithm and try again", "no selection", JOptionPane.INFORMATION_MESSAGE);
            }
          
           
        }

    }

   

}

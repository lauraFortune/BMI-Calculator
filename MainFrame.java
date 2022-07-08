// Laura Fortune, laurafordefortune@gmail.com, 0831221696

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame{
    
    //================================== 
    //          INSTANCE VARIABLES
    //==================================
    private double numKg, numCm; // metric
    private double numSt, numLb, numFt, numIn; // imperial
    // components
    // radio buttons - to select measurement type
    private JRadioButton metricRadio;
    private JRadioButton imperialRadio;
    private ButtonGroup radioGroup;
    // labels
    private JLabel weightLabel;
    private JLabel heightLabel;
    // labels to prompt input
    private JLabel cmLabel;
    private JLabel ftLabel;
    private JLabel inLabel;
    private JLabel kgLabel;
    private JLabel stLabel;
    private JLabel lbLabel;
    // metric inputs
    private JTextField kiloField;
    private JTextField centimeterField;
    // imperial inputs
    private JTextField stoneField;
    private JTextField poundField;
    private JTextField footField;
    private JTextField inchField;
    //show hide elements
    private JComponent[] metricElements; // jcomponent parent component class
    private JComponent[] imperialElements;
    // calculate bmi button
    private JButton calcBmi;
    private JPanel dataPanel;
    private JLabel bmiLabel;
    private JLabel bmiStatusLabel;
    private JLabel bmiCriteriaLabel;
   
    //================================== 
    //          CONSTRUCTOR
    //==================================
    public MainFrame(){

        setLayout(null);
        //========== RADIO BUTTONS
        metricRadio = new JRadioButton("Metric", true);
        metricRadio.setBounds(30, 20, 80, 25);
        imperialRadio = new JRadioButton("Imperial", false);
        imperialRadio.setBounds(110, 20, 80, 25);
        radioGroup = new ButtonGroup(); // create logical relationship between radio buttons
        
        RadioButtonHandler radioEvent = new RadioButtonHandler(); // register events for radio buttons
        JRadioButton[] radioBtns = {metricRadio, imperialRadio};
         
        for(JRadioButton radioBtn : radioBtns){ // for each radioBtn do this:
            radioBtn.addActionListener(radioEvent); 
            radioGroup.add(radioBtn);
            add(radioBtn);   
        }
        //========== INPUT LABELS
        weightLabel = new JLabel("Weight");
        weightLabel.setBounds(35, 65, 80, 25);
        add(weightLabel);
        heightLabel = new JLabel("Height");
        heightLabel.setBounds(35, 105, 80, 25);
        add(heightLabel);
        // labels to further prompt user input 
        kgLabel = new JLabel("kg");
        kgLabel.setBounds(306, 65, 20, 25);
        lbLabel = new JLabel("lbs");
        lbLabel.setBounds(306, 65, 20, 25);
        cmLabel = new JLabel("cm");
        cmLabel.setBounds(306, 105, 20, 25);
        inLabel = new JLabel("in");
        inLabel.setBounds(306, 105, 20, 25); 
        stLabel = new JLabel("st");
        stLabel.setBounds(196, 65, 20, 25);
        ftLabel = new JLabel("ft");
        ftLabel.setBounds(196, 105, 20, 25);
        //========== INPUT FIELDS
        // metric
        kiloField = new JTextField("0.0");
        kiloField.setBounds(105, 65, 200, 25);
        centimeterField = new JTextField("0.0");
        centimeterField.setBounds(105, 105, 200, 25);     
        // imperial
        stoneField = new JTextField("0.0"); 
        stoneField.setBounds(105, 65, 90, 25);
        poundField = new JTextField("0.0");
        poundField.setBounds(215, 65, 90, 25);
        footField = new JTextField("0.0");
        footField.setBounds(105, 105, 90, 25);  
        inchField = new JTextField("0.0");
        inchField.setBounds(215, 105, 90, 25);
        //========== SHOW/HIDE ELEMENTS
        metricElements = new JComponent[]{kiloField, centimeterField, kgLabel, cmLabel};
        for(JComponent element : metricElements){ // for each metric elem do this:
            add(element);
        }
        imperialElements = new JComponent[]{stoneField, poundField, footField, inchField, stLabel,lbLabel, ftLabel, inLabel};
        for(JComponent element : imperialElements){ // for each imperial elem do this:
            element.setVisible(false);
            add(element);
        }
        //========== CALCULATE BMI BUTTON
        calcBmi = new JButton("Calculate");
        calcBmi.setBounds(30, 160, 100, 27);
        BmiBtnEventHandler eventHandler = new BmiBtnEventHandler(); // register event for bmiCalc button
        calcBmi.addActionListener(eventHandler);
        add(calcBmi); 
        //========== DISPLAY BMI DATA
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(4, 1)); 
        dataPanel.setBorder(new EmptyBorder(15, 15, 10, 10)); //t,l,b,r
        dataPanel.setBounds(40, 230, 280, 180);
        dataPanel.setBackground(Color.white);
        add(dataPanel);
        //bmi data
        bmiLabel = new JLabel();
        bmiLabel.setFont(new Font("Monaco", Font.BOLD, 45));
        dataPanel.add(bmiLabel);
        bmiStatusLabel = new JLabel();
        bmiStatusLabel.setFont(new Font("Monaco", Font.PLAIN, 20));
        dataPanel.add(bmiStatusLabel);
        bmiCriteriaLabel = new JLabel();
        dataPanel.add(bmiCriteriaLabel);
    } // end constructor
    
    //================================== 
    //          EVENT HANDLERS
    //==================================
    //========== RADIO BUTTONS EVENT HANDLER
    private class RadioButtonHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            AbstractButton btn = (AbstractButton) e.getSource();
            
            if(btn.getText() == "Metric"){ // if metric
                 // show/hide
                setElementsVisible(true, metricElements);
                setElementsVisible(false, imperialElements);
                // input validation
                try{
                    numSt = Double.parseDouble(stoneField.getText());
                    numLb = Double.parseDouble(poundField.getText());
                    numFt = Double.parseDouble(footField.getText());
                    numIn = Double.parseDouble(inchField.getText());
                 
                    numKg = MathHelper.stonesPoundsToKilos(numSt, numLb);
                    numCm = MathHelper.feetInchesToCms(numFt, numIn);
                    
                    String resultKg = String.format("%,.2f", numKg);
                    String resultCm = String.format("%,.2f", numCm);
                    kiloField.setText(resultKg);
                    centimeterField.setText(resultCm);

                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this, String.format("Numeric values only!" ) );
                    resetInputFields();
                }
            } else { // else if Imperial
                // show/hide
                setElementsVisible(false, metricElements);
                setElementsVisible(true, imperialElements);
                // input validation
                try{
                    numKg = Double.parseDouble(kiloField.getText());
                    numCm = Double.parseDouble(centimeterField.getText());
                    
                    numSt = MathHelper.kilosToStonesPounds(numKg)[0]; //stone is 1st element of array
                    numLb = MathHelper.kilosToStonesPounds(numKg)[1]; //pound is 2nd element of array
                    numFt = MathHelper.cmsToFeetInches(numCm)[0]; // feet is 1st element of array
                    numIn = MathHelper.cmsToFeetInches(numCm)[1]; // cm is 2nd element of array
                    
                    String resultSt = String.format("%,.2f", numSt);
                    String resultLb = String.format("%,.2f", numLb);
                    String resultFt = String.format("%,.2f", numFt);
                    String resultIn = String.format("%,.2f", numIn);
                    stoneField.setText(resultSt);
                    poundField.setText(resultLb);
                    footField.setText(resultFt);
                    inchField.setText(resultIn);
     
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this, String.format("Numeric values only!" ) );
                    resetInputFields();
                }
            } // end if/else
        }  // end actionPerformed
    } // end class radiobuttonhandler 
    
    //========== CALCULATE BMI BUTTON EVENT HANDLER
    private class BmiBtnEventHandler implements ActionListener{
   
        public void actionPerformed( ActionEvent e ){            
            
            // check for valid values
            try{
                // set values 
                if(metricRadio.isSelected()){
                    numKg = Double.parseDouble(kiloField.getText());
                    numCm = Double.parseDouble(centimeterField.getText());
                } else if (imperialRadio.isSelected()){
                    numSt = Double.parseDouble(stoneField.getText());
                    numLb = Double.parseDouble(poundField.getText());
                    numFt = Double.parseDouble(footField.getText());
                    numIn = Double.parseDouble(inchField.getText());
                    //get values form converter functions
                    numKg = MathHelper.stonesPoundsToKilos(numSt, numLb);
                    numCm = MathHelper.feetInchesToCms(numFt, numIn); 
                } // end if/else

                // perform bmi calculation or error handle
                if((numKg > 0  && numCm > 0) && (numKg <= 958 && numCm <= 396)){  
                    bmiLabel.setText(MathHelper.bmiCalculator(numKg, numCm)[0]);
                    bmiStatusLabel.setText(MathHelper.bmiCalculator(numKg, numCm)[1] + " BMI");
                    bmiCriteriaLabel.setText("BMI " + MathHelper.bmiCalculator(numKg, numCm)[2]);
                } else { // error handling - incorrect values entered
                    if (metricRadio.isSelected()){ // if metricRadio selected
                        JOptionPane.showMessageDialog(MainFrame.this, String.format(
                            "ALL Numbers must be greater than 0 and within a normal range:\n"
                                + "- Kilos less than or equal to 958kg\n"
                                + "- Centimeters less than or equal to 396cm\n"
                        ));
                    } else if (imperialRadio.isSelected()) { // if imperialRadio selected
                        JOptionPane.showMessageDialog(MainFrame.this, String.format(
                            "ALL Numbers must be greater than 0 and within a normal range:\n"
                                + "-Stones less than or equal to 150st\n"
                                + "-Pounds less than or equal to 14lb\n"
                                + "-Feet less than or equal to 12ft\n"
                                + "-Inches less than or equal to 12in\n"
                        ));                  
                    }
                    resetInputFields();
                } // end if/else
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(MainFrame.this, String.format("Numeric values only!" ) );
                // set all fields to zero
                resetInputFields();
            } 
        } // end actionPerformed
    } // end class BmiBtnEventHandler 
    
    //================================== 
    //          METHODS
    //==================================
    // setVisible
    private void setElementsVisible(boolean visible, JComponent[] elements) {
        for(JComponent element : elements) {
            element.setVisible(visible);
        }
    }
    // reset input field values to zero
    private void resetInputFields() {
        kiloField.setText("0.0");
        centimeterField.setText("0.0");
        stoneField.setText("0.0");
        poundField.setText("0.0");
        footField.setText("0.0");
        inchField.setText("0.0");
        bmiLabel.setText("");
        bmiStatusLabel.setText("");
        bmiCriteriaLabel.setText("");
    }
    
} // end class mainframe



// ======================= References
// https://stackoverflow.com/questions/22595477/selecting-radio-buttons-to-make-text-appear
// https://stackhowto.com/how-to-get-value-of-selected-jradiobutton-in-java/
// http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningtoJRadioButtonEventswithanActionListener.htm
// https://www.tutorialspoint.com/how-to-set-font-face-style-size-and-color-for-jtextpane-text-in-java


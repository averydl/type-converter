
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DerekAvery
 */
public class ConverterGUI {

    private JFrame frm = new JFrame("Number Converter");
    private JPanel pnl = new JPanel();

    // labels for text fields
    private JLabel decLbl = new JLabel("Decimal");
    private JLabel binLbl = new JLabel("Binary");
    private JLabel octLbl = new JLabel("Octal");
    private JLabel hexLbl = new JLabel("Hex");
    private JLabel ascLbl = new JLabel("Characters");

    // text fields for numeric types to be converted
    private JTextField decTxt = new JTextField();
    private JTextField binTxt = new JTextField();
    private JTextField octTxt = new JTextField();
    private JTextField hexTxt = new JTextField();
    private JTextField ascTxt = new JTextField();

    // text fields for color and float representations
    private JTextField colTxt = new JTextField();
    private JTextField fpnTxt = new JTextField();

    private JButton convertBtn = new JButton("Convert");
    private JButton clearBtn = new JButton("Clear");

    public static void main(String[] args) {
        ConverterGUI gui = new ConverterGUI();
    }

    public void convertFields(int value) {
        decTxt.setText("" + value);
        binTxt.setText(TypeConverter.decToBin(value));
        octTxt.setText(TypeConverter.decToOct(value));
        hexTxt.setText(TypeConverter.decToHex(value));
        ascTxt.setText(TypeConverter.binToChars(binTxt.getText()));
    }

    public ConverterGUI() {

        // set appropriate default colors for GUI 
        // background and color display window
        pnl.setBackground(new Color(0xB7A57A));
        colTxt.setBackground(new Color(0x4B2E83));

        // add GUI components to JPanel
        pnl.setLayout(new GridLayout(0, 2));
        pnl.add(decLbl);
        pnl.add(decTxt);

        pnl.add(binLbl);
        pnl.add(binTxt);

        pnl.add(octLbl);
        pnl.add(octTxt);

        pnl.add(hexLbl);
        pnl.add(hexTxt);

        pnl.add(ascLbl);
        pnl.add(ascTxt);

        pnl.add(convertBtn);
        pnl.add(clearBtn);

        // add panel to frame and set dimensions/on-close options
        frm.add(pnl);

        frm.setVisible(true);
        frm.setPreferredSize(new Dimension(500, 500));
        frm.pack();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add action listener to submit button to convert
        // user-entered color values to Hex color
        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (decTxt.getText().length() > 0) {
                    convertFields(Integer.parseInt(decTxt.getText()));
                } else if (binTxt.getText().length() > 0) {
                    convertFields(Integer.parseInt(binTxt.getText(), 2));
                } else if (octTxt.getText().length() > 0) {
                    convertFields(Integer.parseInt(octTxt.getText(), 8));
                } else if(hexTxt.getText().length() > 0) {
                    String hexNumber = hexTxt.getText().substring(2, hexTxt.getText().length());
                    try {
                        convertFields(Integer.parseInt(hexNumber, 16));
                    } catch(NumberFormatException nfe) {
                        hexTxt.setText("Invalid hexadecimal format");
                    }
                } else if (ascTxt.getText().length() > 0) {
                    String binaryChars = TypeConverter.stringToBin(ascTxt.getText());
                    convertFields(Integer.parseInt(binaryChars, 2));
                }
            }
        }
        );

        // add action listener to clear button to 
        // empty all text fields upon clicking
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decTxt.setText("");
                binTxt.setText("");
                octTxt.setText("");
                hexTxt.setText("");
                ascTxt.setText("");
            }
        }
        );
    }
    
    /*
     * nested static class used for conversions
     * between decimal and non-decimal types, 
     * including hexadecimal, octal, and ASCII
     * character Strings
     */
    public static class TypeConverter {

        /* 
         * Converts integer argument to a binary
         * number in String format
         */
        public static String decToBin(int number) {
            String result = "";
            while (number > 0) {
                result = number % 2 + result;
                number /= 2;
            }
            return result;
        }

        /* 
         * Converts integer argument to a octal
         * number in String format
         */
        public static String decToOct(int number) {
            String result = "";
            while (number > 0) {
                result = number % 8 + result;
                number /= 8;
            }
            return result;
        }

        /* 
         * Converts integer argument to a hex
         * number in String format
         */
        public static String decToHex(int number) {
            String result = "";

            while (number > 0) {
                int remainder = number % 16;
                switch (remainder) {
                    case 10:
                        result = "A" + result;
                        break;
                    case 11:
                        result = "B" + result;
                        break;
                    case 12:
                        result = "C" + result;
                        break;
                    case 13:
                        result = "D" + result;
                        break;
                    case 14:
                        result = "E" + result;
                        break;
                    case 15:
                        result = "F" + result;
                        break;
                    default:
                        result = remainder + result;
                }
                number /= 16;
            }
            return "0x" + result;
        }

        /* 
         * Converts a binary number in String form to its 
         * corresponding ASCII-encoded character String
         */
        public static String binToChars(String number) {
            String result = "";

            // pad number with leading 0's to ensure valid character encoding
            while (number.length() % 8 != 0) {
                number = '0' + number;
            }

            // extract each ASCII character from the binary number & append to @param result
            for (int i = 0; i < number.length(); i += 8) {
                char curChar = (char) Integer.parseInt(number.substring(i, i + 8), 2);
                result += curChar;
            }
            return result;
        }

        /* 
         * Converts a character String into its corresponding
         * binary number representation in String form
         */
        public static String stringToBin(String str) {
            String result = "";
            for (int i = 0; i < str.length(); i++) {
                result += decToBin(str.charAt(i));
                while(result.length() % 8 != 0)
                    result = "0" + result;
            }
            return result;
        }
    }
}

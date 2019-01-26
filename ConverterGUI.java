
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

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
    private JLabel colLbl = new JLabel("Color");
    private JLabel fltLbl = new JLabel("Float Decimal");

    // text fields for types to be converted
    private JTextField decTxt = new JTextField();
    private JTextField binTxt = new JTextField();
    private JTextField octTxt = new JTextField();
    private JTextField hexTxt = new JTextField();
    private JTextField ascTxt = new JTextField();
    private JTextField colTxt = new JTextField();
    private JTextField fltTxt = new JTextField();

    // last text field altered by user
    private JTextField lastAlteredTxt;

    private JButton convertBtn = new JButton("Convert");
    private JButton clearBtn = new JButton("Clear");
    private JButton colorBtn = new JButton("Choose Color");
    
    private Color curColor = Color.WHITE;

    public static void main(String[] args) {
        ConverterGUI gui = new ConverterGUI();
    }

    public void convertFields(int value) {
        decTxt.setText("" + value);
        binTxt.setText(TypeConverter.decToBin(value));
        octTxt.setText(TypeConverter.decToOct(value));
        hexTxt.setText(TypeConverter.decToHex(value));
        ascTxt.setText(TypeConverter.binToChars(binTxt.getText()));
        colTxt.setBackground(new Color(value));
        fltTxt.setText(TypeConverter.binaryToFloat(binTxt.getText()));
    }

    /*
     * Adds empty String to every JTextField in 
     * the JPanel GUI; sets background color to 'black'
     */
    public void clearFields() {
        decTxt.setText("");
        binTxt.setText("");
        octTxt.setText("");
        hexTxt.setText("");
        ascTxt.setText("");
        curColor = Color.WHITE;
        colTxt.setBackground(curColor);
        fltTxt.setText("");
    }

    public void setFields() {
        if (lastAlteredTxt == decTxt) {
            convertFields(TypeConverter.stringToInt(decTxt.getText()));
        } else if (lastAlteredTxt == binTxt) {
            try {
                convertFields(TypeConverter.binaryStringToInt(binTxt.getText()));
            } catch (Exception exc) {
                binTxt.setText("Invalid binary format");
            }
        } else if (lastAlteredTxt == octTxt) {
            try {
                convertFields(TypeConverter.octalStringToInt(octTxt.getText()));
            } catch (Exception exc) {
                octTxt.setText("Invalid octal format");
            }
        } else if (lastAlteredTxt == hexTxt) {
            try {
                convertFields(TypeConverter.hexStringToInt(hexTxt.getText()));
            } catch (Exception exc) {
                hexTxt.setText("Invalid hexadecimal format");
            }
        } else if (lastAlteredTxt == ascTxt) {
            String binaryChars = TypeConverter.stringToBin(ascTxt.getText());
            try {
                convertFields(TypeConverter.binaryStringToInt(binaryChars));
            } catch (Exception exc) {
                ascTxt.setText("Invalid character input");
            }
        } else if(lastAlteredTxt == colTxt) {
            try {
                convertFields(TypeConverter.binaryStringToInt(TypeConverter.colorToBinary(curColor)));
            } catch (Exception exc) {
                clearFields();
            }
        } else if(lastAlteredTxt == fltTxt) {
            try {
                convertFields(TypeConverter.binaryStringToInt(TypeConverter.floatToBinary(fltTxt.getText())));
            } catch (Exception exc) {
                fltTxt.setText("Invalid float format");
            }
        }
    }

    public ConverterGUI() {
        
        // no text has been altered on instantiation
        lastAlteredTxt = null;

        // set up sizing
        convertBtn.setPreferredSize(new Dimension(50, 4));
        clearBtn.setPreferredSize(new Dimension(50, 4));

        pnl.setLayout(new GridLayout(0, 3));

        // add GUI components to JPanel
        pnl.add(decLbl);
        pnl.add(decTxt);
        pnl.add(new JPanel());

        pnl.add(binLbl);
        pnl.add(binTxt);
        pnl.add(new JPanel());

        pnl.add(octLbl);
        pnl.add(octTxt);
        pnl.add(new JPanel());

        pnl.add(hexLbl);
        pnl.add(hexTxt);
        pnl.add(new JPanel());

        pnl.add(ascLbl);
        pnl.add(ascTxt);
        pnl.add(new JPanel());

        pnl.add(colLbl);
        pnl.add(colTxt);
        pnl.add(colorBtn);
        
        colTxt.setBackground(curColor);

        pnl.add(fltLbl);
        pnl.add(fltTxt);
        pnl.add(new JPanel());
        
        pnl.add(convertBtn);
        pnl.add(clearBtn);

        // add panel to frame and set dimensions/on-close options
        frm.add(pnl);

        frm.setVisible(true);
        frm.setPreferredSize(new Dimension(500, 500));
        frm.pack();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        /*
         * Add action listener to 'convert' button to convert
         * all fields. 
         *
         * NOTE: The fields will be converted using the textfield
         *       which has been changed most recently, which is tracked
         *       using the @param lastAlteredTxt reference
         */
        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFields();
            }
        }
        );
        
        /* 
         * Add on-click action listener to @param colorBtn to 
         * allow users to select/change the @param colTxt background
         * color using the JColorChooser object
         */
        colorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curColor = JColorChooser.showDialog(pnl, "Color Selector", new Color(0x4b2e83));
                colTxt.setBackground(curColor);
                lastAlteredTxt = colTxt;
                try {
                    convertFields(TypeConverter.binaryStringToInt(TypeConverter.colorToBinary(curColor)));
                } catch (Exception exc) {
                    //do nothing
                }
            }
        }
        );

        /*
         * add action listener to clear button to 
         * empty all text fields upon clicking
         */
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                lastAlteredTxt = null;
            }
        }
        );

        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param decTxt field 
         */
        decTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = decTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.stringToInt(decTxt.getText()));
                    } catch (Exception exc) {
                        decTxt.setText("Invalid decimal input");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
            }
        }
        );

        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param binTxt field 
         */
        binTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = binTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.binaryStringToInt(binTxt.getText()));
                    } catch (Exception exc) {
                        binTxt.setText("Invalid binary input");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
            }
        }
        );

        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param octTxt field 
         */
        octTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = octTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.octalStringToInt(octTxt.getText()));
                    } catch (Exception exc) {
                        octTxt.setText("Invalid octal input");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
            }
        }
        );

        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param hexTxt field 
         */
        hexTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = hexTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.hexStringToInt(hexTxt.getText()));
                    } catch (Exception exc) {
                        hexTxt.setText("Invalid Hex input");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
            }
        }
        );

        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param ascTxt field 
         */
        ascTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = ascTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.binaryStringToInt(TypeConverter.stringToBin(ascTxt.getText())));
                    } catch (Exception exc) {
                        ascTxt.setText("Invalid ASCII input");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
            }
        }
        );
        
        /* 
         * add KeyListener to repopulate all fields if 
         * text is re-entered in the @param decTxt field 
         */
        fltTxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                lastAlteredTxt = fltTxt;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        convertFields(TypeConverter.binaryStringToInt(TypeConverter.floatToBinary(fltTxt.getText())));
                    } catch (Exception exc) {
                        fltTxt.setText("Invalid float input: " + exc);
                        exc.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no action
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

        public static int charToInt(char digit) {
            switch (digit) {
                case '0':
                    return 0;
                case '1':
                    return 1;
                case '2':
                    return 2;
                case '3':
                    return 3;
                case '4':
                    return 4;
                case '5':
                    return 5;
                case '6':
                    return 6;
                case '7':
                    return 7;
                case '8':
                    return 8;
                case '9':
                    return 9;
                default:
                    throw new IllegalArgumentException();

            }
        }

        public static int stringToInt(String number) {
            int pow = 0;
            int result = 0;
            for (int i = number.length() - 1; i >= 0; i--) {
                result += charToInt(number.charAt(i)) * Math.pow(10, pow);
                pow++;
            }
            return result;
        }

        public static int binaryStringToInt(String number) {
            int pow = 0;
            int result = 0;
            for (int i = number.length() - 1; i >= 0; i--) {
                result += charToInt(number.charAt(i)) * Math.pow(2, pow);
                pow++;
            }
            return result;
        }

        public static int octalStringToInt(String number) {
            int pow = 0;
            int result = 0;
            for (int i = number.length() - 1; i >= 0; i--) {
                result += charToInt(number.charAt(i)) * Math.pow(8, pow);
                pow++;
            }
            return result;
        }

        public static int hexStringToInt(String number) throws Exception {
            if(number.length() < 2 || number.charAt(0) != '0' || number.charAt(1) != 'x')
                throw new Exception();
            else 
                number = number.substring(2, number.length());
            int pow = 0;
            int result = 0;
            for (int i = number.length() - 1; i >= 0; i--) {
                switch (number.charAt(i)) {
                    case 'A':
                        result += 10 * Math.pow(16, pow);
                        break;
                    case 'B':
                        result += 11 * Math.pow(16, pow);
                        break;
                    case 'C':
                        result += 12 * Math.pow(16, pow);
                        break;
                    case 'D':
                        result += 13 * Math.pow(16, pow);
                        break;
                    case 'E':
                        result += 14 * Math.pow(16, pow);
                        break;
                    case 'F':
                        result += 15 * Math.pow(16, pow);
                        break;
                    default:
                        result += charToInt(number.charAt(i)) * Math.pow(16, pow);
                }
                pow++;
            }
            return result;
        }

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

            while (result.length() % 8 != 0) {
                result = '0' + result;
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
                while (result.length() % 8 != 0) {
                    result = "0" + result;
                }
            }
            return result;
        }

        /* 
         * Converts a Color object to a binary string 
         * representing its respective alpha/RGB components
         */
        public static String colorToBinary(Color color) {
            StringBuilder result = new StringBuilder();
            String red = TypeConverter.decToBin(color.getRed());
            String green = TypeConverter.decToBin(color.getGreen());
            String blue = TypeConverter.decToBin(color.getBlue());

            result.append(red);
            result.append(green);
            result.append(blue);
            return result.toString();
        }
        
        /* 
         * Converts a binary String to its corresponding 'float'
         * value per the IEEE standard, and returns this value 
         * in String format
         */
        public static String binaryToFloat(String number) {
            while(number.length() < 32)
                number += "0";
            String result = "";
            
            // add negative sign if 'float' has leading '1'
            if(number.charAt(0) == '1')
                result += "-";

            // calculate exponent of the floating point number
            int power = TypeConverter.binaryStringToInt(number.substring(1, 9))-127;
            
            // calculate mantissa of the float
            double sum = 0;
            int exp = power;
            
            for(int i = 9; i < 32; i++) {
                if(number.charAt(i) == '1')
                    sum += Math.pow(2, exp);
                exp--;
            }
            
            result = result + sum;

            return result;
        }
        
        /* 
         * Converts a 'float' in String form to its corresponding
         * binary value per the IEEE standard, and returns it in 
         * String format
         */
        public static String floatToBinary(String number) {
            StringBuilder result = new StringBuilder();
            float num = Float.parseFloat(number);

            if(num < 0) {
                result.append("1");
                num *= -1;
            } else {
                result.append("0");
            }
            
            
            // extract leading non-decimal portion of number
            String lead = TypeConverter.decToBin((int) num);
            lead = lead.substring(lead.indexOf('1'), lead.length());

            num = num % 1;
            
            int exp = lead.length()-1;
            
            // append exponent portion of float to @param result
            result.append(TypeConverter.decToBin(exp+127).substring(0,8));

            
            // append mantissa to @param result
            result.append(lead);
            
            while(result.length() < 32) {
                num *= 2;
                if(num > 1.0) {
                    result.append("1");
                    num -= 1;
                } else {
                    result.append("0");
                }
            }
            
            return result.toString();
        }
    }
}

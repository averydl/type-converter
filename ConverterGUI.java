
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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Menu that pops up when the user right clicks in the table menu
 * @author Chris
 */
public class TablePopupMenu extends JPopupMenu 
{
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    private ActionListener parent; 
    private JMenu insertMenu;
    private JMenuItem fillBlank, list, freeRes, multChoice, matching, order;
    private JMenuItem trueFalse, divider, numberRst, instructions;
    private JMenuItem pageBreak, sectionTitle;
    /** The name for all fill in the blanks */
    public static final String kFITBName =
            kBundle.getString("FILL-IN-THE-BLANKS");
    /** The name for all lists */
    public static final String kListName = kBundle.getString("LIST");
    /** The name for all long answers */
    public static final String kFRName = kBundle.getString("FREE RESPONSE");
    /** The name for all multiple choices */
    public static final String kMCName = kBundle.getString("MULTIPLE CHOICE");
    /** The name for all matching */
    public static final String kMatName = kBundle.getString("MATCHING");
    /** The name for all order type questions */
    public static final String kOrdName = kBundle.getString("ORDER");
    /** The name for all true or false questions */
    public static final String kTFName = kBundle.getString("TRUE FALSE");
    /** The name for all dividers */
    public static final String kDVName = kBundle.getString("DIVIDER");
    /** The name for all numbering restarts */
    public static final String kNRName = kBundle.getString("NUMBERING RESTART");
    /** The name for all page breaks */
    public static final String kPBName = kBundle.getString("PAGE BREAK");
    /** The name for all instructions */
    public static final String kInsName = kBundle.getString("INSTRUCTIONS");
    /** The name for all section titles */
    public static final String kSTName = kBundle.getString("SECTION TITLE");



    /**
     * Default constructor
     * @param nParent The parent listening to the menu choice
     */
    public TablePopupMenu(ActionListener nParent)
    {
        parent = nParent;
        insertMenu = new JMenu("Insert");
        initItem(fillBlank, kFITBName);
        initItem(list, kListName);
        initItem(freeRes, kFRName);
        initItem(multChoice, kMCName);
        initItem(matching, kMatName);
        initItem(order, kOrdName);
        initItem(trueFalse, kTFName);
        initItem(divider, kDVName);
        initItem(numberRst, kNRName);
        initItem(instructions, kInsName);
        initItem(pageBreak, kPBName);
        initItem(sectionTitle, kSTName);

        add(insertMenu);
     

    }
    
    private void initItem(JMenuItem item, String name)
    {
        item = new JMenuItem(name);
        item.setActionCommand(name);
        item.addActionListener(parent);
        insertMenu.add(item);
    }
}

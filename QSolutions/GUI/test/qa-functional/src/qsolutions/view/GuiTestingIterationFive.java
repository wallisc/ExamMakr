package qsolutions.view;

import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.MainWindowOperator;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jemmy.operators.*;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;


/**
 * Tests features and defect fixes from iteration 5.
 * @author michaelmease, jake tobin
 */
public class GuiTestingIterationFive extends JellyTestCase
{    
    private MainWindowOperator mainWindow;
    private TopComponentOperator tco;
    private TopComponentOperator dView;
    private JTreeOperator treeOp;
    private JEditorPaneOperator textField;
    private JTableOperator tableOp;
    private JTreeOperator op;
            
    public GuiTestingIterationFive(String testName)
    {
        super(testName);
    }
    @Override
    protected void setUp()
    {
        mainWindow = MainWindowOperator.getDefault();
        dView = new TopComponentOperator("Design View");
        dView.clickMouse();
        tco = new TopComponentOperator("Document Items");
        treeOp = new JTreeOperator(tco.getWindowContainerOperator());
        tco = new TopComponentOperator("Table View");
        tableOp = new JTableOperator(tco);
    }
    
    public static Test suite() 
    {
        Configuration testConfig = NbModuleSuite.createConfiguration(
                GuiTestingIterationFive.class);
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }
    
    public void testRandomizeOptions()
    {
        System.out.println("Randomize Options");
        DialogOperator window;
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 8).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 6).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 8).push();
        
        //test Randomize All
        tco = new TopComponentOperator("Table View");
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 0).push();
        new JButtonOperator(window, 0).push();
        
        //test Randomize Selected
        tableOp.selectAll();
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 1).push();
        new JButtonOperator(window, 0).push();
        
        //test Randomize Multiple Choice
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 2).push();
        new JButtonOperator(window, 0).push();
        
        //test Randomize Matching
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 3).push();
        new JButtonOperator(window, 0).push();
        
        //test Randomize Matching
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 4).push();
        new JButtonOperator(window, 0).push();
        
        //test cancel
        new JButtonOperator(tco, 6).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 0).push();
        new JButtonOperator(window, 1).push();
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
      
    /**
     * Test of extractItem method, of class SectionTitleView.
     */
    public void testBold()
    {
        System.out.println("WYSIWYG Bold");
        
        treeOp.selectRow(treeOp.findRow("Section Title"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco.getWindowContainerOperator());
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        //push bold
        new JButtonOperator(tco, 0).push();
        Boolean result1 = textField.getText().contains("<b>");
        assertTrue(result1);
        //push add
        new JButtonOperator(tco, 5).push();
        Boolean result2 = tableOp.getValueAt(0,3).toString().equals("ST"); 
        assertTrue(result2);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    
    public void testItalic()
    {
        System.out.println("WYSIWYG Italic");
        
        treeOp.selectRow(treeOp.findRow("Section Title"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco.getWindowContainerOperator());
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        //push bold
        new JButtonOperator(tco, 1).push();
        Boolean result1 = textField.getText().contains("<i>");
        assertTrue(result1);
        //push add
        new JButtonOperator(tco, 5).push();
        Boolean result2 = tableOp.getValueAt(0,3).toString().equals("ST"); 
        assertTrue(result2);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testUnderline()
    {
        System.out.println("WYSIWYG Underline");
        
        treeOp.selectRow(treeOp.findRow("Section Title"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco.getWindowContainerOperator());
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        //push bold
        new JButtonOperator(tco, 2).push();
        Boolean result1 = textField.getText().contains("<u>");
        assertTrue(result1);
        //push add
        new JButtonOperator(tco, 5).push();
        Boolean result2 = tableOp.getValueAt(0,3).toString().equals("ST"); 
        assertTrue(result2);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testMonospace()
    {
        System.out.println("WYSIWYG Monospace");
        
        treeOp.selectRow(treeOp.findRow("Section Title"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco.getWindowContainerOperator());
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        new JButtonOperator(tco, 3).push();
        Boolean result1 = textField.getText().contains("<font");
        assertTrue(result1);
        //push add
        new JButtonOperator(tco, 5).push();
        Boolean result2 = tableOp.getValueAt(0,3).toString().equals("ST"); 
        assertTrue(result2);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    
    /*****************************************************************/
    public void testRandomizeMC()
    {
        System.out.println("Randomize MC Ordering");
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 1).push();
        //Fill text boxes with one gap
        new JTextPaneOperator(tco, 0).typeText("Item One");
        new JTextPaneOperator(tco, 1).typeText("Item Two");
        new JTextPaneOperator(tco, 2).typeText("Item Three");
        new JTextPaneOperator(tco, 4).typeText("Item Four");
        //add three more choices
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        //fill two more boxes
        new JTextPaneOperator(tco, 5).typeText("Item Five");
        new JTextPaneOperator(tco, 7).typeText("Item Six");
        //deactivate Item Two
        new JCheckBoxOperator(tco, 1).push();
        new JButtonOperator(tco, 0).push();
        //Ensure that the randomize happened by checking the empty box position
        Boolean stringBool = new JTextPaneOperator(tco, 7).getText().contains("Item Two");
        
        new JButtonOperator(tco, 10).push();
        
        //Close procedure
        assertFalse(stringBool);
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
        //new JButtonOperator(new DialogOperator(), 2).push();
    }
    
    public void testRandomizeMC2()
    {
        System.out.println("Randomize MC Ordering");
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 1).push();
        //Fill text boxes with one string
        new JTextPaneOperator(tco, 3).typeText("Item One");
        //add three more choices
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        //fill two more boxes
        new JTextPaneOperator(tco, 5).typeText("Item Two");
        new JTextPaneOperator(tco, 7).typeText("Item Three");
        //deactivate Items 2 3 and 4
        new JCheckBoxOperator(tco, 5).clickMouse();
        new JCheckBoxOperator(tco, 6).push();
        new JCheckBoxOperator(tco, 7).push();
        new JButtonOperator(tco, 0).push();
        //Ensure that the randomize properly ordered the answers
        new JButtonOperator(tco, 10).push();
        tableOp.selectCell(0,2);
        //assertEquals(0, new JTextPaneOperator(tco, 3).getText().length());
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
        //new JButtonOperator(new DialogOperator(), 2).push();
    }
    
    public void testAddRemoveAnswersMC()
    {
        System.out.println("Add and Remove MC Answers");
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 5).push();
        new JButtonOperator(tco, 4).push();
        new JButtonOperator(tco, 3).push();
        new JButtonOperator(tco, 2).push();
        
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        new JButtonOperator(tco, 1).push();
        
        assertFalse(new JButtonOperator(tco, 1).isEnabled());
        
        new JButtonOperator(tco, 17).push();
        
        tableOp.selectCell(0, 1);
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        new JButtonOperator(tco, 2).push();
        
        //update empty question
        new JButtonOperator(tco, 2).push();
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
}

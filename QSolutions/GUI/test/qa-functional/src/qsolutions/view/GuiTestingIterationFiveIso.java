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
public class GuiTestingIterationFiveIso extends JellyTestCase
{    
    private MainWindowOperator mainWindow;
    private TopComponentOperator tco;
    private TopComponentOperator dView;
    private JTreeOperator treeOp;
    private JEditorPaneOperator textField;
    private JTableOperator tableOp;
    private JTreeOperator op;
            
    public GuiTestingIterationFiveIso(String testName)
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
                GuiTestingIterationFiveIso.class);
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }
    
    public void testFilter()
    {
        System.out.println("Filter");
        DialogOperator window;
        
        //Add a normal question
   /*     treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("Category");
        new JButtonOperator(tco, 8).push();
        */
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("");
        new JButtonOperator(tco, 6).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 8).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("Category");
        new JButtonOperator(tco, 7).push();
        
        //Test filter all category with all category selected
        tco = new TopComponentOperator("Table View");
        new JButtonOperator(tco, 5).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 0).push();//select all categories
        new JRadioButtonOperator(window, 1).push();//set to filter category
        new JButtonOperator(window, 0).push();//Press filter
        //TODO Check active boxes?
        
        //Test filter all level with no level selected
        new JButtonOperator(tco, 5).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 0).push();//select all categories
        new JRadioButtonOperator(window, 0).push();//set to filter level
        new JButtonOperator(window, 0).push();//Press filter
        //TODO Check active boxes?
        
        //Test filter all level with all levels selected
        new JButtonOperator(tco, 5).push();
        window = new DialogOperator();
        new JRadioButtonOperator(window, 0).push();//set to filter level
        new JButtonOperator(window, 0).push();//Press filter
        //TODO Check active boxes?
        
        //Test filter both with everything selected
        new JButtonOperator(tco, 5).push();
        window = new DialogOperator();
        new JCheckBoxOperator(window, 0).push();//select all levels
        new JRadioButtonOperator(window, 2).push();//set to filter both
        new JButtonOperator(window, 0).push();//Press filter
        //TODO Check active boxes?
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    
    
    /*public void testGlobalDocSettings()
    {
        System.out.println("Global Document Settings");
        tco = new TopComponentOperator("Document View");
        tco.makeComponentVisible();
        tco = new TopComponentOperator("Properties Window");
        //TODO Test prop table here
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }*/
    
    
    //Invalid - must be done manually
    /*public void testRadioButtonCheckboxRegisterChanges()
    {
        System.out.println("RadioButton/Checkbox Register a Change");
        treeOp.selectRow(treeOp.findRow("Fill-In-The-Blanks"));
        tco = new TopComponentOperator("Design View");
        new JCheckBoxOperator(tco, 0).push();
        
        //new JButtonOperator(new JDialogOperator(), 0).push();
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }*/
    
    public void testHeaderFooterPreserveValues()
    {
        System.out.println("Header/Footer Preserve Values");
        new JButtonOperator(mainWindow, 5).push();
        DialogOperator window = new DialogOperator();
        new JEditorPaneOperator(window, 0).typeText("This is box 1.");
        new JEditorPaneOperator(window, 1).typeText("This is box 2.");
        new JCheckBoxOperator(window, 1).push();
        new JButtonOperator(window, "Ok").push();
        
        //check values
        new JButtonOperator(mainWindow, 5).push();
        window = new DialogOperator();
        String result = new JEditorPaneOperator(window, 0).getText();
        String result2 = new JEditorPaneOperator(window, 1).getText();
        Boolean result3 = new JCheckBoxOperator(window, 1).isSelected();
        //Close the window
        new JButtonOperator(window, "Ok").push();
        //Check values
        assertTrue(result.contains("This is box 1."));
        assertTrue(result2.contains("This is box 2."));
        assertFalse(result3);
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testRandomizeMCCommit()
    {
        System.out.println("Randomize MC Before Commit");
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        //Test Regular randomize beofre commit
        new JButtonOperator(tco, 1).push();
        new JTextPaneOperator(tco, 0).typeText("Item One");
        new JTextPaneOperator(tco, 1).typeText("Item Two");
        new JTextPaneOperator(tco, 2).typeText("Item Three");
        new JTextPaneOperator(tco, 4).typeText("Item Four");
        
        new JButtonOperator(tco, 0).push();
        //Ensure that the randomize happened by checking the empty box position
//        assertFalse(new JTextPaneOperator(tco, 4).getText().contains("Item"));
        new JButtonOperator(tco, 7).push();
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
        //new JButtonOperator(new DialogOperator(), 2).push();
    }
    
    public void testFileNameAsterisk()
    {
        System.out.println("File Name Asterisk");
        //Generate expected default title
        new JButtonOperator(mainWindow, 0).push();
        
        //add an item to generate change
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        //push add
        new JButtonOperator(tco, 6).push();
        
        //check for asterisk in title bar
        assertEquals("*NewExam - Questionable ExamMakr", mainWindow.getTitle());
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testFileNameInTitle()
    {
        System.out.println("File Name in Title");
        //Generate expected default title
        new JButtonOperator(mainWindow, 0).push();
        assertEquals(mainWindow.getTitle(), "NewExam - Questionable ExamMakr");
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
    }
    
    public void testNumberingRestart()
    {
        System.out.println("NumberingRestart");
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        //push add
        new JButtonOperator(tco, 6).push();
        
        //Add a numbering restart
        treeOp.selectRow(treeOp.findRow("Numbering Restart"));
        //push add
        new JButtonOperator(tco, 0).push();
        
        //Add another normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        //push add
        new JButtonOperator(tco, 6).push();
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
        
    public void testMatchingQuestion()
    {
        System.out.println("Matching Question Type");
        treeOp.selectRow(treeOp.findRow("Matching"));
        tco = new TopComponentOperator("Design View");
        //set fields
        new JTextPaneOperator(tco, 0).typeText("Item One");
        new JTextPaneOperator(tco, 1).typeText("Answer One");
        new JTextPaneOperator(tco, 2).typeText("Item Two");
        new JTextPaneOperator(tco, 3).typeText("Answer Two");
        
        String s1 = new JTextPaneOperator(tco, 0).getText();
        
        // Randomize
        new JButtonOperator(tco, 0).push();
                
        String t0 = new JTextPaneOperator(tco, 0).getText();
        String t1 = new JTextPaneOperator(tco, 1).getText();
        String t2 = new JTextPaneOperator(tco, 2).getText();
        String t3 = new JTextPaneOperator(tco, 3).getText();
        
        
        new JButtonOperator(tco, 7).push();
        
        //String s2 = new JTextPaneOperator(tco, 0).getText();
        
        assertTrue(s1.contains("Item One"));
       
        assertTrue(t0.contains("Item One"));
        assertTrue(t1.contains("Answer One"));
        assertTrue(t2.contains("Item Two"));
        assertTrue(t3.contains("Answer Two"));
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        //new JButtonOperator(new DialogOperator(), 0).push();
       
       
    }
    
    
    public void testOrderQuestion()
    {
        System.out.println("Order Question Type");
        treeOp.selectRow(treeOp.findRow("Order"));
        tco = new TopComponentOperator("Design View");
        //push add
        new JTextPaneOperator(tco, 0).typeText("Item One");
        new JTextPaneOperator(tco, 1).typeText("Item Two");
        new JTextPaneOperator(tco, 2).typeText("Item Three");
        
        String s1 = new JTextPaneOperator(tco, 0).getText();
        
        // Randomize
        new JButtonOperator(tco, 0).push();
                
        String t0 = new JTextPaneOperator(tco, 0).getText();
        String t1 = new JTextPaneOperator(tco, 1).getText();
        String t2 = new JTextPaneOperator(tco, 2).getText();
        
        new JButtonOperator(tco, 7).push();
        
        String s2 = new JTextPaneOperator(tco, 0).getText();
        assertTrue(s1.contains("Item One"));       
        assertTrue(t0.contains("Item One"));
        assertTrue(t1.contains("Item Two"));
        assertTrue(t2.contains("Item Three"));
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        //new JButtonOperator(new DialogOperator(), 0).push();
    }   
    
    public void testTrueFalseDefault()
    {        
        System.out.println("Order Question Type");
        treeOp.selectRow(treeOp.findRow("True False"));
        tco = new TopComponentOperator("Design View");
        
        JRadioButtonOperator radio1 = new JRadioButtonOperator(tco, 1);
        JRadioButtonOperator radio2 = new JRadioButtonOperator(tco, 0);
        Boolean t1 = radio1.isSelected();
        Boolean t2 = radio2.isSelected();

        radio2.push();
        Boolean t3 = radio2.isSelected();
        Boolean t4 = radio1.isSelected();
        
        new JButtonOperator(tco, 0).push();
        assertTrue(t1);
        assertFalse(t2);

        assertTrue(t3);
        assertFalse(t4);
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
}

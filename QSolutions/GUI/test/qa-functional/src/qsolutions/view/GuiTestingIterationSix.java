package qsolutions.view;

import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.MainWindowOperator;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jemmy.operators.*;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;


/**
 * Tests features and defect fixes from iteration 6.
 * @author michaelmease, jake tobin
 */
public class GuiTestingIterationSix extends JellyTestCase
{    
    private MainWindowOperator mainWindow;
    private TopComponentOperator tco;
    private TopComponentOperator dView;
    private JTreeOperator treeOp;
    private JEditorPaneOperator textField;
    private JTableOperator tableOp;
    private JTreeOperator op;
            
    public GuiTestingIterationSix(String testName)
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
                GuiTestingIterationSix.class);
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }
    
    public void testExamStatistics()
    {
        System.out.println("Exam Statistics");
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("Category");
        new JButtonOperator(tco, 6).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("");
        new JButtonOperator(tco, 6).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 6).push();
        
        new JMenuBarOperator(mainWindow).pushMenu("Exam|Summary", "|");
        JDialogOperator jd = new JDialogOperator();
        new JButtonOperator(jd).push(); 
        
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testTitleGroupFormattingAndMarksTag()
    {
        System.out.println("TitleGroup Formatting And Marks Tag");
        new JButtonOperator(mainWindow, 6).push();
        DialogOperator window = new DialogOperator();
        textField = new JEditorPaneOperator(window, 4);
        textField.typeText("BEST TITLE");
        textField.selectText("BEST");
        new JButtonOperator(window, 3).push();
        Boolean result1 = textField.getText().contains("<b>");
        textField = new JEditorPaneOperator(window, 3);
        textField.typeText("MARKS ARE: <m>");
        Boolean result2 = textField.getText().contains("&lt;m&gt;");
        //Close the window
        new JButtonOperator(window, "Ok").push();
        
        //Check values
        assertTrue(result1);
        assertTrue(result2);
        
        //close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testFullFormattingQuestion()
    {
        System.out.println("Question Formatting");
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco, 1);
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        //push bold
        new JButtonOperator(tco, 0).push();
        Boolean result1 = textField.getText().contains("<b>");
        assertTrue(result1);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
//        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testPaste()
    {
        System.out.println("Off by One (Paste)");
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("Category");
        new JTextPaneOperator(tco, 1).typeText("This is the answer.");
        new JButtonOperator(tco, 8).push();
        
        //Add a normal question
        /*treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("");
        new JButtonOperator(tco, 6).push();
        
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Free Response"));
        tco = new TopComponentOperator("Design View");
        new JButtonOperator(tco, 8).push();
        */
        //select the free response and copy it
        tableOp.selectCell(0,2);
        tco = new TopComponentOperator("Table View");
        //copy
        new JButtonOperator(tco, 3).push();
        //paste
        new JButtonOperator(tco, 4).push();
        
        tableOp.selectCell(0,2);
        tco = new TopComponentOperator("Design View");
        String string1 = new JTextPaneOperator(tco, 1).getText();
                
        tco = new TopComponentOperator("Table View");
        tableOp.selectCell(1,2);
        tco = new TopComponentOperator("Design View");
        String string2 = new JTextPaneOperator(tco, 1).getText();
        assertEquals(string1, string2);
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    public void testTableLevelEditable()
    {
        System.out.println("Table Level Field Uneditable");
        //Add a normal question
        treeOp.selectRow(treeOp.findRow("Multiple Choice"));
        tco = new TopComponentOperator("Design View");
        new JTextFieldOperator(tco, 1).setText("Level");
        new JTextFieldOperator(tco, 2).setText("Category");
        new JButtonOperator(tco, 6).push();
        
        for(int a = 0; a < 9; a++)
        {
            if(tableOp.getColumnName(a).equals("Level"))
            {
                assertFalse(tableOp.isCellEditable(0, a));
            }
        }
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }
    
    
   /* public void testFullFormattingInstruction()
    {
        System.out.println("Question Formatting");
        treeOp.selectRow(treeOp.findRow("Divider"));
        tco = new TopComponentOperator("Design View");
        textField = new JEditorPaneOperator(tco);
        textField.typeText("This is a TEST string.");
        textField.selectText("TEST");
        //push bold
        new JButtonOperator(tco, 0).push();
        Boolean result1 = textField.getText().contains("<b>");
        assertTrue(result1);
        
        //Close procedure
        new JButtonOperator(mainWindow, 0).push();
        new JButtonOperator(new DialogOperator(), 0).push();
    }*/
    
    
    
}

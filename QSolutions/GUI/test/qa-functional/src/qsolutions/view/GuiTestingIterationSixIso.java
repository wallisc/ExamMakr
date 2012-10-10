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
public class GuiTestingIterationSixIso extends JellyTestCase
{    
    private MainWindowOperator mainWindow;
    private TopComponentOperator tco;
    private TopComponentOperator dView;
    private JTreeOperator treeOp;
    private JEditorPaneOperator textField;
    private JTableOperator tableOp;
    private JTreeOperator op;
            
    public GuiTestingIterationSixIso(String testName)
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
                GuiTestingIterationSixIso.class);
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }
    
}

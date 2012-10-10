/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import junit.framework.TestCase;

/**
 *
 * @author Chris
 */
public class HTMLEditorKitWrapperTest extends TestCase
{
    public HTMLEditorKitWrapperTest(String testName)
    {
        super(testName);
    }

    public void testConstructor()
    {
        assertTrue(new HTMLEditorKitWrapper() != null);
    }
}

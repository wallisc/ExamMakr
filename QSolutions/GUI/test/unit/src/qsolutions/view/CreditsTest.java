package qsolutions.view;

import junit.framework.TestCase;

/**
 *
 * @author coreyf
 */
public class CreditsTest extends TestCase
{
    Credits credits;
    public CreditsTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        credits = new Credits();
    }
    
    public void testCreditsDefect289() 
    {
        assertTrue(credits.getCredits().contains("Ryan Johnston (ryjohnst)"));
    }
    
    public void testCreditsDefect290() 
    {
        assertTrue(credits.getCredits().contains("Jake Tobin (jtobin)"));
    }
    
    public void testCreditsDefect291() 
    {
        assertTrue(credits.getCredits().contains("Corey Farwell (cfarwell)"));
    }
    
    public void testCreditsDefect286() 
    {
        assertTrue(credits.getCredits().contains("Michael Mease, (mmease)"));
    }
    
    public void testCreditsDefect287()
    {
        assertTrue(credits.getCredits().contains("Ryan Dollahon, (rdollaho)"));
    }
    
    public void testCreditsDefect288()
    {

        assertTrue(credits.getCredits().contains("Chris Wallis, (cwallis)"));
    }
}

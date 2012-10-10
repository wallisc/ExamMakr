package qsolutions.exam;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;

/**
 * Tests the ItemImage class methods
 * @author michaelmease
 */
public class ItemImageTest extends TestCase
{
    private ItemImage image;
    private File file;
    
    public ItemImageTest(String testName)
    {
        super(testName);
    }
    
    @Override
    public void setUp() throws Exception
    {
        file = new File(ItemImageTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "bold.png");
        image = new ItemImage(file);
    }

    /**
     * Test of setImage method, of class ItemImage.
     */
    public void testSetImage() throws Exception
    {
        
        System.out.println("setImage");
        file = new File(ItemImageTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "underline.png");
        image.setImage(file);
        assertEquals("underline.png", image.getImageFile().getName());
    }

    /**
     * Test of getImageFile method, of class ItemImage.
     */
    public void testGetImageFile()
    {
        System.out.println("getImageFile");
        try 
        {
            image.setImage(file);
        }
        catch (IOException ex)
        {
            fail("Cannot find file. Directories changed in the project.");
        }
        assertEquals(file, image.getImageFile());
    }

    /**
     * Test of getImage method, of class ItemImage.
     */
    public void testGetImage()
    {
        System.out.println("getImage");
        assertFalse(image.getImage() == null);
    }

    /**
     * Test of writeToTempFile method, of class ItemImage.
     */
    public void testWriteToTempFile() throws Exception
    {
        System.out.println("writeToTempFile");
        image.writeToTempFile();
        assertEquals("bold.png", image.getImageFile().getName().substring(0, 8));
        ItemImage instance = new ItemImage();
        try
        {
            instance.writeToTempFile();
        }
        catch(NullPointerException e)
        {
            assertEquals(null, instance.getImageFile());
        }
    }

    /**
     * Test of deleteTempFile method, of class ItemImage.
     */
    public void testDeleteTempFile() throws Exception
    {
        System.out.println("deleteTempFile");
        image.writeToTempFile();
        image.deleteTempFile();
        assertEquals(false, image.getImageFile().exists());
    }
    
    public void testSetAndGetScale()
    {
        System.out.println("setScale and getScale");
        assertEquals(1F, image.getScale());
        image.setScale(110F);
        assertEquals(110F, image.getScale());
    }
}

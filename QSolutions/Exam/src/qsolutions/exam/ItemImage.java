
package qsolutions.exam;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 * This class stores an image as a byte array and allows for the creation and
 * access of the image in a temporary area.
 * @author michaelmease
 */
public class ItemImage implements Serializable
{
    private byte[] imageData;
    private String name;
    private File imageLink;
    private float scale;
    
    /**
     * Creates a new ItemImage with no image loaded.
     */
    public ItemImage()
    {
        imageData = null;
        name = "temp.img";
        scale = 1F;
    }

    
    /**
     * Constructs a new image with data initialized to the file pointed to by
     * path.
     * Precondition: The file File is an image file
     * @param file The File to store
     * Precondition: The file File is an image file
     * @throws IOException thrown if an read error occurs
     */
    public ItemImage(File file) throws IOException
    {
        imageData = extractBytes(file);
        imageLink = file;
        this.name = file.getName();
        scale = 1F;
    }
    
    /**
     * Sets the image of the Question to a file specified by path.
     * @param file The file to store
     * @exception IOException throws if there is a problem reading the file
     */
    public void setImage(File file) throws IOException
    {
        imageData = extractBytes(file);
        this.name = file.getName();
        imageLink = file;
    }
    
    /**
     * Returns the File containing the image in the temporary 
     * folder on the system.
     * @return A File containing an image
     */
    public File getImageFile()
    {
        return imageLink;
    }
    
    /**
     * Extracts bytes from an image at the location path.
     * @param path the path to the file to be extracted
     * @return the new byte array
     * @throws IOException if the file is not found
     */
    private byte[] extractBytes(File file) throws IOException
    {
        // open image
        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        //If the file contained bad data or there was a read error
        if (bufferedImage == null)
        {
            throw new IOException();
        } 
        // get DataBufferBytes from Raster
        ImageIO.write(bufferedImage, "png", bas);
        bas.flush();
        return ( bas.toByteArray() );
    }
    
    /**
     * Returns the byte array containing the current image data.
     * @return the image data
     */
    public byte[] getImage()
    {
        return imageData;
    }
    
    /**
     * Writes the data stored in imageData to a temp file given the name 
     * stored in the name instance variable of this class.
     * @throws IOException If an error occurs writing to the temp file
     * @throws NullPointerException If the imageData is null
     */
    public void writeToTempFile() throws IOException
    {
        //If no file has been loaded yet, throw an exception
        if(imageData == null)
        {
            throw new NullPointerException("This image does not contain data.");
        }
        imageLink = File.createTempFile(name, ".img");
        imageLink.deleteOnExit();
        InputStream in = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", imageLink);
    }
    
    /**
     * Deletes the temporary file associated with this ItemImage.
     */
    public void deleteTempFile()
    {
        imageLink.delete();
    }
    
    /**
     * Sets the value of the image scale as a percentage.
     * @param value The new scale of the image
     */
    public void setScale(Float value)
    {
        scale = value;
    }
    
    /**
     * Returns the value of the current image scale.
     * @return The scale of the image
     */
    public float getScale()
    {
        return scale;
    }
    

}

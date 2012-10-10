/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.api;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Class with data and methods for storing and displaying the exam title group.
 * @author Jake
 */
public interface TitleGroupApi extends Serializable
{    
    /**
     * Get the title of the file
     * 
     * @return title of the file
     */
    String getTitle();
    
    /**
     * Get top left cell of title group
     *
     * @return Top left cell of title group
     */
    String getTopLeftCell();
    
    /**
     * Get top right cell of title group
     *
     * @return Top right cell of title group
     */
    String getTopRightCell();
    
    /**
     * Get bottom left cell of title group
     *
     * @return Bottom left cell of title group
     */
    String getBottomLeftCell();
    
    /**
     * Get bottom right cell of title group
     *
     * @return Bottom right cell of title group
     */
    String getBottomRightCell();
    
    /**
     * Get whether or not to show the title group
     * 
     * @return Whether the title group is visible
     */
    boolean getShowTitleGroup();
    
    /**
     * Returns the font size of the title description that is placed at the top
     * of the exam
     * @return the font size
     */
    int getTitleGroupFontSize();
    
    /**
     * Returns whether or not to override the title group instructions font
     * size with the global setting.
     * 
     * @return whether or not to use the global font size.
     */
    public boolean getTitleGroupFontGlobal();
    
    /**
     * Get Title Group return HTML of the title group.
     *
     * @return The string of html
     */
    public String getTitleGroupHtml();
            
    /**
     * Sets whether or not to override the title group instructions font
     * size with the global setting.
     * @param toggle true to use global font size, false to use title group size
     */
    public void setTitleGroupFontGlobal(boolean toggle);
    
    /**
     * Returns the font size of the title description that is placed at the top
     * of the exam
     * 
     * @return the font size
     */
    int getTitleFontSize();
    
    /**
     * Set whether the title group will be shown or not.
     * 
     * @param show Boolean to determine if the title group should be shown
     */
    void setShowTitleGroup(boolean show);
    
    /**
     * Sets the title of the test
     *
     * @param title The string to be displayed at the top of the test
     */
    void setTitle(String title);
    
    /**
     * Set the description of the test
     *
     * @param desc The string to describe the content/instruction of the test
     */
    void setDescription(String desc);
    
    /**
     * Gets the description from the exam
     * 
     * @return String description
     */
    String getDescription();
    
    /**
     * Set top left cell of title group
     *
     * @param cell String for top left cell.
     */
    void setTopLeftCell(String cell);
    
    /**
     * Set top right cell of title group
     *
     * @param cell String for top right cell.
     */
    void setTopRightCell(String cell);
    
    /**
     * Set bottom left cell of title group
     *
     * @param cell String for bottom left cell.
     */
    void setBottomLeftCell(String cell);
    
    /**
     * Set bottom right cell of title group
     *
     * @param cell String for bottom left right.
     */
    void setBottomRightCell(String cell);
    
    /**
     * Sets the font size for the title description placed at the top of the 
     * exam
     * 
     * @param fontSize the size to change the font to 
     */
    void setTitleGroupFontSize(int fontSize);
    
    /**
     * Sets the font size for the title description placed at the top of the 
     * exam
     * 
     * @param fontSize the size to change the font to 
     */
    void setTitleFontSize(int fontSize);
    
}

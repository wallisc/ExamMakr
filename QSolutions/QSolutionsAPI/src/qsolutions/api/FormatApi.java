package qsolutions.api;

import java.io.File;
import java.io.Serializable;
import java.util.Observer;

/**
 * The Format interface for use with LookUp. Only contains methods needed
 * externally.
 */
public interface FormatApi extends Serializable
{
    /**
     * Gets the closing sections of the HTML
     *
     * @return closing sections of the HTML
     */
    public String getHtmlBodyEnd();
    
    /**
     * Gets the opening sections of the HTML.
     * 
     * @return opening sections of the HTML
     */
    public String getHtmlBodyStart();
    
    /**
     * Gets the HTML representation of the footer.
     *
     * @param left Left section of the footer
     * @param middle Middle section of the footer
     * @param right Right section of the footer
     * @return HTML representation of the footer
     */
    public String getHtmlFooter(String left, String middle, String right);
    
    /**
     * Gets the HTML representation of the footer from existing object
     *
     * @param hf HeaderFooter object
     * @return HTML representation of the footer
     */
    public String getHtmlFooter(HeaderFooterApi hf);
            
    /**
     * Gets the HTML representation of the header
     *
     * @param left Left section of the header
     * @param middle Middle section of the header
     * @param right Right section of the header
     * @return HTML representation of the header
     */
    public String getHtmlHeader(String left, String middle, String right);
    
    
    /**
     * Gets the HTML representation of the header from existing object
     *
     * @param hf HeaderFooter object
     * @return HTML representation of the header
     */
    public String getHtmlHeader(HeaderFooterApi hf);
            
    /**
     * Gets the HTML representation of a mark.
     *
     * @param marks Numerical mark
     * @param pos Where the mark should be wrapped in parenthesis
     * @param paren Whether the marks 
     * @return HTML representation of a mark
     */
    public String getMarksHtml(int marks, Object pos, 
            boolean paren);
    
    /**
     * Accesses the property table to return a given property of the exam.
     *
     * @param prop The Property to access
     * @return An object representing the property requested
     */
    public Object getProperty(Property prop);
    
    /**
     * Gets the CSS representation of the exam.
     * @return CSS representation of the exam
     */
    public String getStyleBlock();
    
    /**
     * Accesses the property table to return a given property of the exam.
     *
     * @param prop The Property to access
     * @param ob Object whose property will be set
     */
    public void setProperty(Property prop, Object ob);
  
    /**
     * Values that indicate properties of formatting for an exam.
     */
    public enum Property
    {
        /** How the marks should be positioned */
        MarksPosition,
        
        /** How choices for multiple choice questions should be positioned */
        MCPosition,
        
        /** How choices for multiple choice questions should be numbered */
        MCNumberingType,
        
        /** Whether answer lines should be shown */
        GlobalFontSize,
        
        /** Whether answer lines should be shown */
        GlobalFontFace,
        
        /** Whether answer lines should be shown */
        ShowAnswerLines,
        
        /** Whether parenthesis should be shown */
        ShowParen;
    }
    
    /**
     * Get marks position index from enum based on parameter 
     * @param p enum to match
     * @return marks int index
     */
    public int getMarksPositionIndex(Object p);
    /**
     * Match marks position index to enum based on parameter 
     * @param p int to match
     * @return marks enum type
     */
    public Object getMarksPositionFromInt(int p);
    
    /**
     * Get MC Numbering type index from enum based on parameter 
     * @param p enum to match
     * @return number type index
     */
    public int getMcNumberingTypeIndex(Object p);
    /**
     * Match MC Numbering type index to enum based on parameter 
     * @param p int to match
     * @return return number type enum
     */
    public Object getMcNumberingTypeFromInt(int p);
    
    /**
     * Horizontal alignment parameter
     * @return boolean true for vertical
     */
    public Boolean getHorizontalAlignment();
    
    /**
     * Add an observer
     * @param obs the observer 
     */
    public void addObserver(Observer obs);
    
    /**
     * Returns format to its initial state
     */
    public void newFormat();
    
    /**
     * Sets this Format to match that passed in
     * @param toBe the Format for this to match
     */
    public void newFormat(FormatApi toBe);
    
    /**
     * Get page width for images
     * @return Page Width
     */
    public float getPageWidth();
    
    
    /**
     * Get Image HTML for this question based on format.
     *
     * @param image image to add to exam
     * @param scale scale of the image
     * @param pos image position (left, middle, right)
     * @return The html for the image in String format
     */
    public String getImageHtml(File image, float scale, int pos);
    
    /**
     * Get scale based on width.
     * @param width The width of the image
     * @return The page scale
     */
    public float getPageScale(int width);
    
}

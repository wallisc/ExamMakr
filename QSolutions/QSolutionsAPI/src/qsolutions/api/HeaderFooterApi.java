/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.api;

import java.io.Serializable;

/**
 * API for header / footer class
 * @author Jake
 */
public interface HeaderFooterApi extends Serializable
{
    /**
     * Gets the left header content
     * @return left header content
     */
    String getHeaderContentLeft();

    /**
     * Gets the middle header content
     * @return middle header content
     */
    String getHeaderContentMiddle();

    /**
     * Gets the right header content
     * @return right header content
     */
    String getHeaderContentRight();

    /**
     * Gets the left footer content
     * @return left footer content
     */
    String getFooterContentLeft();
    
    /**
     * Gets the middle footer content
     * @return middle footer content
     */
    String getFooterContentMiddle();

    /**
     * Gets the right footer content
     * @return right footer content
     */
    String getFooterContentRight();

    /**
     * Gets whether the header is visible
     * @return Whether the header is visible
     */
    boolean getShowHeader();
    
    /**
     * Gets whether the footer is visible
     * @return Whether the footer is visible
     */
    boolean getShowFooter();

    /**
     * Sets whether the header should be visible
     * @param selected Whether the header should be visible
     */
    void setShowHeader(boolean selected);

    /**
     * Sets whether the footer should be visible
     * 
     * @param selected Whether the footer should be visible
     */
    void setShowFooter(boolean selected);

    /**
     * Sets the left footer header
     * @param content Content to be set as the left header
     */
    void setHeaderContentLeft(String content);
  
    /**
     * Sets the middle header content
     * @param content Content to be set as the middle header
     */
    void setHeaderContentMiddle(String content);

    /**
     * Sets the right header content
     * @param content Content to be set as the right header
     */
    void setHeaderContentRight(String content);
    
    /**
     * Sets the left footer content
     * @param content Content to be set as the left footer
     */
    void setFooterContentLeft(String content);

    /**
     * Sets the middle footer content
     * @param content Content to be set as the middle footer
     */
    void setFooterContentMiddle(String content);

 
    /**
     * Sets the right footer content
     * @param content Content to be set as the right footer
     */
    void setFooterContentRight(String content);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.Serializable;
import java.util.Observable;
import java.util.ResourceBundle;
import qsolutions.api.HeaderFooterApi;

/**
 * Methods and data relating to displaying the exam header and footer
 * @author Jake
 */
public class HeaderFooter extends Observable 
    implements Serializable, HeaderFooterApi
{
    // header / footer
    
    private boolean showFooter = true;
    private boolean showHeader = true;
    private String footerContentLeft = "";
    private String footerContentMiddle = "";
    private String footerContentRight = kBundle.getString("PAGE") +
            " &lt;pn&gt; " + kBundle.getString("OF") + " &lt;pc&gt;";
    private String headerContentLeft = "";
    private String headerContentMiddle = "";
    private String headerContentRight = "";
    
    private static final String kBundleName = "qsolutions/exam/Bundle";
    private static final ResourceBundle kBundle = ResourceBundle.getBundle(
            kBundleName);
    
    /**
     * Method to alert observers of updates
     */
    public void updated()
    {
        setChanged();
        notifyObservers();
        
    }

    /**
     * Gets the left header content
     * @return left header content
     */
    public String getHeaderContentLeft()
    {
        return this.headerContentLeft;
    }

    /**
     * Sets the left header content
     * @param content Content to be set as the left header
     */
    public void setHeaderContentLeft(String content)
    {
        this.headerContentLeft = content;
        updated();
    }

    /**
     * Gets the middle header content
     * @return middle header content
     */
    public String getHeaderContentMiddle()
    {
        return this.headerContentMiddle;
    }

    /**
     * Sets the middle header content
     * @param content Content to be set as the middle header
     */
    public void setHeaderContentMiddle(String content)
    {
        this.headerContentMiddle = content;
        updated();
    }

    /**
     * Gets the right header content
     * @return right header content
     */
    public String getHeaderContentRight()
    {
        return this.headerContentRight;
    }

    /**
     * Sets the right header content
     * @param content Content to be set as the middle header
     */
    public void setHeaderContentRight(String content)
    {
        this.headerContentRight = content;
        updated();
    }
    
    /**
     * Gets the left footer content
     * @return left footer content
     */
    public String getFooterContentLeft()
    {
        return this.footerContentLeft;
    }

    /**
     * Sets the left footer content
     * @param content Content to be set as the left footer
     */
    public void setFooterContentLeft(String content)
    {
        this.footerContentLeft = content;
        updated();
    }
    
    /**
     * Gets the middle footer content
     * @return middle footer content
     */
    public String getFooterContentMiddle()
    {
        return this.footerContentMiddle;
    }

    /**
     * Sets the middle footer content
     * @param content Content to be set as the middle footer
     */
    public void setFooterContentMiddle(String content)
    {
        this.footerContentMiddle = content;
        updated();
    }

    /**
     * Gets the right footer content
     * @return right footer content
     */
    public String getFooterContentRight()
    {
        return this.footerContentRight;

    }
    
    /**
     * Sets the right footer content
     * @param content Content to be set as the right footer
     */
    public void setFooterContentRight(String content)
    {
        this.footerContentRight = content;
        updated();
    }

    /**
     * Gets whether the header is visible
     * @return Whether the header is visible
     */
    public boolean getShowHeader()
    {
        return this.showHeader;
    }

    /**
     * Sets whether the header should be visible
     * @param show Whether the header should be visible
     */
    public void setShowHeader(boolean show)
    {
        this.showHeader = show;
        updated();
    }

    /**
     * Gets whether the footer is visible
     * @return Whether the footer is visible
     */
    public boolean getShowFooter()
    {
        return this.showFooter;
    }

    /**
     * Sets whether the footer should be visible
     * 
     * @param show Whether the footer should be visible
     */
    public void setShowFooter(boolean show)
    {
        this.showFooter = show;
        updated();
    }
    
}

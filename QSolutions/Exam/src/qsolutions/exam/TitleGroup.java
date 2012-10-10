/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.Serializable;
import java.util.Observable;
import java.util.ResourceBundle;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.DriverApi;
import qsolutions.api.ExamApi;
import qsolutions.api.FormatApi;
import qsolutions.api.TitleGroupApi;

/**
 * Exam Title Group
 * @author Jake
 */
@ServiceProvider(service = TitleGroupApi.class)
public class TitleGroup extends Observable 
    implements Serializable, TitleGroupApi
{    /*
     * Title Group
     */
    //Toggle whether or not to show the title group.
    private ExamApi exam;
    private boolean showTitleGroup = true;
    //The title of the test
    private String title = kBundle.getString("SAMPLE TITLE");
    //The test description displayed under title
    private String description = kBundle.getString("TITLE GROUP TEXT");
    // Top left cell ( Usually "Name" )
    private String topLeftCell = kBundle.getString("NAME: ");
    // Bottom left cell ( date? )
    private String bottomLeftCell = "";
    // Top Right Cell ( course )
    private String topRightCell = kBundle.getString("COURSE") + ": ";
    private String bottomRightCell = kBundle.getString("TOTAL MARKS") +
            ": &lt;m&gt;";
    private int titleGroupFontSize = kDefaultTitleGroupFontSize;
    private boolean titleGroupFontGlobal = true;
    private int titleFontSize = kDefaultTitleFontSize;
    
    private static final int kDefaultTitleFontSize = 24;
    private static final int kDefaultTitleGroupFontSize = 12;
    private final static int kAnswerCompareInt = -2;
    
    private static final String kBundleName = "qsolutions/exam/Bundle";
    private static final ResourceBundle kBundle = ResourceBundle.getBundle(
            kBundleName);
    
    /**
     * Default exam constructor.
     */
    public TitleGroup()
    {
        super();
    }
    /**
     * Construct a title group with parent exam
     * @param exam parent of title group
     */
    public TitleGroup(ExamApi exam)
    {
        this.exam = exam;
    }
    
    /**
     * Method to alert observers of updates
     */
    public void updated()
    {
        setChanged();
        notifyObservers();
        
    }

    /**
     * Returns the title of the test
     *
     * @return The title of the test
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Get top left cell of title group
     *
     * @return Top left cell of title group
     */
    public String getTopLeftCell()
    {
        return this.topLeftCell;
    }

    /**
     * Get top right cell of title group
     *
     * @return Top right cell of title group
     */
    public String getTopRightCell()
    {
        return this.topRightCell;
    }

    /**
     * Get bottom left cell of title group
     *
     * @return Bottom left cell of title group
     */
    public String getBottomLeftCell()
    {
        return this.bottomLeftCell;
    }

    /**
     * Get bottom right cell of title group
     *
     * @return string of bottom right cell of title group
     */
    public String getBottomRightCell()
    {
        return this.bottomRightCell;
    }

    /**
     * Get whether or not to show the title group
     * 
     * @return Whether the title group is visible
     */
    public boolean getShowTitleGroup()
    {
        return this.showTitleGroup;
    }

    /**
     * Returns the font size of the title description that is placed at the top
     * of the exam
     * @return the font size
     */
    public int getTitleGroupFontSize()
    {
        return titleGroupFontSize;
    }
    
    
    /**
     * Returns whether or not to override the title group instructions font
     * size with the global setting.
     * 
     * @return whether or not to use the global font size.
     */
    public boolean getTitleGroupFontGlobal()
    {
        return this.titleGroupFontGlobal;
    }
    
    /**
     * Replace the total marks symbol with the actual total marks.
     * @param in string to search and replace.
     * @return 
     */
    private String replaceMarks(String in)
    {
        String out =  in.replace("&lt;m&gt;", 
                ((Integer)exam.getTotalMarks()).toString());
        out = out.replace("<m>", ((Integer)exam.getTotalMarks()).toString());
        
        return out;
    }

  /**
     * Get Title Group return HTML of the title group.
     *
     * @return The string of html
     */
    public String getTitleGroupHtml()
    {
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        //If there is a title, generate title group.
        if (this.getShowTitleGroup())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("<table border=\"0\" cellpadding=\"0\" ").append(
                    "style=\"width:100%;\">").append("<tr><td>").append(
                    replaceMarks(driver.cleanHTML(
                    getTopLeftCell()))).append("</td><td>")
                    .append(replaceMarks(driver.cleanHTML(
                    this.getTopRightCell())))
                    .append("</td></tr>");
            // Check formatting, if needed, add
            if (!this.getBottomRightCell().equals("") || 
                    !this.getBottomLeftCell().equals(""))
            {
                sb.append("<tr><td>").append(replaceMarks(driver.cleanHTML(
                        getBottomLeftCell()))).append(
                        "</td><td>").append(replaceMarks(driver.cleanHTML(
                        getBottomRightCell())))
                        .append("</td></tr>");
            }
            sb.append("<tr><td colspan=\"2\"><h1><p style=\"font-size:");
            sb.append(getTitleFontSize());
            sb.append("pt;\">");
            sb.append(driver.cleanHTML(this.getTitle()))
                    .append("</p>").append("</h1></td></tr>");
            // If there is no description for the exam 
            if (this.getDescription() != null)
            {
                    
                    
                sb.append("<tr><td colspan=\"2\"><p style=\"font-size:");
                // If we are using the global font, adjust html
                if(titleGroupFontGlobal)
                {
                    sb.append(exam.getFormat().getProperty(
                            FormatApi.Property.GlobalFontSize));
                }
                else
                {
                    sb.append(this.titleGroupFontSize);
                }
                sb.append("pt;\">").append( 
                        driver.cleanHTML(this.getDescription()))
                        .append("</p></td></tr>");
            }
            sb.append("</table>");
            return sb.toString();
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Sets whether or not to override the title group instructions font
     * size with the global setting.
     * @param toggle value for whether or not we override global font
     */
    public void setTitleGroupFontGlobal(boolean toggle)
    {
        titleGroupFontGlobal = toggle;
        updated();
    }
    
    
    /**
     * Returns the font size of the title description that is placed at the top
     * of the exam
     * 
     * @return the font size
     */
    public int getTitleFontSize()
    {
        return titleFontSize;
    }

    /**
     * Set whether the title group will be shown or not.
     * 
     * @param show Boolean to determine if the title group should be shown
     */
    public void setShowTitleGroup(boolean show)
    {
        this.showTitleGroup = show;
        updated();
    }

    /**
     * Sets the title of the test
     *
     * @param title The string to be displayed at the top of the test
     */
    public void setTitle(String title)
    {
        this.title = title;
        updated();
    }

    /**
     * Set the description of the test
     *
     * @param desc The string to describe the content/instruction of the test
     */
    public void setDescription(String desc)
    {
        description = desc;
        updated();
    }

    /**
     * Returns the description of the test.
     *
     * @return The description of the test
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Set top left cell of title group
     *
     * @param cell String for top left cell.
     */
    public void setTopLeftCell(String cell)
    {
        this.topLeftCell = cell;
        updated();
    }

    /**
     * Set top right cell of title group
     *
     * @param cell String for top right cell.
     */
    public void setTopRightCell(String cell)
    {
        this.topRightCell = cell;
        updated();
    }

    /**
     * Set bottom left cell of title group
     *
     * @param cell String for bottom left cell.
     */
    public void setBottomLeftCell(String cell)
    {
        this.bottomLeftCell = cell;
        updated();
    }

    /**
     * Set bottom right cell of title group
     *
     * @param cell String for bottom right cell
     */
    public void setBottomRightCell(String cell)
    {
        this.bottomRightCell = cell;
        updated();
    }
    
    /**
     * Sets the font size for the title description placed at the top of the 
     * exam
     * @param fontSize the size to change the font to 
     */
    public void setTitleGroupFontSize(int fontSize)
    {
        titleGroupFontSize = fontSize;
        updated();
    }
    
    /**
     * Sets the font size for the title description placed at the top of the 
     * exam
     * @param fontSize the size to change the font to 
     */
    public void setTitleFontSize(int fontSize)
    {
        titleFontSize = fontSize;
        updated();
    }
}

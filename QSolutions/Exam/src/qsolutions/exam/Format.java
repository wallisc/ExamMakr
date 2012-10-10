package qsolutions.exam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Observable;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import property.AnswerIndexing;
import property.FontFace;
import property.FontSize;
import property.Marks;
import qsolutions.api.ExamApi;
import qsolutions.api.FormatApi;
import qsolutions.api.HeaderFooterApi;
import qsolutions.api.properties.*;

/**
 * This class stores formatting data for entire tests, not individual questions.
 * This includes line spacing, text formatting, and title formatting.
 */
@ServiceProvider(service = FormatApi.class)
public class Format extends Observable implements Serializable, FormatApi
{
    private static final String kBundleName = "qsolutions/exam/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    //The table storing the properties of the exam
    private EnumMap<Property, Object> formatTable = 
            new EnumMap<Property, Object>(Property.class);
    
    /** Predefined Page Width in pixels*/
    public static final int kPageWidth = 575;
    
    /** Whether there should be horizontal alignment */
    public static final boolean kHorizontalAlignment = true;
    
    /** Whether there should be vertical alignment */
    public static final boolean kVerticalAlignment = false;
    
    /** Whether answer lines should be hidden */
    public static final boolean kHideAnswerLines = false;
    
    /** Whether answer lines should be shown */
    public static final boolean kShowAnswerLines = true;
    
    /** Whether parenthesis should be shown*/
    public static final boolean kShowParenthesis = true;
    
    /** Left margin offset for design items */
    public static final double kDesignItemOffset = -1.5;
    
    /** Default left margin for left marks */
    public static final int kLeftMargin = 5;
    
    /** Default left margin for left marks */
    public static final double kDLeftMargin = 1.5;
    
    /** The integer associated with three */
    public static final int kThree = 3;
    
    /** The integer associated with four */
    public static final int kFour = 4;
    
    /** Approved Serif Fonts */
    public static final String kSerifFonts = "\"Times New Roman\", "
            + "\"DejaVu Serif\", Georgia, serif";
    
    /** Approved Sans Serif fonts */
    public static final String kSansFonts = "Verdana, Geneva, sans-serif";

    /** Numbers for multiple choice numbering */
    public static final String[] kNumArray =
    {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
        "14", "15"
    };

    /** Lowercase letters for multiple choice numbering */
    public static final String[] kLowCharArray =
    {
        kBundle.getString("A"), kBundle.getString("B"), kBundle.getString("C"),
        kBundle.getString("D"), kBundle.getString("E"), kBundle.getString("F"),
        kBundle.getString("G"), kBundle.getString("H"), kBundle.getString("I"),
        kBundle.getString("J"), kBundle.getString("K"), kBundle.getString("L"),
        kBundle.getString("M"), kBundle.getString("N"), kBundle.getString("O")
    };

    /** Capital letters for multiple choice numbering */
    public static final String[] kCapCharArray =
    {
        kBundle.getString("A").toUpperCase(),
        kBundle.getString("B").toUpperCase(),
        kBundle.getString("C").toUpperCase(),
        kBundle.getString("D").toUpperCase(),
        kBundle.getString("E").toUpperCase(),
        kBundle.getString("F").toUpperCase(),
        kBundle.getString("G").toUpperCase(),
        kBundle.getString("H").toUpperCase(),
        kBundle.getString("I").toUpperCase(),
        kBundle.getString("J").toUpperCase(),
        kBundle.getString("K").toUpperCase(),
        kBundle.getString("L").toUpperCase(),
        kBundle.getString("M").toUpperCase(),
        kBundle.getString("N").toUpperCase(),
        kBundle.getString("O").toUpperCase()
    };

    /** Lowercase Roman numerals for multiple choice numbering */
    public static final String[] kLowRomanArray =
    {
        "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x", "xi",
        "xii", "xiii", "xiv", "xv"
    };

    /** Capital Roman numerals for multiple choice numbering */
    public static final String[] kCapRomanArray =
    {
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI",
        "XII", "XIII", "XIV", "XV"
    };

    /**
     * Get current selected number index (used in format properties panel)
     * @param p Property to check.
     * @return Property index.
     */
    @Override
    public int getMarksPositionIndex(Object p) 
    {    
        int ret; // What to return
        // Verify we have the right object
        if(p instanceof Marks.MarksEnum)
        {
            ret =  ((Marks.MarksEnum) p).getNdx();
        }
        else
        {
            ret =  0;
        }
        return ret;
    }
    /**
     * Get current selected number index (used in format properties panel)
     * @param p Property to check.
     * @return Property index.
     */
    @Override
    public int getMcNumberingTypeIndex(Object p) 
    {
        
        int ret; // What to return
        // Verify we have the right object
        if( p instanceof AnswerIndexing.AnswerIndexingEnum)
        {
            ret =  ((AnswerIndexing.AnswerIndexingEnum) p).getNdx();
        }
        else
        {
            ret =  0;
        }
        return ret;
    }
   /**
     * Gets the horizontal alignment property.
     * @return the property value
     */
    @Override
    public Boolean getHorizontalAlignment()
    {
        return this.kHorizontalAlignment;
    }
    
    /**
     * Get marks position property from the given integer.
     * @param pos Integer to match
     * @return property to set
     */
    @Override
    public Object getMarksPositionFromInt(int pos)
    {
        return Marks.MarksEnum.newPropertyEnum(pos);
    }
    
        /**
     * Get marks position property from the given integer
     * @param toChk Integer to match
     * @return Property to set.
     */
    @Override
    public Object getMcNumberingTypeFromInt(int toChk)
    {
        return AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(toChk);
    }


    /**
     * Initializes all available properties to their default values.
     */
    public Format()
    {
        formatTable.put(Property.MarksPosition, Marks.MarksEnum.InLine);
        formatTable.put(Property.MCPosition, kHorizontalAlignment);
        formatTable.put(Property.ShowParen, kShowParenthesis);
        formatTable.put(Property.MCNumberingType,
            AnswerIndexing.AnswerIndexingEnum.CapLetters);
        formatTable.put(Property.ShowAnswerLines, kHideAnswerLines);
        formatTable.put(Property.GlobalFontSize,
            FontSize.FontSizeEnum.Twelve.toString());
        formatTable.put(Property.GlobalFontFace,
                FontFace.FontFaceEnum.Serif.toString()
                .toLowerCase().replaceAll(" ", "-"));
    }

    /**
     * Accesses the property table to return a given property of the exam.
     *
     * @param prop The Property to access
     * @return An object representing the property requested
     */
    public Object getProperty(Property prop)
    {
        return formatTable.get(prop);
    }

    /**
     * Set a Property to a given value.
     *
     * @param prop The property to change
     * @param ob The value of the property
     */
    public void setProperty(Property prop, Object ob)
    {
        formatTable.put(prop, ob);
        setChanged();
        notifyObservers();
    }
    
    /**
     * Gets the CSS representation of the exam.
     * @return CSS representation of the exam
     */
    public String getStyleBlock()
    {
        double leftmargin = kDLeftMargin, minusmargin = kDesignItemOffset;
         // margin changes for left marks
        if(this.getProperty(Property.MarksPosition) == Marks.MarksEnum.Left)
        {
            leftmargin = kThree;
            //minusmargin = -3.3;
        }
        
        String face;
        // Check font
        if(this.getProperty(Property.GlobalFontFace).equals("serif"))
        {
            face = kSerifFonts;
        }
        else
        {
            face = kSansFonts;
        }
        
        return "<style type=\"text/css\">"
                /**
                 * Document Formatting
                 */
           //     + "body{font-family:"+this.getProperty(Property.GlobalFontFace) 
           //     +"; font-size:"+this.getProperty(Property.GlobalFontFace)+"pt;}"
                + getStyleReset()
                + "\n\n/* Document Formatting */\n"
                + "body{\nfont-family:"+face //this.getProperty(Property.GlobalFontFace) 
                +";\n font-size:"+this.getProperty(Property.GlobalFontSize)
                +"pt;}\n"
                + "@page {\n"
                + "margin: 1in;\n"
                + "@bottom-center { content: element(footer); }\n"
                + " @top-center { content: element(header); }\n"
                + " }\n"
                + "#footer {font-size: 1em; \n"
                + "font-style: none; \n"
                + "position: running(footer);\n}\n"
                + "#header {font-size: 1em;\n"
                + "font-style: none;\n"
                + "position: running(header);\n}\n"
                + "ul{margin-left:0em;}\n"
                + "ol{margin-left:"+leftmargin+"em;}\n"
                + "li{margin-left:0em;}\n"
                //+ "ol{margin-left:-1em;}"
                //Instructions / HR
                + ".leftalign{margin-left:"+minusmargin+"em;}\n"
                + "hr {margin-left:"+minusmargin+"em;}\n"
                
                // Header and footer left, middle, right
                + ".left{text-align:left;}\n"
                + ".middle{text-align:center;}\n.right{text-align:right;}\n"
                + "#hf-cell{width:30%;padding:2px; float: left;}\n"
                + "#pagenumber:before {content: counter(page); }\n"
                + "#pagecount:before {content: counter(pages);  }\n"
                + "hr{margin-left:0}\n"
                + "p{margin-bottom:0.5em;\n"
                + "font-size:inherit;\n}\n" + getStyleBlock2();
    }
    
    /**
     * Generates the second half of the initial style block.
     * @return A string in CSS
     */
    private String getStyleBlock2()
    {
        
        int topmargin = 1;
         // margin changes for left marks
        if(this.getProperty(Property.ShowParen) == (Object)true)
        {
            topmargin = 0;
        }
        return ".default_font{font-family:\"Times New Roman\";}\n"
            + ".monospace_font{font-family:monospace;}\n"
            + "li{font-size:inherit;}\n"
            //+"font{font-family:monospace;}"

            /**
                * Design Items
                */
            //Section Title
            + ".section_title{margin-left:0;}\n"
            // Page Break
            + ".page_break{page-break-after:always;}\n"
            /**
                * Question Items
                */
            + ".question_item{margin-bottom:2em;}\n"
            + ".marks-left{position:absolute; left:-3em; top:"+topmargin+"em;}\n"
            + ".marks-right{position:absolute; right:-30px; top:"+topmargin+"em;}\n"
            + "#keep-together{page-break-inside:avoid;}\n"
            + ".answer{color:red;}\n"
            //Short and Long answer
            + ".answer_line{width:100%; border-bottom:#ccc 1px solid;}\n"
            // Multiple Choice
            + ".multiple_choice-LowLetters{list-style-type:lower-alpha}\n"
            + ".multiple_choice-CapLetters{list-style-type:upper-alpha}\n"
            + ".multiple_choice-Numbers{list-style-type:decimal}\n"
            + ".multiple_choice-LowRoman{list-style-type:lower-roman}\n"
            + ".multiple_choice-CapRoman{list-style-type:upper-roman}\n"
            + ".multiple_choice-table{width:100%;}\n"
            + ".multiple_choice-cell{width:50%; padding:2px 5px;}\n"
            //True / False
            + ".true_false{list-style-type:lower-alpha}\n"
            //Matching
            + ".matching_left{float:left;}\n"
            + ".matching_right{float:right;}\n"
            //True / False
            + ".true_false{list-style-type:lower-alpha}\n"
            // Misc Fix
            + ".clearfix{clear:both;}\n"
            + "</style>";
    }
    /**
     * Generates the Style Reset Block.
     * @return A string in CSS
     */
    private String getStyleReset()
    {
        return "/* Style Reset */\n"
                + "ol, ul, li{\n"
                + "margin: 0;\n"
                + "padding: 0;\n"
                + "border: 0;\n"
                + "outline: 0;\n"
                + "font-size: 100%;\n"
                + "vertical-align: baseline;\n"
                + "background: transparent;}";
    }
    
    /**
     * Gets the HTML representation of the footer from existing object
     *
     * @param hf HeaderFooter object
     * @return HTML representation of the footer
     */
    @Override
    public String getHtmlFooter(HeaderFooterApi hf)
    {
        String ret = "";
        // If header should be shown, show header
        if(hf.getShowFooter())
        {
            ret =  getHtmlFooter(hf.getFooterContentLeft(),
                hf.getFooterContentMiddle(),
                hf.getFooterContentRight());
        }
        return ret;
    }
    
    /**
     * Gets the HTML representation of the header from existing object
     *
     * @param hf HeaderFooter object
     * @return HTML representation of the header
     */
    @Override
    public String getHtmlHeader(HeaderFooterApi hf)
    {
        String ret = "";
        // if footer should be shown, show footer
        if(hf.getShowHeader())
        {
            ret =  getHtmlHeader(hf.getHeaderContentLeft(),
                hf.getHeaderContentMiddle(),
                hf.getHeaderContentRight());
        }
        return ret;
    }
    
    /**
     * Gets the HTML representation of the header
     *
     * @param left Left section of the header
     * @param middle Middle section of the header
     * @param right Right section of the header
     * @return HTML representation of the header
     */
    public String getHtmlHeader(String left, String middle, String right)
    {
        StringBuilder sb = new StringBuilder();

        //Add the page number to the left of the test header
        left = left.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        left = left.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");

        //Add the page number to the middle of the test header
        middle = middle.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        middle = middle.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");

        //Add the page number to the right of the test header
        right = right.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        right = right.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");


        sb.append("<div id=\"header\">");
        //Add left header
        sb.append("<div id=\"hf-cell\" class=\"left\">").append(left).append(
                "</div>");

        //Add middle header
        sb.append("<div id=\"hf-cell\" class=\"middle\">").append(
                middle).append("</div>");

        //Add right header
        sb.append("<div id=\"hf-cell\" class=\"right\">").append(right).append(
                "</div>");

        sb.append("</div>");
        return sb.toString();
    }

    /**
     * Gets the HTML representation of the footer.
     *
     * @param left Left section of the footer
     * @param middle Middle section of the footer
     * @param right Right section of the footer
     * @return HTML representation of the footer
     */
    public String getHtmlFooter(String left, String middle, String right)
    {
        StringBuilder sb = new StringBuilder();

        //Add the page number to the left of the test footer
        left = left.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        left = left.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");

        //Add the page number to the middle of the test footer
        middle = middle.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        middle = middle.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");


        //Add the page number to the right of the test footer
        right = right.replace("&lt;pn&gt;", "<span id=\"pagenumber\"/>");
        right = right.replace("&lt;pc&gt;", "<span id=\"pagecount\"/>");

        sb.append("<div id=\"footer\">");
        //Add left footer
        sb.append("<div id=\"hf-cell\" class=\"left\">").append(left).append(
                "</div>");

        //Add middle footer
        sb.append("<div id=\"hf-cell\" class=\"middle\">").append(
                middle).append("</div>");

        //Add right footer
        sb.append("<div id=\"hf-cell\" class=\"right\">").append(right).append(
                "</div>");

        sb.append("</div>");
        return sb.toString();
    }

    /**
     * Gets the opening sections of the HTML.
     * @return opening sections of the HTML
     */
    public String getHtmlBodyStart()
    {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<title>Questionable TestMakr</title>"
                + this.getStyleBlock()
                + "</head>"
                + "<body>";
    }

    /**
     * Gets the closing sections of the HTML
     *
     * @return closing sections of the HTML
     */
    public String getHtmlBodyEnd()
    {
        return "</body></html>";
    }

    /**
     * Gets the HTML representation of a mark.
     *
     * @param marks Numerical mark
     * @param pos Where the mark should appear
     * @param paren Whether the mark should be wrapped in parenthesis
     * @return HTML representation of a mark
     */
    public String getMarksHtml(int marks, 
            Object pos, boolean paren)
    {
        String marksString = Integer.toString(marks);
        
        //Checks to see if parenthesis should be included
        if (paren || pos == Marks.MarksEnum.InLine)
        {
            marksString = "("+marks+")  ";
        }
        else
        {
            marksString = "<span style=\"text-decoration: overline;\">&nbsp;" + 
                    marks + "&nbsp;</span>";
        }
        
        //Determine where the marks should be placed.
        if (pos == Marks.MarksEnum.InLine)
        {
            return "<p style=\"margin-top: 0\">"+marksString;
        }
        
        //Check Left vs Right if return ""; it is not parenthesis
        else if (pos == Marks.MarksEnum.Left)
        {
            return "<div class=\"marks-left\">" + marksString + "</div> ";
        }
        else
        {
            return "<div class=\"marks-right\">" + marksString + "</div> ";
        }
    }
    
    /**
     * Returns format to its initial state
     */
    @Override
    public void newFormat()
    {
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        exam.bulkUpdate(true);
        
        formatTable = new EnumMap<Property, Object>(Property.class);
        
        ((MarksApi) Lookup.getDefault()
                .lookup(MarksApi.class))
                .setProperty(Marks.MarksEnum.InLine);
        formatTable.put(Property.MarksPosition, Marks.MarksEnum.InLine);
        
        ((AnswerAlignmentApi) Lookup.getDefault()
                .lookup(AnswerAlignmentApi.class))
                .setProperty(kHorizontalAlignment);
        formatTable.put(Property.MCPosition, kHorizontalAlignment);
        
        ((ShowParenthesisApi) Lookup.getDefault()
                .lookup(ShowParenthesisApi.class))
                .setProperty(kShowParenthesis);
        formatTable.put(Property.ShowParen, kShowParenthesis);
        
        ((AnswerIndexingApi) Lookup.getDefault()
                .lookup(AnswerIndexingApi.class))
                .setProperty(AnswerIndexing.AnswerIndexingEnum.CapLetters);
        formatTable.put(Property.MCNumberingType,
                AnswerIndexing.AnswerIndexingEnum.CapLetters);
        
        ((ShowAnswerLinesApi) Lookup.getDefault()
                .lookup(ShowAnswerLinesApi.class))
                .setProperty(kHideAnswerLines);
        formatTable.put(Property.ShowAnswerLines, kHideAnswerLines);
        
        ((FontSizeApi) Lookup.getDefault()
                .lookup(FontSizeApi.class))
                .setProperty(FontSize.FontSizeEnum.Twelve);
        formatTable.put(Property.GlobalFontSize,
                FontSize.FontSizeEnum.Twelve.toString());
        
        ((FontFaceApi) Lookup.getDefault()
                .lookup(FontFaceApi.class))
                .setProperty(FontFace.FontFaceEnum.Serif);
        formatTable.put(Property.GlobalFontFace,
                FontFace.FontFaceEnum.Serif.toString().toLowerCase());
        
        exam.bulkUpdate(false);
    }
    
    /**
     * Sets this Format to match that passed in
     * @param toBe the Format for this to match
     */
    @Override
    public void newFormat(FormatApi toBe)
    {
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        exam.bulkUpdate(true);
        
        formatTable = new EnumMap<Property, Object>(Property.class);
        ((MarksApi) Lookup.getDefault()
                .lookup(MarksApi.class))
                .setProperty(toBe.getProperty(Property.MarksPosition));
        formatTable.put(Property.MarksPosition,
                toBe.getProperty(Property.MarksPosition));
        
        ((AnswerAlignmentApi) Lookup.getDefault()
                .lookup(AnswerAlignmentApi.class))
                .setProperty(toBe.getProperty(Property.MCPosition));
        formatTable.put(Property.MCPosition,
                toBe.getProperty(Property.MCPosition));
        
        ((ShowParenthesisApi) Lookup.getDefault()
                .lookup(ShowParenthesisApi.class))
                .setProperty(toBe.getProperty(Property.ShowParen));
        formatTable.put(Property.ShowParen,
                toBe.getProperty(Property.ShowParen));
        
        ((AnswerIndexingApi) Lookup.getDefault()
                .lookup(AnswerIndexingApi.class))
                .setProperty(toBe.getProperty(Property.MCNumberingType));
        formatTable.put(Property.MCNumberingType,
                toBe.getProperty(Property.MCNumberingType));
        
        ((ShowAnswerLinesApi) Lookup.getDefault()
                .lookup(ShowAnswerLinesApi.class))
                .setProperty(toBe.getProperty(Property.ShowAnswerLines));
        formatTable.put(Property.ShowAnswerLines,
                toBe.getProperty(Property.ShowAnswerLines));
        
        ((FontSizeApi) Lookup.getDefault()
                .lookup(FontSizeApi.class))
                .setProperty(toBe.getProperty(Property.GlobalFontSize));
        formatTable.put(Property.GlobalFontSize,
                toBe.getProperty(Property.GlobalFontSize));
        
        ((FontFaceApi) Lookup.getDefault()
                .lookup(FontFaceApi.class))
                .setProperty(FontFace.FontFaceEnum.newPropertyEnum(
                (String) toBe.getProperty(Property.GlobalFontFace)));
        formatTable.put(Property.GlobalFontFace,
                toBe.getProperty(Property.GlobalFontFace));
        
        exam.bulkUpdate(false);
    }
    
    /**
     * Get page width for images
     * @return Page Width
     */
    public float getPageWidth()
    {
        return this.kPageWidth;
    }
    
    
    /**
     * Get Image HTML for this question based on format.
     *
     * @param image image to add to exam
     * @param scale scale of the image
     * @param pos image position (left, middle, right)
     * @return The html for the image in String format
     */
    public String getImageHtml(File image, float scale, int pos)
    {
        int width;
        int height;
        String ret = "";
        String pstring;
        //If the item has no image
        if(image != null)
        {
            
            String location = (image).getAbsolutePath();
            try 
            {
                BufferedImage img = ImageIO.read(image);
                width = Math.round(img.getWidth() * scale);
                height = Math.round(img.getHeight() * scale);
                
                // Windows fix
                if(location.contains("\\"))
                {
                    location = location.replace("\\", "/");
                    location = "file:///"+location;
                }
                // Select which HTML rendering to use for image position
                switch(pos)
                {
                    // Left
                    case 0:
                        pstring = "margin:0 auto 0 0; display:block;";
                        break;
                    // Center
                    case 1:
                        pstring = "margin:0 auto 0 auto; display:block;";
                        break;
                    // Right
                    case 2:
                        pstring = "margin:0 0 0 auto; display:block;";
                        break;
                    // Default left
                    default:
                        pstring = "margin:0 auto 0 0; display:block;";
                }
                ret = "<img style=\"width:"+width+"px; "
                    + "height:"+height+"; "+pstring+"\" "
                        + "src=\""+location+"\" />";
                /*ret = "<p>Img 1</p>"
                    + "<br />Image:"+location+"<br />Scale:"+scale
                    + "<br />Page Percent:"+this.getPageScale(width)
                    + "<br />Alignment:" + pos.toString();*/
                return ret;
            } 
            catch (IOException ex) 
            {
                return "";
            }
        }
        return "";
    }
    
    /**
     * Get scale based on width.
     * @param width The width of the image
     * @return The page scale
     */
    public float getPageScale(int width)
    {
        return width / this.getPageWidth();
    }
    
}

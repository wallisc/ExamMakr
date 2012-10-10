/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.FontSizeApi;
import qsolutions.exam.Format;


/**
 * Class representing font size in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = FontSizeApi.class)
public class FontSize implements FontSizeApi
{
    //The property represented in the TopComponent
    private FontSizeEnum property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("FONT SIZE");
    //The default value for the property
    private static FontSizeEnum kDefaultProperty = FontSizeEnum.Twelve;
    
    /**
     * Default FontFace constructor
     */
    public FontSize()
    {
        property = kDefaultProperty;
    }
    
    /**
     * Getter method for the kName
     * @return kName
     */
    @Override
    public String getName()
    {
        return kName;
    }
    
    /**
     * Getter for the property
     * @return the property
     */
    @Override
    public Object getProperty()
    {
        return property;
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     * @pre nProperty must be a FontSizeEnum or String
     */
    @Override
    public void setProperty(Object nProperty)
    {
        //If nProperty is a FontSizeEnum
        if (nProperty instanceof FontSizeEnum)
        {
            setProperty((FontSizeEnum) nProperty);
        }
        //Otherwise
        else
        {
            int ndx = Integer.valueOf((String) nProperty).intValue();
            setProperty(FontSizeEnum.newPropertyEnum(ndx));
        }
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(FontSizeEnum nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat()
                .setProperty(Format.Property.GlobalFontSize, property);
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return FontSizeEnum.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public FontSizeEnum getDefaultProperty()
    {
        return kDefaultProperty;
    }
    
    /**
     * Enum representing the possible property values
     */
    public enum FontSizeEnum
    {
        Eight(8), Nine(9), Ten(10), Eleven(11), Twelve(12),
        Fourteen(14), Eighteen(18), TwentyFour(24);
        // The property value's size
        private final int size;

        /**
         * Constructor for the enums
         * @param nSize the enum's size
         */
        private FontSizeEnum(int nSize)
        {
            size = nSize;
        }

        /**
         * Returns the size of the enum
         * @return the size
         */
        @Override
        public String toString()
        {
            return String.valueOf(size);
        }

        /**
         * Returns an enum determined by str
         * @param num the size of the desired enum
         * @return the desired enum, Twelve by default
         */
        public static Object newPropertyEnum(int num)
        {
            FontSizeEnum ret;

            //Gets the appropriate enum for the index
            switch (num)
            {
                case 8:
                    ret = Eight;
                    break;
                case 9:
                    ret = Nine;
                    break;
                case 10:
                    ret = Ten;
                    break;
                case 11:
                    ret = Eleven;
                    break;
                case 14:
                    ret = Fourteen;
                    break;
                case 18:
                    ret = Eighteen;
                    break;
                case 24:
                    ret = TwentyFour;
                    break;
                default:
                    ret = Twelve;
            }
            return ret;
        }
    }
}
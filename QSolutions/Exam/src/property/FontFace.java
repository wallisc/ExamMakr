/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.FontFaceApi;
import qsolutions.exam.Format;


/**
 * Class representing font type in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = FontFaceApi.class)
public class FontFace implements FontFaceApi
{
    //The property represented in the TopComponent
    private FontFaceEnum property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("FONT FACE");
    //The default value for the property
    private static FontFaceEnum kDefaultProperty = FontFaceEnum.Serif;
    
    /**
     * Default FontFace constructor
     */
    public FontFace()
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
     * @pre nProperty must be a FontFaceEnum
     */
    @Override
    public void setProperty(Object nProperty)
    {
        setProperty((FontFaceEnum) nProperty);
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(FontFaceEnum nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat()
                .setProperty(Format.Property.GlobalFontFace,
                property.toString().toLowerCase().replaceAll(" ", "-"));
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return FontFaceEnum.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public FontFaceEnum getDefaultProperty()
    {
        return kDefaultProperty;
    }
    
    /**
     * Enum representing the possible property values
     */
    public enum FontFaceEnum
    {
        Serif(java.util.ResourceBundle.getBundle("property/Bundle").getString("SERIF")), SansSerif(java.util.ResourceBundle.getBundle("property/Bundle").getString("SANS SERIF"));
        // The property value's name
        private final String name;

        /**
         * Constructor for the enums
         * @param nName the new name
         */
        private FontFaceEnum(String nName)
        {
            name = nName;
        }

        /**
         * Returns the name of the enum
         * @return the name
         */
        @Override
        public String toString()
        {
            return name;
        }

        /**
         * Returns an enum determined by str
         * @param str the name of the desired enum
         * @return the desired enum, Serif by default
         */
        public static Object newPropertyEnum(String str)
        {
            FontFaceEnum ret;
            
            //If the str is Sans Serif
            if (str.equals(java.util.ResourceBundle.getBundle("property/Bundle").getString("SANS-SERIF")))
            {
                ret = SansSerif;
            }
            //Otherwise
            else
            {
                ret = Serif;
            }
            
            return ret;
        }
    }
}
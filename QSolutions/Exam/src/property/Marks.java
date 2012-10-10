/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.MarksApi;
import qsolutions.exam.Format;


/**
 * Class representing mark placement in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = MarksApi.class)
public class Marks implements MarksApi
{
    //The property represented in the TopComponent
    private MarksEnum property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("MARKS");
    //The default value for the property
    private static MarksEnum kDefaultProperty = MarksEnum.InLine;
    
    /**
     * Default FontFace constructor
     */
    public Marks()
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
     * @pre nProperty must be a MarksEnum
     */
    @Override
    public void setProperty(Object nProperty)
    {
        setProperty((MarksEnum) nProperty);
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(MarksEnum nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat()
                .setProperty(Format.Property.MarksPosition, property);
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return MarksEnum.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public MarksEnum getDefaultProperty()
    {
        return kDefaultProperty;
    }
    
    /**
     * Enum representing the possible property values
     */
    public enum MarksEnum
    {
        Left(java.util.ResourceBundle.getBundle("property/Bundle").getString("LEFT"), 0), Right(java.util.ResourceBundle.getBundle("property/Bundle").getString("RIGHT"), 1), InLine(java.util.ResourceBundle.getBundle("property/Bundle").getString("INLINE"), 2);
        // The property value's name
        private final String name;
        // The property value's ndx
        private final int ndx;

        /**
         * Constructor for the enums
         * @param nName the new name
         * @param nNdx the new ndx
         */
        private MarksEnum(String nName, int nNdx)
        {
            name = nName;
            ndx = nNdx;
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
         * Getter for the ndx
         * @return ndx
         */
        public int getNdx()
        {
            return ndx;
        }

        /**
         * Returns an enum determined by str
         * @param str the name of the desired enum
         * @return the desired enum, MarksEnum by default
         */
        public static MarksEnum newPropertyEnum(String str)
        {
            MarksEnum ret;
            
            //If str is Right
            if (str.equals(java.util.ResourceBundle.getBundle("property/Bundle").getString("RIGHT")))
            {
                ret = Right;
            }
            //If str is InLine
            else if (str.equals(java.util.ResourceBundle.getBundle("property/Bundle").getString("INLINE")))
            {
                ret = InLine;
            }
            //Otherwise
            else
            {
                ret = Left;
            }
            
            return ret;
        }
        
        /**
         * Returns an enum determined by str
         * @param nNdx the index of the desired enum
         * @return the desired enum, LowLetters by default
         */
        public static MarksEnum newPropertyEnum(int toGet)
        {
            MarksEnum ret;
            
            //Gets the appropriate enum for the index
            switch (toGet)
            {
                case 1:
                    ret = Right;
                    break;
                case 2:
                    ret = InLine;
                    break;
                default:
                    ret = Left;
            }
            
            return ret;
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.AnswerAlignmentApi;
import qsolutions.exam.Format;


/**
 * Class representing MultipleChoice answer alignment in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = AnswerAlignmentApi.class)
public class AnswerAlignment implements AnswerAlignmentApi
{
    //The property represented in the TopComponent
    private AnswerAlignmentEnum property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("ANSWER ALIGNMENT");
    //The default value for the property
    private static AnswerAlignmentEnum kDefaultProperty =
            AnswerAlignmentEnum.Horizontal;
    
    /**
     * Default AnswerAlignment constructor
     */
    public AnswerAlignment()
    {
        property = kDefaultProperty;
    }
    
    /**
     * Getter for the kName
     * @return the kName
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
     * @pre nProperty must be an AnswerAlignmentEnum or Boolean
     */
    @Override
    public void setProperty(Object nProperty)
    {
        //If nProperty is an AnswerAlignmentEnum
        if (nProperty instanceof AnswerAlignmentEnum)
        {
            setProperty((AnswerAlignmentEnum) nProperty);
        }
        //Otherwise
        else
        {
            boolean val = (Boolean) nProperty;
            //If val is true
            if (val)
            {
                setProperty(AnswerAlignmentEnum.Horizontal);
            }
            //Otherwise
            else
            {
                setProperty(AnswerAlignmentEnum.Vertical);
            }
            
        }
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(AnswerAlignmentEnum nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat()
                .setProperty(Format.Property.MCPosition, property.getMarker());
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return AnswerAlignmentEnum.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public AnswerAlignmentEnum getDefaultProperty()
    {
        return kDefaultProperty;
    }
    
    /**
     * Enum representing the possible property values
     */
    public enum AnswerAlignmentEnum
    {
        Horizontal(java.util.ResourceBundle.getBundle("property/Bundle").getString("HORIZONTAL"), true), Vertical(java.util.ResourceBundle.getBundle("property/Bundle").getString("VERTICAL"), false);
        //Boolean, true if Vertical, otherwise false
        private final boolean marker;
        //The property value's kName
        private final String name;

        /**
         * Constructor for the enums
         * @param nName the new kName
         * @param nMarker the new marker value
         */
        private AnswerAlignmentEnum(String nName, boolean nMarker)
        {
            name = nName;
            marker = nMarker;
        }

        /**
         * Returns the kName of the enum
         * @return the kName
         */
        @Override
        public String toString()
        {
            return name;
        }
        
        /**
         * Getter for marker
         * @return the marker
         */
        public boolean getMarker()
        {
            return marker;
        }

        /**
         * Returns an enum determined by str
         * @param str the kName of the desired enum
         * @return the desired enum, Horizontal by default
         */
        public static AnswerAlignmentEnum newPropertyEnum(String str)
        {
            AnswerAlignmentEnum ret;
            
            //If str equals Vertical
            if (str.equals(java.util.ResourceBundle.getBundle("property/Bundle").getString("VERTICAL")))
            {
                ret = Vertical;
            }
            //Otherwise
            else
            {
                ret = Horizontal;
            }
            
            return ret;
        }
    }
}
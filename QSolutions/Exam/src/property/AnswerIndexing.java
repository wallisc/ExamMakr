/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.AnswerIndexingApi;
import qsolutions.exam.Format;


/**
 * Class representing MultipleChoice answer indexing in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = AnswerIndexingApi.class)
public class AnswerIndexing implements AnswerIndexingApi
{
    //The property represented in the TopComponent
    private AnswerIndexingEnum property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("ANSWER INDEXING");
    //The default value for the property
    private static AnswerIndexingEnum kDefaultProperty =
            AnswerIndexingEnum.CapLetters;
    
    /**
     * Default AnswerIndexing constructor
     */
    public AnswerIndexing()
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
     * @pre nProperty must be an AnswerAlignmentEnum
     */
    @Override
    public void setProperty(Object nProperty)
    {
        setProperty((AnswerIndexingEnum) nProperty);
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(AnswerIndexingEnum nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat()
                .setProperty(Format.Property.MCNumberingType, property);
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return AnswerIndexingEnum.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public AnswerIndexingEnum getDefaultProperty()
    {
        return kDefaultProperty;
    }
    
    /**
     * Enum representing the possible property values
     */
    public enum AnswerIndexingEnum
    {
        
        CapLetters("A B C D", 0), LowLetters("a b c d", 1),
        CapRoman("I II III IV", 2), LowRoman("i ii iii iv", 3),
        Numbers("1 2 3 4", 4);
        //The property value's name
        private final String name;
        //The index of the property value
        private final int ndx;
        //String patterns for the various enums
        public static final String kCLPattern = "A B C D";
        public static final String kLLPattern = "a b c d";
        public static final String kCRPattern = "I II III IV";
        public static final String kLRPattern = "i ii iii iv";
        public static final String kNumbers = "1 2 3 4";

        /**
         * Constructor for the enums
         * @param nName the new name
         * @param nMarker the new marker value
         */
        private AnswerIndexingEnum(String nName, int nNdx)
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
         * @param str the kName of the desired enum
         * @return the desired enum, LowLetters by default
         */
        public static AnswerIndexingEnum newPropertyEnum(String str)
        {
            AnswerIndexingEnum ret;
            
            //If str equals the capital letter pattern
            if (str.equals(kCLPattern))
            {
                ret = CapLetters;
            }
            //If str equals the capital roman pattern
            else if (str.equals(kCRPattern))
            {
                ret = CapRoman;
            }
            //If str equals the lowercase roman pattern
            else if (str.equals(kLRPattern))
            {
                ret = LowRoman;
            }
            //If str equals the numbers pattern
            else if (str.equals(kNumbers))
            {
                ret = Numbers;
            }
            //Otherwise
            else
            {
                ret = LowLetters;
            }
            
            return ret;
        }
        
        /**
         * Returns an enum determined by str
         * @param nNdx the index of the desired enum
         * @return the desired enum, LowLetters by default
         */
        public static Object newPropertyEnum(int nNdx)
        {
            AnswerIndexingEnum ret;
            
            //Gets the appropriate enum for the index
            switch (nNdx)
            {
                case 0:
                    ret = CapLetters;
                    break;
                case 2:
                    ret = CapRoman;
                    break;
                case 3:
                    ret = LowRoman;
                    break;
                case 4:
                    ret = Numbers;
                    break;
                default:
                    ret = LowLetters;
            }
            
            return ret;
        }
    }
}
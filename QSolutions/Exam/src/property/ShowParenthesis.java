/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import javax.swing.JOptionPane;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamApi;
import qsolutions.api.properties.ShowParenthesisApi;
import qsolutions.exam.Format;


/**
 * Class representing if the parentheses will be shown in PropertiesTopComponent
 * @author Ryan Dollahon
 */
@ServiceProvider(service = ShowParenthesisApi.class)
public class ShowParenthesis implements ShowParenthesisApi
{
    //The property represented in the TopComponent
    private Boolean property;
    //The property kName
    private static String kName = java.util.ResourceBundle.getBundle("property/Bundle").getString("SHOW PARENTHESIS");
    //The default value for the property
    private static Boolean kDefaultProperty = true;
    
    /**
     * Default ShowParenthesis constructor
     */
    public ShowParenthesis()
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
     * @pre nProperty must be a ShowAnswerLinesEnum
     */
    @Override
    public void setProperty(Object nProperty)
    {
        setProperty((Boolean) nProperty);
    }
    
    /**
     * Setter for the property
     * @param nProperty the new value for the property
     */
    public void setProperty(Boolean nProperty)
    {
        property = nProperty;
        Lookup.getDefault().lookup(ExamApi.class).getFormat().setProperty(Format.Property.ShowParen, property);
    }
    
    /**
     * Getter for the property class
     * @return the property class
     */
    @Override
    public Class getPropertyClass()
    {
        return Boolean.class;
    }
    
    /**
     * Getter for the default property
     * @return the default property
     */
    @Override
    public Boolean getDefaultProperty()
    {
        return kDefaultProperty;
    }
}
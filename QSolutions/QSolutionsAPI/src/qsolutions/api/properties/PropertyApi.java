/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.api.properties;

import java.io.Serializable;

/**
 *
 * @author Ryan Dollahon
 */
public interface PropertyApi extends Serializable
{
    public String getName();
    
    public Object getProperty();
    
    public void setProperty(Object nProperty);
    
    public Class getPropertyClass();
    
    public Object getDefaultProperty();
}

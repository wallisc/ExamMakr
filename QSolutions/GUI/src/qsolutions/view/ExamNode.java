/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import qsolutions.api.properties.*;


/**
 * Node for PropertiesTopCopmonet
 * @author Chris
 */
public class ExamNode extends AbstractNode
{
    
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Default ExamNode constructor
     */
    public ExamNode() 
    {
        super(Children.LEAF);
        setDisplayName(kBundle.getString("EXAM FORMAT"));
    }
    
    @Override
    protected Sheet createSheet() 
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        
        Collection<Class> col = new ArrayList<Class>();
        
        col.add(FontSizeApi.class);
        col.add(FontFaceApi.class);

        sheet.put(getSet(kBundle.getString("DOCUMENT"), col));
        
        col.clear();
        
        col.add(MarksApi.class);
        col.add(ShowParenthesisApi.class);
        
        sheet.put(getSet(kBundle.getString("MARKS"), col));
        
        col.clear();
        
        col.add(ShowAnswerLinesApi.class);
        
        sheet.put(getSet(kBundle.getString("LONG / SHORT ANSWER"), col));
        
        col.clear();
        
        col.add(AnswerIndexingApi.class);
        col.add(AnswerAlignmentApi.class);
        
        sheet.put(getSet(kBundle.getString("MULTIPLE CHOICE"), col));
        
        return sheet;
    }
    
    private Sheet.Set getSet(String title, Collection<Class> classes)
    {
        Sheet.Set ret = Sheet.createPropertiesSet();
        
        ret.setDisplayName(title);
        ret.setName(title);
        
        try
        {
            // For every class in the Collection
            for (Class itr : classes)
            {
                PropertyApi temp = (PropertyApi) Lookup.getDefault().lookup(itr);
                Property indexProp = new PropertySupport.Reflection(temp,
                        temp.getPropertyClass(), "Property");

                indexProp.setName(temp.getName());
                ret.put(indexProp);
            }
        } 
        catch (NoSuchMethodException ex) 
        {
            ErrorManager.getDefault();
        }
        
        return ret;
    }
}


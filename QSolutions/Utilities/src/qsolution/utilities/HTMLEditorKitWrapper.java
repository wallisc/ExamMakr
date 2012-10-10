package qsolution.utilities;

import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.HTMLEditorKitAPI;

/**
 * Wrapper class to allow global lookup of a single HTMLEditorKit
 */
@ServiceProvider(service = HTMLEditorKitAPI.class)
public class HTMLEditorKitWrapper extends HTMLEditorKit
    implements HTMLEditorKitAPI 
{
    
}

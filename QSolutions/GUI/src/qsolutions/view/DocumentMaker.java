package qsolutions.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import javax.swing.*;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.DocumentMakerApi;


/**
 * Add print preview functionality to any Swing Text component.
 *
 * <p>When active, the print preview frame temporary replaces on-screen the 
 * text component's window.</p>
 * 
 * <p>Keyboard shortcuts are available for zooming print preview images in and
 * out and for invoking the standard print dialog for the text component.</p>
 */
@ServiceProvider(service = DocumentMakerApi.class)
public class DocumentMaker implements DocumentMakerApi
{
    
    /** Swing Text component to generate preview for.  */
    private JEditorPane textPane = null;
    
    /** Container hosting the text component.  */
    private Container textHost = null;
    
    /** Pane holding the print preview images.  */
    private final JPanel previewPane = new JPanel();
    
    /** Background thread for generating the print preview images.  */
    private Thread previewThread = null;
    
    /** Distance between images in the print preview pane.  */
    private static final int kGap = 5;
    
    /** Scale factor for the print preview images.  */
    private static final double kPreviewScale = 0.75;

    // The two methods that actually generate the print preview.

    /**
     * Returns the number of pages for textPane
     * @param nTextPane the text pane to count pages for
     * @return Number of pages
     */
    public int pageCount( JEditorPane nTextPane )
    {
        // What to print: the Printable object.
        Printable printable = nTextPane.getPrintable( null, null );
        
        // Print parameters: page width and height.
        PageFormat format = new PageFormat();
        int width = ( int ) format.getWidth();
        int height = ( int ) format.getHeight();
        int pages = 0;
        
        // Stop generating images when the preview thread is interrupted.
        for ( ; !Thread.currentThread().isInterrupted(); pages++ ) 
        {
            
            // Where to print: the Graphics object.
            final Image pageImage = new BufferedImage( 
                                width, height, BufferedImage.TYPE_INT_ARGB );
            Graphics graphic = pageImage.getGraphics();
            
            try 
            {
                // How to print: generate preview image of the n-th page.
                if ( printable.print( graphic, format, pages ) != 
                    Printable.PAGE_EXISTS ) 
                {
                    break;
                }
            }
            catch ( Exception e )
            {
                break;
            }
        }
        return pages;
    }
    
    /** 
     * Generate print preview images for the cached text component.
     * @param tpane The pane to print the preview from
     * @param target Where the preview should be generated to
     * @return number of pages
     */
    public int generatePreview( JEditorPane tpane, final JPanel target ) 
    {

        // What to print: the Printable object.
        Printable printable = tpane.getPrintable( null, null );
        
        // Print parameters: page width and height.
        PageFormat format = new PageFormat();
        int width = ( int ) format.getWidth();
        int height = ( int ) format.getHeight();
        int pages;
        
        // Stop generating images when the preview thread is interrupted.
        for ( pages = 0; !Thread.currentThread().isInterrupted(); pages++ ) 
        {
            // Where to print: the Graphics object.
            final Image pageImage = new BufferedImage( 
                                width, height, BufferedImage.TYPE_INT_ARGB );
            Graphics graphic = pageImage.getGraphics();
            
            try 
            {
                // How to print: generate preview image of the n-th page.
                if ( printable.print( graphic, format, pages ) != 
                    Printable.PAGE_EXISTS ) 
                {
                    break;
                }
            } 
            catch ( Exception e ) 
            {
                break;
            }
            addPage( pageImage, target );
            
            // This calls into Swing and should be scheduled to run on EDT.
            /*SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    addPage( pageImage, target );
                }
            } );*/
        }
        return pages;
    }
 
    /** Scale the generated image and add it to the print preview pane.  
     */
    private void addPage( final Image pageImage, final JPanel target )
    {
        // Create a component representing the scaled page preview image.
        JPanel panel = new JPanel() 
        {
            private Image scaled = null;
            private Dimension dim = new Dimension();

            /** Return preview dimensions using the current scale factor.  */
            public Dimension getPreferredSize() 
            {
                int width = (int)(pageImage.getWidth(null) * kPreviewScale );
                int height = (int)(pageImage.getHeight(null) * kPreviewScale );
                // if the dimensions changed
                if ( ( dim.width != width ) || ( dim.height != height ) ) 
                {
                    // Cached dimensions are invalid, re-scale the image.
                    dim.width = width;
                    dim.height = height;
                    scaled = pageImage.getScaledInstance(width, height, 
                                Image.SCALE_SMOOTH );
                    setPreferredSize( new Dimension( width + 2 * kGap, 
                            height + 2 * kGap ) );
                }
                return super.getPreferredSize();
            }

            /** Minimum preview size is the same as the preferred size.  */
            public Dimension getMinimumSize() 
            {
                return getPreferredSize();
            }

            /** Maximum preview size is the same as the preferred size.  */
            public Dimension getMaximumSize() 
            {
                return getPreferredSize();
            }

            /** Clear the drawing area and paint the scaled image.  */
            public void paintComponent( Graphics g ) 
            {
                g.setColor( target.getBackground() );
                g.fillRect( 0, 0, dim.width + 2 * kGap, dim.height + 2 * kGap );
                g.setColor( Color.WHITE );
                g.fillRect( kGap, kGap, dim.width, dim.height );
                //If the document image should be scaled to specific size, draw
                if ( scaled != null ) 
                {
                    g.drawImage( scaled, kGap, kGap, dim.width, dim.height, 
                        null );
                }
            }
        };

        // Add scaled image to the print preview pane and re-layout the pane.
        target.add( panel );
        target.revalidate();
        target.repaint();
    }
    
    /** 
     * Scale the generated image and add it to the print preview pane.  
     * @param pageImage Image to be scaled
     */
    private void addPage( final Image pageImage ) 
    {
        // Create a component representing the scaled page preview image.
        JPanel panel = new JPanel() 
        {
            private Image scaled = null;
            private Dimension dim = new Dimension();

            /** Return preview dimensions using the current scale factor.  */
            public Dimension getPreferredSize() 
            {
                int width = (int)(pageImage.getWidth(null) * kPreviewScale );
                int height = (int)(pageImage.getHeight(null) * kPreviewScale );
                //If the dimensions are not too large, continue processing
                if ( ( dim.width != width ) || ( dim.height != height ) ) 
                {
                    dim.height = height;
                    dim.width = height;
                    // Cached dimensions are invalid, re-scale the image.
                    scaled = pageImage.getScaledInstance(width, height,
                                Image.SCALE_SMOOTH );
                    setPreferredSize( new Dimension( 
                                        dim.width + 2 * kGap, dim.height + 2 *
                                        kGap ) );
                }
                return super.getPreferredSize();
            }

            /** Minimum preview size is the same as the preferred size.  */
            public Dimension getMinimumSize() 
            {
                return getPreferredSize();
            }

            /** Maximum preview size is the same as the preferred size.  */
            public Dimension getMaximumSize() 
            {
                return getPreferredSize();
            }

            /** Clear the drawing area and paint the scaled image.  */
            public void paintComponent( Graphics g ) 
            {
                g.setColor( previewPane.getBackground() );
                g.fillRect( 0, 0, dim.width + 2 * kGap, dim.height + 2 * kGap );
                g.setColor( Color.WHITE );
                g.fillRect( kGap, kGap, dim.width, dim.height );
                // If the image should be scaled, draw it
                if ( scaled != null ) 
                {
                    g.drawImage( scaled, kGap, kGap, dim.width, dim.height, 
                        null );
                }
            }
        };

        // Add scaled image to the print preview pane and re-layout the pane.
        previewPane.add( panel );
        previewPane.revalidate();
        previewPane.repaint();
    }
}

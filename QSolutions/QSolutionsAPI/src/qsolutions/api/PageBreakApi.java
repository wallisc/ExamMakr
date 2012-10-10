package qsolutions.api;

/**
 * The PageBreak interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface PageBreakApi extends DesignItemApi
{
    /**
     * Generates a new instance of a PageBreak item.
     * @return the new PageBreak
     */
    PageBreakApi newItem();
}

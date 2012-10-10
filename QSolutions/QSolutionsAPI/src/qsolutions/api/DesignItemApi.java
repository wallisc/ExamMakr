package qsolutions.api;

/**
 * The DesignItem interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface DesignItemApi extends DocumentItemApi
{
    /**
     * Generates a new instance of a DesignItem item.
     * @return the new DesignItem
     */
    @Override
    DesignItemApi newItem();
}

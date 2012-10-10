package qsolutions.api;

/**
 * The Divider interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface DividerApi extends DesignItemApi
{
    /**
     * Generates a new instance of a Divider item.
     * @return the new Divider
     */
    public DividerApi newItem();
}

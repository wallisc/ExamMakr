package qsolutions.exam;

import qsolutions.api.DesignItemApi;

/**
 * Represents a design item.
 */
public abstract class DesignItem extends DocumentItem implements DesignItemApi
{
    /**
     * DesignItem constructor that only contains a call to super.
     */
    public DesignItem()
    {
        super();
    }
}

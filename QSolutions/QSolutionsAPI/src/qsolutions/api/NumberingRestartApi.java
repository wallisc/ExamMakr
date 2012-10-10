package qsolutions.api;

/**
 * The NumberingRestart interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface NumberingRestartApi extends DesignItemApi
{
    /**
     * Generates a new instance of a NumberingRestart item.
     * @return the new Divider
     */
    public NumberingRestartApi newItem();
}

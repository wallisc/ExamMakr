package qsolutions.api;

/**
 * The Instruction interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface InstructionsApi extends DesignItemApi
{
    /**
     * Generates a new instance of a Instructions item.
     * @return the new Instructions
     */
    InstructionsApi newItem();
}

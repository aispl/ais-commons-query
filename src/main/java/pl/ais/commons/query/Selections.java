package pl.ais.commons.query;

/**
 * Defines commonly used set of {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class Selections {

    /**
     * Creates and returns a {@link Selection} instance selecting all records.
     *
     * @param factory the factory which will be used to create Selection
     * @return newly created {@link Selection} instance
     */
    public static Selection allRecords(final SelectionFactory factory) {
        return factory.createSelection(0, -1);
    }

    /**
     * Constructs new instance.
     */
    private Selections() {
        super();
    }

}

package pl.ais.commons.query;

import java.io.Serializable;

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
    @SuppressWarnings("unchecked")
    public static <R extends Serializable> Selection<R> allRecords(final SelectionFactory<R> factory) {
        return factory.createSelection(0, -1);
    }

    /**
     * Constructs new instance.
     */
    private Selections() {
        super();
    }

}

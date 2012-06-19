/*
 * Copyright (c) 2012, AIS.PL
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pl.ais.commons.query.dsl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.Selection;

import com.mysema.query.types.OrderSpecifier;

/**
 * {@link Selection} implementation suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class QueryDSLSelection extends AbstractSelection {

    private final OrderSpecifier<?>[] orderings;

    /**
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings orderings which should be used
     */
    public QueryDSLSelection(final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        super(startIndex, displayLength);
        this.orderings = orderings;
    }

    /**
     * @return the orderings
     */
    @Override
    @SuppressWarnings("unchecked")
    public OrderSpecifier<?>[] getOrderings() {
        return orderings;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringBuilder().append("orderings", orderings).build();
    }

    /**
     * @see pl.ais.commons.query.Selection#withOrderings(R[])
     */
    @Override
    public <R extends Serializable> Selection withOrderings(@SuppressWarnings("hiding") final R... orderings) {
        final List<OrderSpecifier<?>> modified = new ArrayList<OrderSpecifier<?>>();
        // Copy those orderings from the current settings, which are not redefined by the method parameters ...
        processing: for (OrderSpecifier<?> ordering : this.orderings) {
            for (int i = 0; i < orderings.length; i++) {
                if (ordering.getTarget().equals(((OrderSpecifier<?>) orderings[i]).getTarget())) {
                    continue processing;
                }
            }
            modified.add(ordering);
        }
        modified.addAll(Arrays.asList((OrderSpecifier<?>[]) orderings));
        return new QueryDSLSelection(getStartIndex(), getDisplayLength(),
            modified.toArray(new OrderSpecifier<?>[modified.size()]));
    }

}

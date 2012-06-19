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

package pl.ais.commons.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Container for search results of specified type.
 *
 * @author Warlock, AIS.PL
 * @since 1.0
 * @param <T> determines the type of each search result
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public final class SearchResults<T extends Serializable> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final SearchResults EMPTY = new SearchResults();

    /**
     * @return empty search results
     */
    public static final <E extends Serializable> SearchResults<E> emptySearchResults() {
        return EMPTY;
    }

    private final List<T> elements;

    private final long totalRecords;

    /**
     * Constructs the instance with empty list of elements.
     */
    private SearchResults() {
        this(Collections.<T> emptyList(), 0L);
    }

    /**
     * Constructor.
     *
     * @param elements
     * @param totalRecords
     */
    public SearchResults(@Nonnull final List<T> elements, @Nonnegative final long totalRecords) {
        this.elements = elements;
        this.totalRecords = totalRecords;
        // Sanity check ...
        Validate.isTrue(null != this.elements, "Elements shouldn't be null");
        Validate.isTrue(this.totalRecords >= 0, "Total number of records should be non-negative");
    }

    /**
     * @return the elements
     */
    @Nonnull
    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * @return the totalRecords
     */
    @Nonnegative
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("totalRecords", totalRecords).append("elements", elements).toString();
    }

}

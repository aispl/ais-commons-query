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

import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.SearchResultsProvider;
import pl.ais.commons.query.Selection;

import com.mysema.query.Projectable;
import com.mysema.query.Query;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

/**
 * Utility class for building {@link SearchResults search results} based 
 * on {@link Projectable} projectable {@link Query query}. 
 *
 * @param <Q> determines the type of query
 * @param <T> determines the type of records returned by the projectable query
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class QueryDSLSearchResultsBuilder<Q extends Projectable & Query<Q>, T extends Serializable> {

    /**
     * Returns new builder instance for specified projectable query.
     * 
     * @param query projectable query which should be encapsulated by newly created builder
     * @return new builder instance for specified projectable query
     */
    public static <Q extends Projectable & Query<Q>, T extends Serializable> QueryDSLSearchResultsBuilder<Q, T> forQuery(
        final Q query) {
        return new QueryDSLSearchResultsBuilder<Q, T>(query);
    }

    protected transient Q query;

    /**
     * Constructs new instance for specified projectable query.
     *
     * @param query projectable query
     */
    private QueryDSLSearchResultsBuilder(final Q query) {
        super();
        this.query = query;
    }

    /**
     * Applies given designation to the encapsulated query.
     *
     * @param designation determines the predicate which should be matched by desired clients
     * @return this object
     */
    public QueryDSLSearchResultsBuilder<Q, T> andRecordsMatching(final QueryDSLPredicatePrecursor designation) {
        final Predicate predicate = designation.toPredicate();
        if (null != predicate) {
            query = query.where(predicate);
        }
        return this;
    }

    /**
     * Defines the {@link ProjectionProvider projection provider} which will be used for the encapsulated query.
     * 
     * @param projectionProvider the projection provider
     * @return {@link SearchResultsProvider} which can be used for fetching the search results
     */
    public SearchResultsProvider<T> withProjectionProvider(final ProjectionProvider<T> projectionProvider) {
        return new SearchResultsProvider<T>() {

            /**
             * @see pl.ais.commons.query.SearchResultsProvider#provideForSelection(pl.ais.commons.query.Selection)
             */
            @Override
            public SearchResults<T> provideForSelection(final Selection selection) {
                // ... count matching records ...
                final long totalRecords = query.count();
                SearchResults<T> searchResults = null;
                if (0 < totalRecords) {
                    // ... apply selection desired by the caller ...
                    if (selection.isSelectingSubset()) {
                        query = query.offset(selection.getStartIndex()).limit(selection.getDisplayLength());
                    }
                    query = query.orderBy(selection.<OrderSpecifier<?>> getOrderings());
                    // ... query the database and process the results ...
                    searchResults = new SearchResults<T>(projectionProvider.provideForQuery(query), totalRecords);
                } else {
                    searchResults = SearchResults.emptySearchResults();
                }
                return searchResults;
            }

        };
    }

}

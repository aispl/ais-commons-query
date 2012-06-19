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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Defines the subset of database records by specifying the records ordering, index of the first one,
 * and the desired number of records.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface Selection extends Serializable {

    /**
     * @return the display length ({@code -1} when all records should be selected)
     * @see {@link #isSelectingSubset()} 
     */
    int getDisplayLength();

    /**
     * @return current orderings used by this selection
     */
    @Nonnull
    <R extends Serializable> R[] getOrderings();

    /**
     * @return the index of first record 
     */
    @Nonnegative
    int getStartIndex();

    /**
     * @return {@code true} if subset of all records (defined by this selection) should be selected,
     *         {@code false} otherwise (all records should be selected)
     */
    boolean isSelectingSubset();

    /**
     * Creates and returns new {@link Selection} instance by merging current and provided data ordering.
     *
     * @param orderings the orderings to add
     * @return newly created {@link Selection} instance
     */
    @Nonnull
    <R extends Serializable> Selection withOrderings(@Nonnull final R... orderings);

}
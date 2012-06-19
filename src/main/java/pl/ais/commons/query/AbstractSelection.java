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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base class for {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public abstract class AbstractSelection implements Selection {

    private final int displayLength;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    protected AbstractSelection(final int startIndex, final int displayLength) {
        Validate.isTrue(startIndex >= 0, "Start index shouldn't be negative.");
        this.startIndex = startIndex;
        this.displayLength = displayLength;
    }

    /**
     * @see pl.ais.commons.query.Selection#getDisplayLength()
     */
    @Override
    public int getDisplayLength() {
        return displayLength;
    }

    /**
     * @see pl.ais.commons.query.Selection#getStartIndex()
     */
    @Override
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @see pl.ais.commons.query.Selection#isSelectingSubset()
     */
    @Override
    public boolean isSelectingSubset() {
        return (displayLength > 0);
    }

    /**
     * @return new instance of {@link ToStringBuilder} for this object, with appended start index and display length
     */
    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder(this).append("startIndex", startIndex).append("displayLength", displayLength);
    }

}

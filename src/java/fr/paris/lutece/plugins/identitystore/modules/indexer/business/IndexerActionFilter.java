/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.identitystore.modules.indexer.business;

import fr.paris.lutece.plugins.identitystore.web.rs.service.Constants;

/**
 *
 * This class is a filter for IndexerAction
 *
 */
public class IndexerActionFilter
{
    /**
     * Represents any integer
     */
    public static final int ALL_INT = -1;
    private IndexerTask _indexerTask = IndexerTask.ALL;
    private String _strCustomerId = Constants.NO_CUSTOMER_ID;

    /**
     * Gets the indexer task
     * 
     * @return the indexer task in the filter
     */
    public IndexerTask getIndexerTask( )
    {
        return _indexerTask;
    }

    /**
     * Sets the indexer task in the filter
     * 
     * @param indexerTask
     *            the indexer task to insert in the filter
     */
    public void setTask( IndexerTask indexerTask )
    {
        _indexerTask = indexerTask;
    }

    /**
     * Tests if the filter contains a task to filter or not
     * 
     * @return {@code true} if the filter contains a task to filter, {@code false} otherwise
     */
    public boolean containsTask( )
    {
        return ( _indexerTask != IndexerTask.ALL );
    }

    /**
     * Gets the id of the identity
     * 
     * @return The id of the identity
     */
    public String getCustomerId( )
    {
        return _strCustomerId;
    }

    /**
     * Sets the id of the customer
     * 
     * @param strCustomerId
     *            The id of the customer
     */
    public void setCustomerId( String strCustomerId )
    {
        this._strCustomerId = strCustomerId;
    }

    /**
     * Tests if the filter contains a customer to filter or not
     * 
     * @return {@code true} if the filter contains a customer to filter, {@code false} otherwise
     */
    public boolean containsCustomerId( )
    {
        return ( !Constants.NO_CUSTOMER_ID.equals( _strCustomerId ) );
    }
}

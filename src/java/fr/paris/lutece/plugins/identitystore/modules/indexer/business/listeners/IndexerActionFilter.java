/*
 * Copyright (c) 2002-2016, Mairie de Paris
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
package fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners;

import org.apache.commons.lang.StringUtils;

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
    private int _nIdTask = ALL_INT;
    
    public static final String NO_CUSTOMER = StringUtils.EMPTY;
    private String _strIdCustomer = NO_CUSTOMER;

    /**
     * Gets the task id
     * @return the task id in the filter
     */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * Sets the task id in the filter
     * @param idTask the task id to insert in the filter
     */
    public void setIdTask( int idTask )
    {
        _nIdTask = idTask;
    }

    /**
     * Tests if the filter contains a task to filter or not
     * @return {@code true} if the filter contains a task to filter, {@code false} otherwise
     */
    public boolean containsIdTask(  )
    {
        return ( _nIdTask != ALL_INT );
    }

    /**
     * Gets the customer id
     * @return the customer id
     */
    public String getIdCustomer(  )
    {
        return _strIdCustomer;
    }

    /**
     * Sets the customer id
     * @param strIdCustomer the customer id to insert in the filter
     */
    public void setIdCustomer( String strIdCustomer )
    {
        this._strIdCustomer = strIdCustomer;
    }

    /**
     * Tests if the filter contains a customer to filter or not
     * @return {@code true} if the filter contains a customer to filter, {@code false} otherwise
     */
    public boolean containsIdCustomer(  )
    {
        return (! NO_CUSTOMER.equals( _strIdCustomer ) );
    }
}

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


/**
 *
 * This class represents an action for the indexer
 *
 */
public class IndexerAction
{
    private int _nIdAction;
    private int _nIdTask;
    private String _strIdCustomer;

    /**
     * Gets the action id
     * @return the action id
     */
    public int getIdAction(  )
    {
        return _nIdAction;
    }

    /**
     * Sets the action id
     * @param nIdAction the action id
     */
    public void setIdAction( int nIdAction )
    {
        _nIdAction = nIdAction;
    }

    /**
     * Gets the customer id
     * @return the customer Id
     */
    public String getIdCustomer(  )
    {
        return _strIdCustomer;
    }

    /**
     * Sets the customer id
     * @param strIdCustomer the customer id
     */
    public void setIdCustomer( String strIdCustomer )
    {
        _strIdCustomer = strIdCustomer;
    }

    /**
     * Gets the task id
     * @return the task id
     */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * Sets the task id
     * @param nIdTask the task id
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }
}

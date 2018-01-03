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
package fr.paris.lutece.plugins.identitystore.modules.indexer.service.listeners;

import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerAction;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionFilter;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerTask;
import fr.paris.lutece.plugins.identitystore.modules.indexer.service.IndexService;
import fr.paris.lutece.plugins.identitystore.service.IdentityChange;
import fr.paris.lutece.plugins.identitystore.service.IdentityChangeListener;

import java.util.List;

/**
 * This class is a listener for indexing an identity when a attribute changes
 *
 */
public class IndexingListener implements IdentityChangeListener
{
    private static final String SERVICE_NAME = "Identity indexing listener";
    private IndexService _indexService;

    /**
     * Sets the index service to use
     * 
     * @param indexService
     *            the index service
     */
    public void setIndexService( IndexService indexService )
    {
        _indexService = indexService;
    }

    /**
     * @{@inheritDoc
     */
    @Override
    public String getName( )
    {
        return SERVICE_NAME;
    }

    /**
     * @{@inheritDoc
     */
    @Override
    public void processIdentityChange( IdentityChange identityChange )
    {
        try
        {
            _indexService.process( identityChange );
        }
        catch( IndexingException ex )
        {
            String strIdCustomer = identityChange.getIdentity( ).getCustomerId( );

            IndexerActionFilter filter = new IndexerActionFilter( );
            filter.setCustomerId( strIdCustomer );
            filter.setTask( IndexerTask.valueOf( identityChange.getChangeType( ).getValue( ) ) );

            List<IndexerAction> listIndexerActions = IndexerActionHome.getListLimit( filter, 0, -1 );

            if ( listIndexerActions.isEmpty( ) )
            {
                IndexerAction indexerAction = new IndexerAction( );
                indexerAction.setCustomerId( strIdCustomer );
                indexerAction.setTask( IndexerTask.valueOf( identityChange.getChangeType( ).getValue( ) ) );

                IndexerActionHome.create( indexerAction );
            }
        }
    }
}

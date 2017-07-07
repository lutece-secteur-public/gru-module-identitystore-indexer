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
package fr.paris.lutece.plugins.identitystore.modules.indexer.service.daemon;

import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.identitystore.business.Identity;
import fr.paris.lutece.plugins.identitystore.business.IdentityConstants;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerAction;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionFilter;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerTask;
import fr.paris.lutece.plugins.identitystore.modules.indexer.service.IndexService;
import fr.paris.lutece.plugins.identitystore.service.IdentityChange;
import fr.paris.lutece.plugins.identitystore.service.IdentityChangeType;
import fr.paris.lutece.plugins.identitystore.service.IdentityStoreService;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * Daemon used to index identities in incremental mode
 */
public class IdentityIndexerDaemon extends Daemon
{
    private static final String LOG_INDEX_CREATED = "Number of created indexes : ";
    private static final String LOG_INDEX_UPDATED = "Number of updated indexes : ";
    private static final String LOG_INDEX_DELETED = "Number of deleted indexes : ";
    private static final String LOG_END_OF_SENTENCE = ". ";
    private static final String APPLICATION_CODE = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_APPLICATION_CODE );
    private static final int PROPERTY_LIMIT_INDEXER_ACTION = AppPropertiesService.getPropertyInt( "identitystore-indexer.indexer.action.limit", -1 );
    private IndexService _indexService;

    /**
     * Constructor
     */
    public IdentityIndexerDaemon( )
    {
        super( );
        _indexService = SpringContextService.getBean( IndexService.BEAN_NAME );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run( )
    {
        StringBuilder sbLogs = new StringBuilder( );

        indexCreatedIdentities( sbLogs );
        indexUpdatedIdentities( sbLogs );
        indexDeletedIdentities( sbLogs );

        setLastRunLogs( sbLogs.toString( ) );
    }

    /**
     * Indexes created identities. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexCreatedIdentities( StringBuilder sbLogs )
    {
        int nNbCreatedIdentities = indexIdentities( IndexerTask.CREATE );
        sbLogs.append( LOG_INDEX_CREATED );
        sbLogs.append( nNbCreatedIdentities );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes updated identities. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexUpdatedIdentities( StringBuilder sbLogs )
    {
        int nNbUpdatedIdentities = indexIdentities( IndexerTask.UPDATE );
        sbLogs.append( LOG_INDEX_UPDATED );
        sbLogs.append( nNbUpdatedIdentities );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes deleted identities. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexDeletedIdentities( StringBuilder sbLogs )
    {
        int nNbDeletedIdentities = indexIdentities( IndexerTask.DELETE );
        sbLogs.append( LOG_INDEX_DELETED );
        sbLogs.append( nNbDeletedIdentities );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes identities
     *
     * @param indexerTask
     *            the indexer task
     * @return the number of indexed identities
     */
    private int indexIdentities( IndexerTask indexerTask )
    {
        int nNbIndexedIdentities = 0;

        IndexerActionFilter indexerActionFilter = new IndexerActionFilter( );
        indexerActionFilter.setTask( indexerTask );

        List<IndexerAction> listIndexerActionFailed = new ArrayList<IndexerAction>( );

        int nIndexerActionStart = 0;

        boolean bContinue = true;

        while ( bContinue )
        {
            List<IndexerAction> listIndexerActions = IndexerActionHome.getListLimit( indexerActionFilter, nIndexerActionStart, PROPERTY_LIMIT_INDEXER_ACTION );
            
            List<IdentityChange> listIdentityChange = new ArrayList<IdentityChange>();
            for ( IndexerAction indexerAction : listIndexerActions )
            {
                try
                {
                    Identity identity = IdentityStoreService.getIdentityByCustomerId( indexerAction.getCustomerId( ), APPLICATION_CODE );

                    IdentityChange identityChange = new IdentityChange( );

                    if ( identity == null )
                    {
                        if ( indexerAction.getTask( ).getValue( ) == IndexerTask.DELETE.getValue( ) )
                        {
                            identity = new Identity( );
                            identity.setCustomerId( indexerAction.getCustomerId( ) );
                            identityChange.setIdentity( identity );
                            identityChange.setChangeType( IdentityChangeType.valueOf( indexerAction.getTask( ).getValue( ) ) );
                        }
                        else
                        {
                            listIndexerActionFailed.add( indexerAction );
                            AppLogService.error( "Try to index the customer " + indexerAction.getCustomerId( ) + " already removed" );
                        }
                    }
                    else
                    {
                        identityChange.setIdentity( identity );
                        identityChange.setChangeType( IdentityChangeType.valueOf( indexerAction.getTask( ).getValue( ) ) );
                    }

                    if ( identity != null )
                    {
                            listIdentityChange.add( identityChange );
                    }
                }
                catch( Exception e )
                {
                    AppLogService.error( "Unable to get the customer with id " + indexerAction.getCustomerId( ) + " : " + e.getMessage( ) );
                }
            }
            try
            {
               _indexService.processList( listIdentityChange );
               nNbIndexedIdentities += listIdentityChange.size( );
            }
            catch ( IndexingException e )
            {
                AppLogService.error( "Unable to process indexing of identities" , e );
            }
            
            if ( listIndexerActions.size( ) < PROPERTY_LIMIT_INDEXER_ACTION )
            {
                bContinue = false;
            }
            else
            {
                nIndexerActionStart += PROPERTY_LIMIT_INDEXER_ACTION;
            }
        }

        IndexerActionFilter filter = new IndexerActionFilter( );
        filter.setTask( indexerTask );
        IndexerActionHome.deleteByFilter( filter );

        for ( IndexerAction failedIndexerAction : listIndexerActionFailed )
        {
            IndexerActionHome.create( failedIndexerAction );
        }

        return nNbIndexedIdentities;
    }
}

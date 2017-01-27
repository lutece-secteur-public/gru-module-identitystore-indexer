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
package fr.paris.lutece.plugins.identitystore.modules.indexer.service.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.indexing.IIndexer;
import fr.paris.lutece.plugins.identitystore.business.Identity;
import fr.paris.lutece.plugins.identitystore.business.IdentityHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerAction;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerTask;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * Implementation of the IElasticSearchIndexer interface for Identity
 */
public class IdentityElasticSearchIndexer implements IIndexer
{
        
        private static final String PROPERTY_ES_INDEXER_NAME = "identitystore-indexer.indexer.name";
        private static final String PROPERTY_ES_INDEXER_DESCRIPTION = "identitystore-indexer.indexer.description";
        private static final String PROPERTY_ES_INDEXER_VERSION = "identitystore-indexer.indexer.version";
        private static final String PROPERTY_ES_INDEXER_ENABLE = "identitystore-indexer.indexer.enable";  
        private static final String PLUGIN_NAME = "identitystore-indexer";
        private static final String ENABLE_VALUE_TRUE = "1";

        /**
         * {@inheritDoc }
         */
        @Override
        public String getName( )
        {
            return AppPropertiesService.getProperty( PROPERTY_ES_INDEXER_NAME );
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public String getVersion( )
        {
            return AppPropertiesService.getProperty( PROPERTY_ES_INDEXER_VERSION );
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public String getDescription( )
        {
            return AppPropertiesService.getProperty( PROPERTY_ES_INDEXER_DESCRIPTION );
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public boolean isEnable( )
        {
            boolean bReturn = false;
            String strEnable = AppPropertiesService.getProperty( PROPERTY_ES_INDEXER_ENABLE );

            if ( ( Boolean.parseBoolean( strEnable ) || strEnable.equals( ENABLE_VALUE_TRUE ) )
                    && PluginService.isPluginEnable( PLUGIN_NAME ) )
            {
                bReturn = true;
            }

            return bReturn;
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void indexAllElements( )
        {
            // Get all identity from the table
            List<Identity> listIdentity = IdentityHome.getIdentitysList( );
            if( listIdentity != null && !listIdentity.isEmpty( ) )
            {
                List<IndexerAction> listIndexerAction = new ArrayList<IndexerAction>( );
                for ( Identity identity : listIdentity )
                {
                    IndexerAction indexerAction = new IndexerAction( );
                    indexerAction.setCustomerId( identity.getCustomerId( ) );
                    indexerAction.setTask( IndexerTask.CREATE );
                    listIndexerAction.add( indexerAction );
                }
                
                // Store all indetity in daemon indexer table
                IndexerActionHome.createAll( listIndexerAction );
            }
        }

}

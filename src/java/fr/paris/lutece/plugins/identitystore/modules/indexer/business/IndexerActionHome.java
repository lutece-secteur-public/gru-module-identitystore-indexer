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
package fr.paris.lutece.plugins.identitystore.modules.indexer.business;

import fr.paris.lutece.plugins.identitystore.modules.indexer.service.IdentityStoreIndexerPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for
 * IndexerAction objects
 */
public final class IndexerActionHome
{
    // Static variable pointed at the DAO instance
    private static IIndexerActionDAO _dao = SpringContextService.getBean( "identitystore-indexer.indexerActionDAO" );
    private static Plugin _plugin = PluginService.getPlugin( IdentityStoreIndexerPlugin.PLUGIN_NAME );

    /**
     * Private constructor
     */
    private IndexerActionHome(  )
    {
    }

    /**
     * Creation of an instance of Indexer Action
     * @param indexerAction The instance of the indexer action which contains
     *            the informations to store
     */
    public static synchronized void create( IndexerAction indexerAction )
    {
        _dao.insert( indexerAction, _plugin );
    }

    /**
     * Creation of a list of IndexerAction
     * @param listIndexerActions The list of the indexer actions
     */
    public static synchronized void createAll( List<IndexerAction> listIndexerActions )
    {
        _dao.insertAll( listIndexerActions, _plugin );
    }

    /**
     * Removes the indexerAction whose identifier is specified in parameter
     * @param nId The IndexerActionId
     */
    public static synchronized void remove( int nId )
    {
        _dao.delete( nId, _plugin );
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Loads the data of all the IndexerAction depending on the filter and returns
     * them in a list
     * @param filter the filter
     * @return the list which contains the data of all the indexerAction
     */
    public static List<IndexerAction> getList( IndexerActionFilter filter )
    {
        return _dao.selectList( filter, _plugin );
    }
}

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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;

/**
 *
 * Interface for an IndexerActionDAO
 *
 */
public interface IIndexerActionDAO
{
    /**
     * Generates a new primary key
     *
     * @param plugin
     *            the plugin
     * @return The new primary key
     */
    int newPrimaryKey( Plugin plugin );

    /**
     * Inserts a new record in the table.
     *
     * @param indexerAction
     *            instance of the IndexerAction object to insert
     * @param plugin
     *            the plugin
     */
    void insert( IndexerAction indexerAction, Plugin plugin );

    /**
     * Inserts in the table all the indexer actions contained in the specified list.
     *
     * @param listIndexerActions
     *            the list of the IndexerAction objects to insert
     * @param plugin
     *            the plugin
     */
    void insertAll( List<IndexerAction> listIndexerActions, Plugin plugin );

    /**
     * Deletes a record from the table
     *
     * @param nId
     *            The identifier of the action
     * @param plugin
     *            the plugin
     */
    void delete( int nId, Plugin plugin );

    /**
     * Loads the data of all indexerAction and returns them in a list
     * 
     * @param filter
     *            the search filter
     * @param plugin
     *            the plugin
     * @return The list which contains the data of all actions
     */
    List<IndexerAction> selectList( IndexerActionFilter filter, Plugin plugin );
}

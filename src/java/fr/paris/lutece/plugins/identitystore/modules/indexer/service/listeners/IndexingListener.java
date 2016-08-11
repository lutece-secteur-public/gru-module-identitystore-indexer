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
package fr.paris.lutece.plugins.identitystore.modules.indexer.service.listeners;

import fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners.IndexerAction;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners.IndexerActionFilter;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.service.AttributeChange;
import fr.paris.lutece.plugins.identitystore.service.AttributeChangeListener;

import java.util.List;


/**
 * This class is a listener for indexing an identity when a attribute changes
 *
 */
public class IndexingListener implements AttributeChangeListener
{
    private static final String SERVICE_NAME = "Identity indexing listener";

    @Override
    public String getName(  )
    {
        return SERVICE_NAME;
    }

    @Override
    public void processAttributeChange( AttributeChange change )
    {
        int nIdCustomer = change.getCustomerId(  );

        IndexerActionFilter filter = new IndexerActionFilter(  );
        filter.setIdCustomer( nIdCustomer );

        List<IndexerAction> listIndexerActions = IndexerActionHome.getList( filter );

        if ( listIndexerActions.isEmpty(  ) )
        {
            IndexerAction indexerAction = new IndexerAction(  );
            indexerAction.setIdCustomer( nIdCustomer );
            indexerAction.setIdTask( change.getChangeType(  ).getValue(  ) );

            IndexerActionHome.create( indexerAction );
        }
    }
}
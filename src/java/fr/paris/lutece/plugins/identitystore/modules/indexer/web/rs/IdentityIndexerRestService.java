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
package fr.paris.lutece.plugins.identitystore.modules.indexer.web.rs;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionFilter;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.IndexerTask;
import fr.paris.lutece.plugins.identitystore.modules.indexer.service.IIdentityIndexerService;
import fr.paris.lutece.plugins.rest.service.RestConstants;

/**
 * This class is a Web service to index identities
 *
 */
@Path( RestConstants.BASE_PATH + IdentityIndexerRestService.PLUGIN_PATH + IdentityIndexerRestService.INDEXING_PATH )
public class IdentityIndexerRestService
{
    protected static final String PLUGIN_PATH = "identitystoreindexer";
    protected static final String INDEXING_PATH = "/indexing";
    protected static final String FULL_INDEXING_PATH = "/full";

    private final IIdentityIndexerService _identityIndexerService;

    /**
     * Constructor
     * 
     * @param identityIndexerService
     *            the identity indexer service
     */
    @Inject
    public IdentityIndexerRestService( IIdentityIndexerService identityIndexerService )
    {
        _identityIndexerService = identityIndexerService;
    }

    /**
     * Performs a full indexing
     * 
     * @return a response with HTTP code 200 if there is no error during the full indexing, a response with HTTP code 500 otherwise.
     */
    @POST
    @Path( FULL_INDEXING_PATH )
    public Response fullIndexing( )
    {
        Response response = Response.ok( ).build( );

        try
        {
            // First, remove the index for all identities
            _identityIndexerService.deleteAllIndexes( );

            // Then remove all indexer actions stored - no filter
            IndexerActionHome.deleteByFilter( new IndexerActionFilter( ) );

            // Then store all identities in daemon indexer table
            IndexerActionHome.createAllByIdTask( IndexerTask.CREATE.getValue( ) );

        }
        catch( IndexingException e )
        {
            response = Response.status( Status.INTERNAL_SERVER_ERROR ).type( MediaType.TEXT_PLAIN ).entity( "An error occurred during the full indexing" )
                    .build( );
        }

        return response;
    }
}

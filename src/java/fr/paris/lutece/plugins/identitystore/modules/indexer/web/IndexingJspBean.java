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
package fr.paris.lutece.plugins.identitystore.modules.indexer.web;

import fr.paris.lutece.plugins.identitystore.business.IdentityHome;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners.IndexerAction;
import fr.paris.lutece.plugins.identitystore.modules.indexer.business.listeners.IndexerActionHome;
import fr.paris.lutece.plugins.identitystore.service.AttributeChangeType;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to index identities from Identity Store
 *
 */
@Controller( controllerJsp = IndexingJspBean.ADMIN_FEATURE_CONTROLLER_JSP, controllerPath = IndexingJspBean.ADMIN_FEATURE_CONTROLLLER_PATH, right = IndexingJspBean.ADMIN_FEATURE_RIGHT )
public class IndexingJspBean extends MVCAdminJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    public static final String ADMIN_FEATURE_CONTROLLER_JSP = "IndexIdentities.jsp";
    public static final String ADMIN_FEATURE_CONTROLLLER_PATH = "jsp/admin/plugins/identitystore/modules/indexer/";
    public static final String ADMIN_FEATURE_RIGHT = "INDENTITY_STORE_INDEXER_INDEX_IDENTITIES";

    // Views
    private static final String VIEW_INDEX_IDENTITIES = "indexIdentities";

    // Actions
    private static final String ACTION_INDEX_IDENTITIES = "doIndexIdentities";

    // Templates
    private static final String TEMPLATE_INDEX_IDENTITIES = "/admin/plugins/identitystore/modules/indexer/index_identities.html";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_INDEX_IDENTITIES = "module.identitystore.indexer.index.identities.pageTitle";

    // Infos
    private static final String INFO_IDENTITIES_INDEXING_SUCCESS = "module.identitystore.indexer.info.index.identities.success";

    // Other constants
    private static final long serialVersionUID = 1L;

    /**
     * Builds the page to index identities
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_INDEX_IDENTITIES, defaultView = true )
    public String getIndexIdentities( HttpServletRequest request )
    {
        return getPage( PROPERTY_PAGE_TITLE_INDEX_IDENTITIES, TEMPLATE_INDEX_IDENTITIES );
    }

    /**
     * Indexes the identities
     * @param request The Http Request
     * @return The JSP URL to display after the process
     */
    @Action( ACTION_INDEX_IDENTITIES )
    public String doIndexIdentities( HttpServletRequest request )
    {
        List<Integer> listCustomerIds = IdentityHome.getCustomerIdsList(  );
        List<IndexerAction> listIndexerActions = new ArrayList<IndexerAction>( listCustomerIds.size(  ) );

        for ( Integer nCustomerId : listCustomerIds )
        {
            IndexerAction indexerAction = new IndexerAction(  );
            indexerAction.setIdCustomer( nCustomerId );
            indexerAction.setIdTask( AttributeChangeType.UPDATE.getValue(  ) );

            listIndexerActions.add( indexerAction );
        }

        IndexerActionHome.createAll( listIndexerActions );

        addInfo( INFO_IDENTITIES_INDEXING_SUCCESS, getLocale(  ) );

        return redirectView( request, VIEW_INDEX_IDENTITIES );
    }
}

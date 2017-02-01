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
package fr.paris.lutece.plugins.identitystore.modules.indexer.service;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.indexing.IIndexingService;
import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.identitystore.business.Identity;
import fr.paris.lutece.plugins.identitystore.business.IdentityAttribute;
import fr.paris.lutece.plugins.identitystore.business.IdentityConstants;
import fr.paris.lutece.plugins.identitystore.service.IdentityChange;
import fr.paris.lutece.plugins.identitystore.service.IdentityChangeType;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * This class represents a service for indexing
 */
public final class IndexService
{
    private static IndexService _singleton;

    private static final String ATTRIBUTE_IDENTITY_USER_GENDER = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_GENDER );
    private static final String ATTRIBUTE_IDENTITY_USER_NAME_GIVEN = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_NAME_GIVEN );
    private static final String ATTRIBUTE_IDENTITY_USER_NAME_PREFERRED_NAME = AppPropertiesService
            .getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_PREFERRED_NAME );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_ONLINE_EMAIL = AppPropertiesService
            .getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER = AppPropertiesService
            .getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_MOBILE_NUMBER = AppPropertiesService
            .getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_USER_BDATE = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_BDATE );
    private static final String BEAN_INDEX_SERVICE = "identitystore-indexer.customerIndexService";

    private static IIndexingService<Customer> _customerIndexService;

    /** private constructor */
    private IndexService( )
    {
    }

    /**
     * Returns the unique instance
     * 
     * @return The unique instance
     */
    public static IndexService instance( )
    {
        if ( _singleton == null )
        {
            _singleton = new IndexService( );
            _customerIndexService = SpringContextService.getBean( BEAN_INDEX_SERVICE );
        }

        return _singleton;
    }

    /**
     * Indexes the identity change
     *
     * @param identityChange
     *            The identity change
     */
    public void process( IdentityChange identityChange ) throws IndexingException
    {
        if ( ( identityChange.getChangeType( ).getValue( ) == IdentityChangeType.CREATE.getValue( ) )
                || ( identityChange.getChangeType( ).getValue( ) == IdentityChangeType.UPDATE.getValue( ) ) )
        {
            index( identityChange.getIdentity( ) );
        }
        else
            if ( identityChange.getChangeType( ).getValue( ) == IdentityChangeType.DELETE.getValue( ) )
            {
                deleteIndex( identityChange.getIdentity( ) );
            }
    }

    /**
     * {@inheritDoc }.
     *
     * @param identity
     *            the identity
     * @throws IndexingException
     *             indexing exception
     * */
    public void index( Identity identity ) throws IndexingException
    {
        Customer customer = buildCustomer( identity );

        _customerIndexService.index( customer );
    }

    /**
     * {@inheritDoc }.
     *
     * @param identity
     *            the identity
     * @throws IndexingException
     *             indexing exception
     * */
    public void deleteIndex( Identity identity ) throws IndexingException
    {
        Customer customer = buildCustomer( identity );

        _customerIndexService.deleteIndex( customer );
    }

    /**
     * Build an identity to a customer.
     *
     * @param identity
     *            the identity
     * @return the customer
     */
    private Customer buildCustomer( Identity identity )
    {
        Customer customer = new Customer( );

        customer.setId( identity.getCustomerId( ) );
        customer.setAccountGuid( identity.getConnectionId( ) );

        if ( identity.getAttributes( ) != null )
        {
            for ( IdentityAttribute attribute : identity.getAttributes( ).values( ) )
            {
                if ( ATTRIBUTE_IDENTITY_USER_GENDER.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setIdTitle( Integer.valueOf( getAttributeValue( attribute ) ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_NAME_GIVEN.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setFirstname( getAttributeValue( attribute ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_NAME_PREFERRED_NAME.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setLastname( getAttributeValue( attribute ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_ONLINE_EMAIL.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setEmail( getAttributeValue( attribute ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setFixedPhoneNumber( getAttributeValue( attribute ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_MOBILE_NUMBER.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setMobilePhone( getAttributeValue( attribute ) );
                }

                if ( ATTRIBUTE_IDENTITY_USER_BDATE.equals( attribute.getAttributeKey( ).getKeyName( ) ) )
                {
                    customer.setBirthDate( getAttributeValue( attribute ) );
                }
            }
        }

        return customer;
    }

    /**
     * Gets the attribute value from the identityAttribute
     * 
     * @param identityAttribute
     *            the identityAttribute
     * @return {@code null} if the identityAttribute does not exist, the identityAttribute value otherwise
     */
    private String getAttributeValue( IdentityAttribute identityAttribute )
    {
        return ( identityAttribute.getValue( ) == null ) ? StringUtils.EMPTY : identityAttribute.getValue( );
    }
}

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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.grustorageelastic.business.ESCustomerDTO;
import fr.paris.lutece.plugins.grustorageelastic.business.ElasticConnexion;
import fr.paris.lutece.plugins.grustorageelastic.util.constant.GRUElasticsConstants;
import fr.paris.lutece.plugins.identitystore.business.Identity;
import fr.paris.lutece.plugins.identitystore.business.IdentityAttribute;
import fr.paris.lutece.plugins.identitystore.business.IdentityConstants;
import fr.paris.lutece.plugins.identitystore.service.IdentityChange;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccessException;


/**
 * The Class ElasticNotificationStorageService.
 */
public class IdentityESIndexService implements IIdentityIndexService
{
    private static final String ATTRIBUTE_IDENTITY_USER_GENDER = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_GENDER );
    private static final String ATTRIBUTE_IDENTITY_USER_NAME_GIVEN = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_NAME_GIVEN );
    private static final String ATTRIBUTE_IDENTITY_USER_NAME_PREFERRED_NAME = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_PREFERRED_NAME );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_ONLINE_EMAIL = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_MOBILE_NUMBER = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_USER_BDATE = AppPropertiesService.getProperty( IdentityConstants.PROPERTY_ATTRIBUTE_USER_BDATE );
   
    /**
     * {@inheritDoc }.
     *
     * @param identity the identity
     * @exception HttpAccessException http access exception
     */
    @Override
    public void index( IdentityChange identityChange ) throws HttpAccessException
    {
        if ( identityChange != null && identityChange.getIdentity(  ) != null )
        {
            ObjectMapper mapper = new ObjectMapper(  );
            mapper.setSerializationInclusion( Include.NON_NULL );

            String jsonUser;

            try
            {
                ESCustomerDTO customerDTO = buildCustomer( identityChange.getIdentity(  ) );
                jsonUser = mapper.writeValueAsString( customerDTO );
                ElasticConnexion.sentToElasticPOST( ElasticConnexion.getESParam( GRUElasticsConstants.PATH_ELK_TYPE_USER,
                        identityChange.getIdentity(  ).getCustomerId(  ) ), jsonUser );
            }
            catch ( JsonProcessingException ex )
            {
                AppLogService.error( ex + " :" + ex.getMessage(  ), ex );
            }
        }
    }

    /**
     * Build an identity to a customer.
     *
     * @param identity the customer identity
     * @return the customer
     */
    private ESCustomerDTO buildCustomer( Identity identity )
    {
        ESCustomerDTO customer = new ESCustomerDTO(  );

        customer.setCustomerId( identity.getCustomerId(  ) );
        customer.setConnectionId( identity.getConnectionId(  ) );
        
        for ( IdentityAttribute attribute : identity.getAttributes( ).values( ) )
        {

            if ( ATTRIBUTE_IDENTITY_USER_GENDER.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setCivility( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_NAME_GIVEN.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setFirstName( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_NAME_PREFERRED_NAME.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setName( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_ONLINE_EMAIL.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setEmail( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setFixedTelephoneNumber( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_HOMEINFO_TELECOM_MOBILE_NUMBER.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setTelephoneNumber( getAttributeValue( attribute ) );
            }
            if ( ATTRIBUTE_IDENTITY_USER_BDATE.equals( attribute.getAttributeKey(  ).getKeyName(  ) ) )
            {
                customer.setBirthday( getAttributeValue( attribute ) );
            }
        }
        
        customer.setStayConnected( true );
        customer.setSuggest(  );
        
        return customer;
    }

    /**
     * Gets the attribute value from the identityAttribute
     * @param identityAttribute the identityAttribute
     * @return {@code null} if the identityAttribute does not exist, the identityAttribute value otherwise
     */
    private String getAttributeValue( IdentityAttribute identityAttribute )
    {
        return ( identityAttribute.getValue(  ) == null ) ? StringUtils.EMPTY : identityAttribute.getValue(  );
    }
}

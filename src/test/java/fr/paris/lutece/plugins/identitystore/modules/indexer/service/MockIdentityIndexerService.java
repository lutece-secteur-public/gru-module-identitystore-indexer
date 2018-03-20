package fr.paris.lutece.plugins.identitystore.modules.indexer.service;

import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.identitystore.service.IdentityChange;

public class MockIdentityIndexerService implements IIdentityIndexerService
{
    private boolean _bMustThrowException;

    @Override
    public void index( IdentityChange identityChange ) throws IndexingException
    {
        error( "index( IdentityChange )" );

    }

    @Override
    public void index( List<IdentityChange> listIdentityChange ) throws IndexingException
    {
        error( "index( List<IdentityChange> )" );
    }

    @Override
    public void deleteAllIndexes( ) throws IndexingException
    {
        error( "deleteAllIndexes( )" );
    }

    private void error( String strError ) throws IndexingException
    {
        if ( _bMustThrowException )
        {
            throw new IndexingException( strError );
        }
    }

    public void mustThrowException( boolean bMustThrowException )
    {
        _bMustThrowException = bMustThrowException;
    }

}

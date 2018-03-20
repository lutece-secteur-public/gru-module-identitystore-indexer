package fr.paris.lutece.plugins.identitystore.modules.indexer.web.rs;

import fr.paris.lutece.plugins.identitystore.modules.indexer.service.MockIdentityIndexerService;
import fr.paris.lutece.test.LuteceTestCase;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IdentityIndexerRestServiceTest extends LuteceTestCase
{
    private final MockIdentityIndexerService _identityIndexerService = new MockIdentityIndexerService( );
    private final IdentityIndexerRestService _restService = new IdentityIndexerRestService( _identityIndexerService );

    public void testFullIndexingResponseWhenExceptionOccurs( )
    {
        _identityIndexerService.mustThrowException( true );

        Response response = _restService.fullIndexing( );

        assertThat( response.getStatus( ), is( Status.INTERNAL_SERVER_ERROR.getStatusCode( ) ) );
        assertThat( response.getEntity( ), is( "An error occurred durring the full indexing" ) );
    }

    public void testFullIndexingResponseWhenNoExceptionOccurs( )
    {
        Response response = _restService.fullIndexing( );

        assertThat( response.getStatus( ), is( Status.OK.getStatusCode( ) ) );
    }
}

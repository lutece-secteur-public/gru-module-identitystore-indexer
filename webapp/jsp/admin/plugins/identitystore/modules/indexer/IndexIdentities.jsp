<jsp:useBean id="indexIdentities" scope="session" class="fr.paris.lutece.plugins.identitystore.modules.indexer.web.IndexingJspBean" />

<% String strContent = indexIdentities.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>
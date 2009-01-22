package org.owasp.proxy.daemon;

import org.owasp.proxy.model.Conversation;
import org.owasp.proxy.model.MessageFormatException;
import org.owasp.proxy.model.Request;
import org.owasp.proxy.model.Response;

public class DefaultProxyMonitor implements ProxyMonitor {

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#requestReceived(org.owasp.proxy.model.Request)
	 */
	public Response requestReceived(Request request)
			throws MessageFormatException {
		String connection = request.getHeader("Connection");
		String version = request.getVersion();
		if ("HTTP/1.1".equals(version) && connection != null) {
			String[] headers = connection.split(" *, *");
			for (int i = 0; i < headers.length; i++) {
				request.deleteHeader(headers[i]);
			}
		}
		request.deleteHeader("Proxy-Connection");
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#errorReadingRequest(org.owasp.proxy.model.Request, java.lang.Exception)
	 */
	public Response errorReadingRequest(Request request, Exception e)
			throws MessageFormatException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#responseHeaderReceived(org.owasp.proxy.model.Conversation)
	 */
	public boolean responseHeaderReceived(Conversation conversation)
			throws MessageFormatException {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#responseContentReceived(org.owasp.proxy.model.Conversation, boolean)
	 */
	public void responseContentReceived(Conversation conversation,
			boolean streamed) throws MessageFormatException {
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#errorFetchingResponseHeader(org.owasp.proxy.model.Request, java.lang.Exception)
	 */
	public Response errorFetchingResponseHeader(Request request, Exception e)
			throws MessageFormatException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#errorFetchingResponseContent(org.owasp.proxy.model.Conversation, java.lang.Exception)
	 */
	public Response errorFetchingResponseContent(Conversation conversation,
			Exception e) throws MessageFormatException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#wroteResponseToBrowser(org.owasp.proxy.model.Conversation)
	 */
	public void wroteResponseToBrowser(Conversation conversation)
			throws MessageFormatException {
	}

	/* (non-Javadoc)
	 * @see org.owasp.proxy.daemon.ProxyMonitor#errorWritingResponseToBrowser(org.owasp.proxy.model.Conversation, java.lang.Exception)
	 */
	public void errorWritingResponseToBrowser(Conversation conversation,
			Exception e) throws MessageFormatException {
	}

}
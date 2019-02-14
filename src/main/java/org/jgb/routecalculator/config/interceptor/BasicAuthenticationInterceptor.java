package org.jgb.routecalculator.config.interceptor;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author jgb
 * @since 13/02/19 17:32
 */
public class BasicAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private final String username;
    private final String password;

    public BasicAuthenticationInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        //Build the auth-header
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        final String authHeader = "Basic " + new String(encodedAuth);

        //Add the auth-header
        request.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);

        return execution.execute(request, body);
    }

}

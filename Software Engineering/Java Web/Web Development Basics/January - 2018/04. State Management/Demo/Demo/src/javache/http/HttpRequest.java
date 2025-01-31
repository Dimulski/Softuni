package javache.http;

import java.util.HashMap;

public interface HttpRequest {

//    HttpSession getSession();

    HashMap<String, String> getHeaders();

    HashMap<String, String> getBodyParameters();

    HashMap<String, String> getCookies();

    String getMethod();

    void setMethod(String method);

    String getRequestUrl();

    void setRequestUrl(String requestUrl);

    void addHeader(String header, String value);

    void addBodyParameter(String parameter, String value);

    boolean isResource();
}

package org.wso2.carbon.scriptengine.util;


import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;
import org.wso2.carbon.scriptengine.exceptions.ScriptException;
import org.wso2.javascript.xmlimpl.XML;
import org.wso2.javascript.xmlimpl.XMLList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HostObjectUtil {

    private static final Log log = LogFactory.getLog(HostObjectUtil.class);

    private static final String FORMAT_DATE_ISO="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_ISO);

    static {
        dateFormat.setTimeZone(TimeZone.getDefault());
    }

    public static void invalidArgsError(String hostObject, String function, String index, String required, Object provided,
                                        boolean constructor) throws ScriptException {
        String msg = "Invalid argument for the " + (constructor ? "constructor" : "function") + ". HostObject : " +
                hostObject + ", " + (constructor ? "Constructor" : "Method") + " : " + function +
                ", Param Index : " + index + ", Required Type : " + required + ", Provided Type : " +
                provided.toString();
        log.warn(msg);
        throw new ScriptException(msg);
    }

    public static void invalidNumberOfArgs(String hostObject, String function, int size, boolean constructor) throws ScriptException {
        String msg = "Invalid number of arguments have been provided. HostObject : " + hostObject + ", " +
                (constructor ? "Constructor" : "Method") + " : " + function + ", Args Count : " + size;
        log.warn(msg);
        throw new ScriptException(msg);
    }

    public static void invalidProperty(String hostObject, String property, String required, Object provided) throws ScriptException {
        String msg = "Invalid property value. HostObject : " + hostObject + ", Property : " + property + ", Required Type : " + required +
                ", Provided Type : " + provided.toString();
        log.warn(msg);
        throw new ScriptException(msg);
    }

    public static void getReservedHostObjectWarn(String hostObject) throws ScriptException {
        String msg = hostObject + " HostObject has been reserved by WSO2 Scripts and cannot be instantiated by a script.";
        log.warn(msg);
        throw new ScriptException(msg);
    }

    public static String serializeJSON(Object obj) {
        StringWriter json = new StringWriter();
        if (obj instanceof Wrapper) {
            obj = ((Wrapper) obj).unwrap();
        }

        if (obj instanceof NativeObject) {
            serializeNativeObject((NativeObject) obj, json);
        } else if (obj instanceof NativeArray) {
            serializeNativeArray((NativeArray) obj, json);
        } else if (obj instanceof Object[]) {
            serializeObjectArray((Object[]) obj, json);
        } else if (obj instanceof XML || obj instanceof XMLList) {
            serializeXML((ScriptableObject) obj, json);
        } else if (obj instanceof Integer ||
                obj instanceof Long ||
                obj instanceof Float ||
                obj instanceof Double ||
                obj instanceof Short ||
                obj instanceof BigInteger ||
                obj instanceof BigDecimal) {
            serializeNumber(obj, json);
        } else if(obj instanceof Boolean) {
            serializeBoolean((Boolean) obj, json);
        } else if (obj instanceof String) {
            serializeString((String) obj, json);
        } else {
            String className = obj.getClass().getName();
            if(className.equals("org.mozilla.javascript.NativeDate")) {
                serializeNativeDate((ScriptableObject) obj, json);
            } else {
                json.append("{}");
            }
        }
        return json.toString();
    }

    public static String serializeObject(Object obj) {
        if (obj instanceof Wrapper) {
            obj = ((Wrapper) obj).unwrap();
        }

        if (obj instanceof String ||
                obj instanceof Integer ||
                obj instanceof Long ||
                obj instanceof Float ||
                obj instanceof Double ||
                obj instanceof Short ||
                obj instanceof BigInteger ||
                obj instanceof BigDecimal ||
                obj instanceof Boolean) {
            return obj.toString();
        } else if (obj instanceof XML || obj instanceof XMLList) {
            return (String) ScriptableObject.callMethod((ScriptableObject) obj, "toXMLString", new Object[0]);
        } else {
            return serializeJSON(obj);
        }
    }

    public static String streamToString(InputStream is) throws ScriptException {
        try {
            StringBuilder sb = new StringBuilder();
            int count;
            while ((count = is.read()) != -1) {
                sb.append((char) count);
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            log.error(e);
            throw new ScriptException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.warn("Error while closing the input stream", e);
                }
            }
        }
    }

    public static String readerToString(Reader reader) throws ScriptException {
        StringBuilder sb = new StringBuilder();
        try {
            int data = reader.read();
            while (data != -1) {
                sb.append((char) data);
                data = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            String msg = "Error while reading the content from the Reader";
            log.error(msg, e);
            throw new ScriptException(msg, e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                log.warn(e);
            }
        }
    }

    public static int getURL(String urlString, String username,
                             String password) throws IOException {

        HttpMethod method = new GetMethod(urlString);
        URL url = new URL(urlString);
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient httpClient = new HttpClient(connectionManager);
        // We should not use method.setURI and set the complete URI here.
        // If we do so commons-httpclient will not use our custom socket factory.
        // Hence we set the path and query separatly
        method.setPath(url.getPath());
        method.setQueryString(url.getQuery());
        method.setRequestHeader("Host", url.getHost());
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        // If a username and a password is provided we support basic auth
        if ((username != null) && (password != null)) {
            Credentials creds = new UsernamePasswordCredentials(username, password);
            int port = url.getPort();
            httpClient.getState()
                    .setCredentials(new AuthScope(url.getHost(), port), creds);
        }
        return httpClient.executeMethod(method);
    }

    private static void serializeObjectArray(Object[] obj, StringWriter json) {
        json.append("[");
        boolean first = true;
        for (Object value : (Object[]) obj) {
            if (!first) {
                json.append(", ");
            } else {
                first = false;
            }
            json.append(serializeJSON(value));
        }
        json.append("]");
    }

    private static void serializeNumber(Object obj, StringWriter json) {
        json.append(obj.toString());
    }

    private static void serializeNativeDate(ScriptableObject obj, StringWriter json) {
        Double time = (Double) ScriptableObject.callMethod(obj, "getTime", new Object[0]);
        Date date = new Date(Math.round(time));
        json.append(dateFormat.format(date));
    }

    private static void serializeBoolean(Boolean obj, StringWriter json) {
        json.append(Boolean.toString(obj));
    }

    private static void serializeString(String obj, StringWriter json) {
        json.append("\"").append(obj.replaceAll("\\n", "\\\\n").replaceAll("\"", "\\\\\"")).append("\"");
    }

    private static void serializeXML(ScriptableObject obj, StringWriter json) {
        String xml = (String) ScriptableObject.callMethod(obj, "toXMLString", new Object[0]);
        xml = xml.replaceAll("\\n", "\\\\n").replaceAll("\"", "\\\\\"");
        json.append("\"").append(xml).append("\"");
    }

    private static void serializeNativeArray(NativeArray obj, StringWriter json) {
        json.append("[");
        Object[] ids = obj.getIds();
        boolean first = true;
        for (Object id : ids) {
            Object value = obj.get((Integer) id, obj);
            if (!first) {
                json.append(", ");
            } else {
                first = false;
            }
            json.append(serializeJSON(value));
        }
        json.append("]");
    }

    private static void serializeNativeObject(NativeObject obj, StringWriter json) {
        json.append("{");
        Object[] ids = obj.getIds();
        boolean first = true;
        for (Object id : ids) {
            String key = (String) id;
            Object value = obj.get((String) id, obj);
            if (!first) {
                json.append(", ");
            } else {
                first = false;
            }
            json.append("\"").append(key).append("\" : ").append(serializeJSON(value));
        }
        json.append("}");
    }
}

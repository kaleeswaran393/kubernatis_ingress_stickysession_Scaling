package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin()
@RestController
public class RoutingController {

    Logger logger = LoggerFactory.getLogger(RoutingController.class);

    private static final String endpoint = "https://192.168.99.100/";

    @RequestMapping(method = RequestMethod.POST, path = "/hit_backend")
    public ResponseOut routing(@RequestBody ResponseOut responseInput,
                                HttpServletResponse response) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Accept","application/json");
        requestHeaders.add("Cookie","Path="+responseInput.getTarget()+";"+
                "username="+responseInput.getUsername()+";"+
                "Max-Age=60");


        CloseableHttpClient httpClient = getCloseableHttpClient();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(converter);


        List<HttpMessageConverter<?>> msgConverters =
                new ArrayList<HttpMessageConverter<?>>(1);
        msgConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(msgConverters);

        HttpEntity requestEntity = new HttpEntity(responseInput, requestHeaders);
        String url = endpoint+responseInput.getTarget().replace("-","");
        logger.info(url);
        ResponseEntity<ResponseOut> resp = restTemplate.exchange(url,
                                  HttpMethod.POST,requestEntity, ResponseOut.class);
      return  (ResponseOut) resp.getBody();
    }

    @RequestMapping(method = RequestMethod.POST, path ="/backend1", produces = {"application/json"},consumes = {"application/json"})
    public  ResponseEntity<ResponseOut> sessionCall(
                                   @RequestBody ResponseOut responseInput,
                                   HttpServletRequest response
                                   ) throws Exception {

        InetAddress inetAddress = InetAddress.getLocalHost();
        responseInput.setPodId(inetAddress.getHostName());
        responseInput.setUsername(responseInput.getUsername());
        responseInput.setTarget(responseInput.getTarget());
        System.out.println(responseInput);
        ResponseEntity<ResponseOut> responseEntity = new ResponseEntity<>(responseInput,
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, path ="/backend2", produces = {"application/json"},consumes = {"application/json"})
    public  ResponseEntity<ResponseOut> sessionCall2(
            @RequestBody ResponseOut responseInput,
            HttpServletRequest response
    ) throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        responseInput.setPodId(inetAddress.getHostName());
        responseInput.setUsername(responseInput.getUsername());
        responseInput.setTarget(responseInput.getTarget());
        System.out.println(responseInput);
        ResponseEntity<ResponseOut> responseEntity = new ResponseEntity<>(responseInput,
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, path ="/backend3", produces = {"application/json"},consumes = {"application/json"})
    public  ResponseEntity<ResponseOut> sessionCall3(
            @RequestBody ResponseOut responseInput,
            HttpServletRequest response
    ) throws Exception {

        InetAddress inetAddress = InetAddress.getLocalHost();
        responseInput.setPodId(inetAddress.getHostName());
        responseInput.setUsername(responseInput.getUsername());
        responseInput.setTarget(responseInput.getTarget());
        System.out.println(responseInput);
        ResponseEntity<ResponseOut> responseEntity = new ResponseEntity<>(responseInput,
                HttpStatus.OK);
        return responseEntity;
    }




    public  CloseableHttpClient getCloseableHttpClient()
    {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().
                    setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).
                    setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
                    {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                        {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException e) {
            logger.error("KeyManagementException in creating http client instance", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException in creating http client instance", e);
        } catch (KeyStoreException e) {
            logger.error("KeyStoreException in creating http client instance", e);
        }
        return httpClient;
    }
}

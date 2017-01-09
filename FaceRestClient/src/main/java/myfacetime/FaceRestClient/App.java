package myfacetime.FaceRestClient;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
class Login
{
   private String api_key;// : "fcm_153b23df5d5ea49d2870f8e9153d16e0"

   public String getApi_key()
   {
      return api_key;
   }

   public void setApi_key(String api_key)
   {
      this.api_key = api_key;
   }

}

class LoginResponse
{
   private String token;
   private String type;
   private String expires;
   public String getToken()
   {
      return token;
   }
   public void setToken(String token)
   {
      this.token = token;
   }
   public String getType()
   {
      return type;
   }
   public void setType(String type)
   {
      this.type = type;
   }
   public String getExpires()
   {
      return expires;
   }
   public void setExpires(String expires)
   {
      this.expires = expires;
   }
   @Override
   public String toString()
   {
      return "LoginResponse [token=" + token + ", type=" + type + ", expires=" + expires + "]";
   }
   
   
}

public class App
{

   public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
   {
      System.out.println("Hello World!");
      Login log_in = new Login();
      log_in.setApi_key("fcmbad5b05b42a6359e5227089e535fa43ffcm");
      String url = "https://api.facematica.vocord.ru/v1/account/login";

      //create a 509 certificate for SSL processing
      TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
         @Override
         public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
             return true;
         }
      }; 

      //setup trust strategy as SSL context.
      SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
      
      //create SSL socket factory
      SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
      
      //setup http client with SSL socket factory
      CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
      
      //create http client request factory
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      
      //save http client  to client request factory
      requestFactory.setHttpClient(httpClient);
      
      //create rest template class
      RestTemplate restTemplate = new RestTemplate(requestFactory);
     
      //create http message converter. setup diff media types to convert. add the converter to the resttemplate converter list.
      MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
      mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
      restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
      
      //make request for token. 
      ResponseEntity<LoginResponse> resp_ent = restTemplate.postForEntity(url, log_in, LoginResponse.class);
      LoginResponse login_resp = resp_ent.getBody();
      
      //print
      System.out.println(resp_ent.getStatusCode());
      System.out.println(resp_ent.getBody());
      System.out.println(login_resp);
      
      //Authorization: bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9... 
      //Content-Type: multipart/form-data;      
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", login_resp.getType()+" "+login_resp.getToken());
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      //Content-Disposition: form-data; 
      //name="img1"; 
      //filename="image1.jpg" 
      //Content-Type: image/jpeg 
      MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
      
      parts.add("Content-Disposition", "form-data");      
      parts.add("Content-Type", "image/jpeg");
      parts.add("name", "jim_img");
      parts.add("filename", "jim_img.jpg");
      //parts.add("file", new byte[10]);

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
            new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

    String url2 = "https://api.facematica.vocord.ru/v1/face/detect";
    ResponseEntity<String> resp_fd_ent = restTemplate.postForEntity(url2, requestEntity, String.class);
    
//    ResponseEntity<String> resp_fd_ent = restTemplate.exchange(url2, HttpMethod.POST, requestEntity, String.class);
    
    System.out.println(resp_fd_ent.getStatusCode());
    System.out.println(resp_fd_ent.getBody());
    
    
   }
}

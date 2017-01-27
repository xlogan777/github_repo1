package com.facerest;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FaceRestController
{
   private static Logger log = Logger.getLogger(FaceRestController.class.getName());
   
   public FaceRestController()
   {
      log.debug("started rest controller.");
   }
   
   @RequestMapping(value = "/faceRestLogin", method = RequestMethod.GET, produces="application/json")
   public ResponseEntity<LoginResponse> faceRestLogin() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException 
   {
      Login log_in = new Login();
      log_in.setApi_key("fcmbad5b05b42a6359e5227089e535fa43ffcm");
      String url = "https://api.facematica.vocord.ru/v1/account/login";

      // create a 509 certificate for SSL processing
      TrustStrategy acceptingTrustStrategy = new TrustStrategy()
      {
         @Override
         public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
         {
            return true;
         }
      };

      // setup trust strategy as SSL context.
      SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

      // create SSL socket factory
      SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

      // setup http client with SSL socket factory
      CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

      // create http client request factory
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

      // save http client to client request factory
      requestFactory.setHttpClient(httpClient);

      // create rest template class
      RestTemplate restTemplate = new RestTemplate(requestFactory);

      // create http message converter. setup diff media types to convert. add
      // the converter to the resttemplate converter list.
//      MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//      mappingJackson2HttpMessageConverter
//            .setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
//      restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

      // make request for token.
      ResponseEntity<LoginResponse> resp_ent = restTemplate.postForEntity(url, log_in, LoginResponse.class);
      LoginResponse login_resp = resp_ent.getBody();

      // print login response for token
      log.debug(resp_ent.getStatusCode());
      log.debug(resp_ent.getBody());
      log.debug(login_resp);
      
      ResponseEntity<LoginResponse> login_resp_entity = 
            new ResponseEntity<LoginResponse>(login_resp, HttpStatus.OK);

      return login_resp_entity;
   }
   
   @RequestMapping(value = "/faceUploadImage", method = RequestMethod.POST, produces="application/json")
   public ResponseEntity<String> faceUploadImage
   (
    @RequestBody ImageRequest imageRequest
   )
   {
      String url2 = "https://api.facematica.vocord.ru/v1/face/detect";
      
      //add file to request body for images
      LinkedMultiValueMap<String, Object> map_request_body = new LinkedMultiValueMap<String, Object>();
      map_request_body.add("imageFile", imageRequest.getImage());
      //String file1 = "C:/Users/jimmy/Desktop/test1.jpg";
      //String file3 = "C:/Users/jimmy/Desktop/test3.jpg";
      //map_request_body.add("file1", new FileSystemResource(file1));
      //map_request_body.add("file3", new FileSystemResource(file3));
      
      //setup http header with authorization and multi part form.
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", imageRequest.getLoginInfo().getType() + " " + imageRequest.getLoginInfo().getToken());
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      //create http entity with header and request body.
      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map_request_body, headers);
      
      RestTemplate restTemplate = new RestTemplate();
      
      //get http post response for this post request for image upload
      String response = restTemplate.postForObject(url2, requestEntity, String.class);
      
      //print response
      log.debug(response);
      
      ResponseEntity<String> upload_resp_entity = 
            new ResponseEntity<String>(response, HttpStatus.OK);
      
      return upload_resp_entity;
   }
}

package com.facerest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@RestController
public class FaceRestController
{
   private static Logger log = Logger.getLogger(FaceRestController.class.getName());
   
   public FaceRestController()
   {
      log.info("started rest controller.");
   }
   
   @CrossOrigin
   @RequestMapping(value = "/myTest1", method = RequestMethod.GET, produces="application/json")
   public String myTest1()
   {
      Gson gson = new Gson();
      ImageRequest img_request = new ImageRequest();
      
      img_request.setToken("asdfa");
      img_request.setType("adsfsdrgwert");
      img_request.setImage(new byte[]{20,30,50,100});
      
      String val = gson.toJson(img_request);
      log.info(val);
      
      return val;
   }
   
   @CrossOrigin
   @RequestMapping(value = "/faceRestLogin", method = RequestMethod.GET, produces="application/json")
   public ResponseEntity<LoginResponse> faceRestLogin() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException 
   {
      Login log_in = new Login();
      log_in.setApi_key("fcmbad5b05b42a6359e5227089e535fa43ffcm");
      String url = "https://api.facematica.vocord.ru/v1/account/login";

      RestTemplate restTemplate = getRestTemplate();

      // make request for token.
      ResponseEntity<LoginResponse> resp_ent = restTemplate.postForEntity(url, log_in, LoginResponse.class);
      LoginResponse login_resp = resp_ent.getBody();

      // print login response for token
      log.info(resp_ent.getStatusCode());
      log.info(resp_ent.getBody());
      log.info(login_resp);
      
      ResponseEntity<LoginResponse> login_resp_entity = 
            new ResponseEntity<LoginResponse>(login_resp, HttpStatus.OK);

      return login_resp_entity;
   }
   
   @CrossOrigin
   @RequestMapping(value = "/faceUploadImage", method = RequestMethod.POST, produces="application/json")
   public ResponseEntity<String> faceUploadImage
   (
    @RequestBody ImageRequest imageRequest
   ) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException
   {
      //log.info(imageRequest);
      
      String url2 = "https://api.facematica.vocord.ru/v1/face/detect";
      
      //create image file from array of byes
      File someFile = new File("tmp.jpeg");
      FileOutputStream fos = new FileOutputStream(someFile);
      fos.write(imageRequest.getImage());
      fos.flush();
      fos.close();
      
      //add file to request body for images
      LinkedMultiValueMap<String, Object> map_request_body = new LinkedMultiValueMap<String, Object>();      
      map_request_body.add("imageFile", new FileSystemResource(someFile));
      
      //setup http header with authorization and multi part form.
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", imageRequest.getType() + " " + imageRequest.getToken());
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      //create http entity with header and request body.
      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map_request_body, headers);
      
      //create rest template
      RestTemplate restTemplate = getRestTemplate();
      
      //get http post response for this post request for image upload
      String response = restTemplate.postForObject(url2, requestEntity, String.class);
      
      //print response
      log.info(response);
      
      ResponseEntity<String> upload_resp_entity = 
            new ResponseEntity<String>(response, HttpStatus.OK);
      
      //delete the local file.
      someFile.delete();
      
      return upload_resp_entity;
   }
   
   public static RestTemplate getRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
   {
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
      MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
      mappingJackson2HttpMessageConverter
            .setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
      
      restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
      
      return restTemplate;
   }
}

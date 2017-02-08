package com.facerest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@SpringBootApplication
public class FaceRestMicroSvcApplication
{
   private static Logger log = Logger.getLogger(FaceRestMicroSvcApplication.class.getName());
   
   public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException
   {
//      Gson gson = new Gson();
//      ImageRequest img_request = new ImageRequest();
//      
//      img_request.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcGkuZmFjZW1hdGljYS52b2NvcmQucnUiLCJpYXQiOjE0ODY1MjM5MTcsImV4cCI6MTQ4NzEyODcxNywidXNlcmRhdGEiOnsiaWQiOiIxMTE0In19.9mGh-sWRBkyCtHO-mRv575HFB3xeGuk2SaANs2g7u8E");
//      img_request.setType("bearer");
//      
//      String file1 = "C:/Users/jimmy/Desktop/test.jpg";
//      Path path = Paths.get(file1);
//      byte[] data = Files.readAllBytes(path);
//      
//      img_request.setImage(data);
//      
//      //String val = gson.toJson(img_request);
//      //log.info(val);
//      
//      RestTemplate restTemplate = FaceRestController.getRestTemplate();
//      
//      long start = System.currentTimeMillis();
//      
//      //get http post response for this post request for image upload
//      String response = 
//            restTemplate.postForObject
//               ("http://ec2-52-90-152-134.compute-1.amazonaws.com:8080/faceUploadImage", 
//                     img_request, String.class);
//      
//      log.info("diff = "+(System.currentTimeMillis() - start));
//      
//      //print response
//      log.info(response);
      

       SpringApplication.run(FaceRestMicroSvcApplication.class, args);
   }
}

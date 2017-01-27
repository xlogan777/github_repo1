package com.facerest;

public class ImageRequest
{
   private String token;
   private String type;  
   private byte [] image;
   
   public ImageRequest()
   {
     
   }

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

   public byte[] getImage()
   {
      return image;
   }

   public void setImage(byte[] image)
   {
      this.image = image;
   }
}

package com.facerest;

public class ImageRequest
{
   private LoginResponse loginInfo;
   private byte [] image;
   
   public ImageRequest()
   {
     
   }

   public LoginResponse getLoginInfo()
   {
      return loginInfo;
   }

   public void setLoginInfo(LoginResponse loginInfo)
   {
      this.loginInfo = loginInfo;
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

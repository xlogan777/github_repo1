package com.facerest;

public class Login
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

   @Override
   public String toString()
   {
      return "Login [api_key=" + api_key + "]";
   }
}

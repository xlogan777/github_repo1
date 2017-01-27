package com.facerest;

public class LoginResponse
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

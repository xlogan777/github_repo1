package com.algm_code;

import java.util.HashMap;

public class HackerRankMain
{
   public int sockMerchant(int n, int[] ar) 
   {
       HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
       int rv = 0;

       for(int input : ar)
       {
           if(map.get(input) == null)
           {
              map.put(input,input); 
           }
           else
           {
               map.remove(input);
               rv++;
           }
       }

       return rv;
   }
   
   public int countingValleys(int n, String s) 
   {
      int ans = 0;
      int sea_lvl = 0;
      
      for(int i = 0; i < s.length(); i++)
      {
         if(s.charAt(i) == 'U')
         {
            sea_lvl++;
         }
         else if(s.charAt(i) == 'D')
         {
            sea_lvl--;
         }
         
         if(sea_lvl == 0 && s.charAt(i) == 'U')
         {
            ans++;
         }
      }
      
      return ans;
   }
   
   
   public long repeatedString(String s, long n)
   {
      long str_size = s.length();
      long a_count = 0;
      long div = n/str_size;
      
      //gets total number of a in the str.
      for(int i = 0; i < str_size; i++)
      {
           if(s.charAt(i) == 'a')
              a_count++;
      }
      
      a_count *= div;
      long total_repeat_count = str_size * div;
      
      long diff_max_adds = n - total_repeat_count;
      
      long loop_max = 0;
      if(diff_max_adds < str_size)
      {
          loop_max = diff_max_adds;
      }
      else
      {
          loop_max = str_size;
      }
      
      for(int i = 0; i < loop_max; i++)
      {
           if(s.charAt(i) == 'a')
              a_count++;
      }
      
      return a_count;
   }
   

   public int jumpingOnClouds(int[] c)
   {
      int jump_cnt = 0;
      int index = 0;
      
      while(index < c.length)
      {
         if((index+1 == c.length-1) && c[index+1]==0)
         {
            jump_cnt++;
            index++;
         }
         else if((index+1 == c.length-1) && c[index+1]==1)
         {
            index++;
         }
         else if(c[index+1]==0 && c[index+2]==0)
         {
            jump_cnt++;
            index+=2;
         }
         else if(c[index+1]==0 && c[index+2]==1)
         {
            jump_cnt++;
            index+=1;
         }
         else if(c[index+1]==1 && c[index+2]==0)
         {
            jump_cnt++;
            index+=2;
         }
         
         //to avoid 
         if(index == c.length-1)
            index++;
      }
      
      return jump_cnt;

   }
   
   public static void main(String [] args)
   {
      HackerRankMain hr = new HackerRankMain();

      //stock merchant algo
      int [] stock_array = {10, 20, 20, 10, 10, 30, 50, 10, 20};
      hr.sockMerchant(9,  stock_array);
      
      hr.countingValleys(8,"UDDDUDUU");
      
      //repeated string algo
      hr.repeatedString("abc", 10);
      hr.repeatedString("a", 1000000000000L);
      
      //cloud jumping algo
      int [] clouds = {0,0,0,0,1,0};
      int [] clouds2 = {0,0,1,0,0,1,0};
      int [] clouds3 = {0,0,0,1,0,0};
      
      hr.jumpingOnClouds(clouds);
      hr.jumpingOnClouds(clouds2);
      hr.jumpingOnClouds(clouds3);


   }
}

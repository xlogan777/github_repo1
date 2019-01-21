package com.algm_code;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class HackerRankMain
{
   //warm up challenges
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
 //warm up challenges
   //this test the max hour glass in a 6x6 matrix
   // 1 0 1
   //   1
   // 0 1 1
   //get all hour glass sums and return it to the caller the max sum.
   public int hourglassSum(int[][] arr) 
   {
      boolean initialized = false;
      int sum = 0;
      int main_row_end = arr.length/2+1;
      int main_col_end = main_row_end;
      
      for(int i = 0; i < main_row_end; i++)
      {
         for(int j = 0; j < main_col_end; j++)
         {
            int tmp_sum = arr[i][j]   + arr[i][j+1]   + arr[i][j+2]
                                      + arr[i+1][j+1] +
                          arr[i+2][j] + arr[i+2][j+1] + arr[i+2][j+2];
            
            if(initialized == false)
            {
               sum = tmp_sum;
               initialized = true;
            }
            else if(tmp_sum > sum)
            {
               sum = tmp_sum;
            }
         }
      }
      
      return sum;
   }
   
   //given 1,2,3,4,5, and rotation count = 4,
   //rotate array 4 times
   //1,2,3,4,5 => 2,3,4,5,1 => 3,4,5,1,2 => 4,5,1,2,3 => 5,1,2,3,4
   public int[] rotLeft(int[] a, int d) 
   {
      int tmp = 0;
      
      //loop control for num of rotations
      for(int i = 0; i < d; i++)
      {
         //save tmp to be copied at end.
         tmp = a[0];
         
         //copy from the 1 location to the prev loc until end of arr is reached.
         for(int j = 1; j < a.length; j++)
         {
            a[j-1] = a[j];
         }
         
         //copy tmp to end of array location
         a[a.length-1] = tmp;
      }
      
      return a;
   }
   
   public void minimumBribes(int[] q) 
   {

   }
   
   //sort array in ascending order with min set of swaps
   //given an unordered array.
   //my solution is to use selection sort, since it 
   //always finds the min/max by scanning entire array
   //and does least amt of swaps always.
//   public int minimumSwaps(int[] arr) 
//   {
//      int min_swaps = 0;
//      
//      for(int i = 0; i < arr.length; i++)
//      {
//         boolean swap_required = false;
//         int min_index = 0;
//         int min_val = arr[i];
//         
//         for(int j = i+1; j < arr.length; j++)
//         {
//            if(arr[j] < min_val)
//            {
//               min_index = j;
//               min_val = arr[j];
//               swap_required = true;
//            }
//         }
//         
//         if(swap_required)
//         {
//            int tmp = arr[i];
//            arr[i] = arr[min_index];
//            arr[min_index] = tmp;
//            min_swaps++;
//         }
//      }
//      
//      return min_swaps;
//   }

   public int minimumSwaps(int[] arr) 
   {
      int min_swaps = 0;
      int begin_idx = 0;
      int end_idx = arr.length-1;
      
      while(begin_idx < end_idx)
      {
         while(begin_idx == arr[begin_idx]-1 && begin_idx < end_idx)
         {
            begin_idx++;
         }
         
         while(end_idx == arr[end_idx]-1 && begin_idx < end_idx)
         {
            end_idx--;
         }
         
         //perform swap
         if(arr[begin_idx]-1 != begin_idx)
         {
            int found_idx = 0;
            //now need to scan from begin to end
            //where the correct value for the
            //begin_idx is, once that is found then swap with that one.
            for(int i = begin_idx; i <= end_idx; i++)
            {
               if(begin_idx+1 == arr[i])
               {
                  found_idx = i;
                  break;
               }
            }
            
            //swap begin idx with found index.
            int tmp = arr[begin_idx];
            arr[begin_idx] = arr[found_idx];
            arr[found_idx] = tmp;
            
            //increment swaps.
            min_swaps++;
         }
      }
      
      return min_swaps;
   }

   public void checkMagazine(String[] magazine, String[] note) 
   {
      HashMap<String, Integer> magazine_map = new HashMap<String,Integer>();

      //keep track of all words in the magazine in the map with a count.
      for(String s : magazine)
      {
         if(magazine_map.get(s) == null)
         {
            magazine_map.put(s, 1);
         }
         else
         {
            magazine_map.put(s, magazine_map.get(s)+1);
         }
      }
      
      String ans = "Yes";
      
      //go over note and check if note words exists in the magazine map.
      for(String s : note)
      {
         //if word is not there then stop and say no
         if(magazine_map.get(s) == null)
         {
            ans = "No";
            break;
         }
         else
         {
            //subtract 1 from magazine map and save it back to map.
            int val = magazine_map.get(s)-1;
            magazine_map.put(s, val);
            
            //if the word does exists, subtract 1 and check if we went < 0
            //then stop cause we dont have the words there in the map.
            if(val < 0)
            {
               ans = "No";
               break;
            }
         }
      }
      
      System.out.println(ans);
   }
   
   public String twoStrings(String s1, String s2) 
   {
      String ans = "NO";
      HashMap<Character,String> s1_map = new HashMap<Character, String>();

      for(int i = 0; i < s1.length(); i++)
      {
          s1_map.put(s1.charAt(i), "");
      }

      for(int i = 0; i < s2.length(); i++)
      {
          if(s1_map.containsKey(s2.charAt(i)))
          {
              ans = "YES";
              break;
          }
      }

      return ans;
   }
   
   public static void main(String [] args)
   {
      HackerRankMain hr = new HackerRankMain();
//warm up challenges
      
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
//warm up challenges      
      
//arrays interview prep kit.
      int [][] ab = 
      {
         {1, 1, 1, 0, 0, 0},
         {0, 1, 0, 0, 0, 0},
         {1, 1, 1, 0, 0, 0},
         {0, 0, 2, 4, 4, 0},
         {0, 0, 0, 2, 0, 0},
         {0, 0, 1, 2, 4, 0}
      };
      hr.hourglassSum(ab);
      
      int [] a = {1,2,3,4,5};
      hr.rotLeft(a, 4);
      
      int [] arr = {4, 3, 1, 2};
      int [] arr2 = {1, 3, 5, 2, 4, 6, 7};
      hr.minimumSwaps(arr);
      hr.minimumSwaps(arr2);
      
      String [] magazine = {"two", "times", "three", "is", "not", "four"};
      String [] note = {"two", "times", "two", "is", "four"};
      
      hr.checkMagazine(magazine, note);
      
      hr.twoStrings("hello", "world");
//arrays interview prep kit.

   }
}

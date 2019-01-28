package com.algm_code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
   
   public int sherlockAndAnagrams(String s) 
   {
      int rv = 0;
      
      ArrayList<String> tmp_str = new ArrayList<String>();
      
      int len = s.length();
      
      //get all substrings from the string.
      for(int i = 0; i < len; i++)
      {
         for(int j = i; j < len; j++)
         {
            tmp_str.add(s.substring(i, j+1));
         }
      }
      
      //in list use first one and find a len match.
      //if found sort both strings and match if they do then increase rv.
      for(int i = 0; i < tmp_str.size(); i++)
      {     
         String input1 = tmp_str.get(i);
         
         //test first string with 2nd string
         for(int j = i+1; j < tmp_str.size(); j++)
         {
            String input2 = tmp_str.get(j);
            
            //only if we have same size str match for str1 and str2
            if(input1.length() == input2.length())
            {
               //sort 1 string
               char [] tmp1 = input1.toCharArray();
               Arrays.sort(tmp1);
               String sorted_a = new String(tmp1);
               
               //sort 2 string
               char [] tmp2 = input2.toCharArray();
               Arrays.sort(tmp2);
               String sorted_b = new String(tmp2);
               
               //of they match then increase count.
               if(sorted_a.equals(sorted_b))
               {
                  rv++;
               }
            }
         }
      }
      
      return rv;
   }
   
   public List<Integer> freqQuery(int [][] queries) 
   {
      Map<Integer, Integer> valueToFreq = new HashMap<>();
      Map<Integer, Integer> freqToOccurrence = new HashMap<>();
      List<Integer> frequencies = new ArrayList<>();

      int key;
      int value;
      Integer oldFreq;
      Integer newFreq;
      Integer oldOccurrence;
      Integer newOccurrence;
      for( int i = 0; i < queries.length; i++) 
      {
          key = queries[i][0];
          value = queries[i][1];
          
          if (key == 3) 
          {
              frequencies.add(freqToOccurrence.get(value) == null ? 0 : 1);
          } 
          else 
          {
              oldFreq = valueToFreq.get(value);
              oldFreq = oldFreq == null ? 0 : oldFreq;
              oldOccurrence = freqToOccurrence.get(oldFreq);
              oldOccurrence = oldOccurrence == null ? 0 : oldOccurrence;

              if (key == 1) 
              {
                  newFreq = oldFreq + 1;
              } 
              else 
              {
                  newFreq = oldFreq - 1;
              }
              newOccurrence = freqToOccurrence.get(newFreq);
              newOccurrence = newOccurrence == null ? 0 : newOccurrence;

              if (newFreq < 1) 
              {
                  valueToFreq.remove(value);
              } 
              else 
              {
                  valueToFreq.put(value, newFreq);
              }

              if ((oldOccurrence - 1) < 1) 
              {
                  freqToOccurrence.remove(oldFreq);
              } 
              else 
              {
                  freqToOccurrence.put(oldFreq, oldOccurrence - 1);
              }
              
              freqToOccurrence.put(newFreq, newOccurrence + 1);
          }
      }
      
      return frequencies;
  }
   
   public int alternatingCharacters(String s) 
   {
      int rv = 0;
      for(int i = 0; i < s.length(); i++)
      {
         char val1 = s.charAt(i);
         
         for(int j = i+1; j < s.length(); j++)
         {
            if(val1 == s.charAt(j))
            {
               rv++;
               i++;
            }
            else
            {
               break;
            }
         }
      }

      return rv;
   }
   
   public int makeAnagram(String a, String b) 
   {
      int min_count = 0;
      
      int [] char_count = new int[26];
      
      for(int i = 0; i < a.length(); i++)
      {
         int idx = a.charAt(i)-'a';
         char_count[idx]++;
      }
      
      for(int i = 0; i < b.length(); i++)
      {
         int idx = b.charAt(i)-'a';
         char_count[idx]--;
      }
      
      for(int i = 0; i < char_count.length; i++)
      {
         min_count += Math.abs(char_count[i]);
      }
      
//      HashMap<Character, Integer> map_a = new HashMap<Character, Integer>();
//      HashMap<Character, Integer> map_b = new HashMap<Character, Integer>();
//      HashMap<Character, Integer> map_cmn = new HashMap<Character, Integer>();
//            
//      int len_a = a.length();
//      for(int i = 0; i < len_a; i++)
//      {
//         char tmp = a.charAt(i);
//         int tmp_val = (map_a.get(tmp) == null) ? 0 : map_a.get(tmp); 
//         map_a.put(tmp, tmp_val+1);
//      }
//      
//      int len_b = b.length();
//      for(int i = 0; i < len_b; i++)
//      {
//         char tmp = b.charAt(i);
//         int tmp_val = (map_b.get(tmp) == null) ? 0 : map_b.get(tmp);
//         map_b.put(tmp, tmp_val+1);
//      }
//
//      for(Map.Entry<Character, Integer> entry : map_a.entrySet())
//      {
//         char key_a = entry.getKey();
//         int val_a = entry.getValue();
//         
//         if(map_b.get(key_a) != null)
//         {
//            int val_b = map_b.get(key_a);
//            int sub = Math.abs(val_a-val_b);
//            if(sub == 0)
//            {
//               map_cmn.put(key_a, 1);
//            }
//            else
//            {
//               min_count += sub;
//               map_cmn.put(key_a, 1);
//            }
//         }
//      }
//      
//      for(Map.Entry<Character, Integer> entry : map_cmn.entrySet())
//      {
//         map_a.remove(entry.getKey());
//         map_b.remove(entry.getKey());
//      }
//      
//      for(Map.Entry<Character, Integer> entry : map_a.entrySet())
//      {
//         min_count += entry.getValue();
//      }
//      
//      for(Map.Entry<Character, Integer> entry : map_b.entrySet())
//      {
//         min_count += entry.getValue();
//      }

      return min_count;
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
      
      hr.sherlockAndAnagrams("abba");
      hr.sherlockAndAnagrams("ifailuhkqq");
      hr.sherlockAndAnagrams("kkkk");
//arrays interview prep kit.
      
      int [][] queries1 =
         {
               {1, 4},
               {1, 5},
               {1, 5},
               {1, 4},
               {2, 4},
               {2, 5},
               {2, 5},
               {2, 4}
         };
      
      int [][] queries2 = 
         {
               {1, 3},
               {2, 3},
               {3, 2},
               {1, 4},
               {1, 5},
               {1, 5},
               {1, 4},
               {3, 2},
               {2, 4},
               {3, 2}
         };
      
      int [][] queries3 = 
         {
               {1, 5},
               {1, 6},
               {3, 2},
               {1, 10},
               {1, 10},
               {1, 6},
               {2, 5},
               {3, 2}
         };
      
      
      
      //hr.freqQuery(queries1);
      hr.freqQuery(queries2);
      hr.freqQuery(queries3);
      
      hr.alternatingCharacters("AABAAB");
      hr.alternatingCharacters("AAAA");
      
      hr.makeAnagram("cde", "abc");
      hr.makeAnagram("fcrxzwscanmligyxyvym", "jxwtrhvujlmrpdoqbisbwhmgpmeoke");
   }
}

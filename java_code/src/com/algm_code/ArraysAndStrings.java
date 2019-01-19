package com.algm_code;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class ArraysAndStrings 
{
	public ArraysAndStrings()
	{
		
	}
	
	//1.1
	public boolean allUniqueChars2Loops(String input)
	{
		boolean rv = true;
		
		char inputs [] = input.toCharArray();
		
		/*
		 * O(n^2) solution runtime, with no extra space used
		 */
		outer : for(int i = 0; i < inputs.length; i++)
		{
			for(int j = i+1; j < inputs.length; j++)
			{
				if(inputs[i] == inputs[j])
				{
					rv = false;
					break outer;
				}
			}
		}
		
		return rv;
	}
	
	//1.1
	/*
	 * O(n) runtime, with extra space used O(n).
	 */
	public boolean allUniqueChars(String input)
	{
		boolean rv = true;
		
		//number of unique chars in a byte.
		boolean [] unique_char_status = new boolean [256];
		
		char inputs [] = input.toCharArray();
		
		for(int i = 0; i < inputs.length; i++)
		{
			//saves the char value as an array location
			int location = inputs[i];

			if(unique_char_status[location] == false)
			{
				unique_char_status[location] = true;				
			}
			else//already seen char...need to stop now..
			{
				rv = false;
				break;
			}
		}
		
		return rv;
	}
	
	//1.1, still need to fix this implementation
	/*
	 * O(n) with a simple tmp variable and doing xor..to help determine the
	 * uniqueness of data
	 */
	public boolean allUniqueCharsXor(String input)
	{
		boolean rv = true;
		
		char [] inputs = input.toCharArray();
		int xor_status = 0;
		
		for(int i = 0; i < inputs.length-1; i++)
		{
			//this will xor the two chars and save it tmp var
			xor_status = inputs[i] ^ inputs[i+1];
			
			if(xor_status == 0)
			{
				rv = false;
				break;
			}
		}
				
		return rv;
	}
	
	//1.2
	/*
	 * O(2n) which is O(n) runtime, O(n) space
	 */
	public void reverseStringWithSpace(char [] input)
	{		
		StringBuilder sb = new StringBuilder();
		
		//reverse string in tmp var
		for(int i = input.length-1; i >= 0; i--)
			sb.append(input[i]);
		
		//save string back into input array
		for(int i = 0; i < input.length; i++)
			input[i] = sb.charAt(i);				
	}
	
	//1.2
	/*
	 * O(n) runtime, not extra space needed
	 */	
	public void reverseString(char [] input)
	{		
		int start_index = 0;
		int end_index = input.length-1;
		
		while(start_index < end_index)
		{
			//swap the two locations
			char tmp = input[start_index];
			input[start_index++] = input[end_index];
			input[end_index--] = tmp;			
		}
	}
	
	//1.3
	/*
	 * O(n^2), runtime without any storage needed for this
	 */
	public void removeAllDuplicates(char [] input)
	{
		int first_null_location = -1;
		int first_valid_data = -1;
		char null_value = '\0';
		
		for(int i = 0; i < input.length; i++)
		{
			//check for null char..and skip this entry
			//for the outer loop
			if(input[i] == null_value)
				continue;
			
			for(int j = i+1; j < input.length; j++)
			{
				//check to see if this location is null..
				//if so then save that location..and continue
				if(input[j] == null_value)
				{
					if(first_null_location == -1)
						first_null_location = j;
				}
				else if(input[i] == input[j])//check to see if value is the same
				{
					input[j] = null_value;
					
					if(first_null_location == -1)
						first_null_location = j;
				}
				else//save first location of non dup value
				{
					if(first_valid_data == -1)
						first_valid_data = j;
				}
			}
			
			//this will swap if we have a value that can be moved over into a null spot
			//only if we have both a valid null spot and a valid value location
			if(first_null_location > 0 && first_valid_data > 0)
			{
				input[first_null_location] = input[first_valid_data];
				input[first_valid_data] = null_value;
				
				//reset both pointers to null
				first_valid_data = first_null_location = -1;
			}
		}
		
		//print out the value of the fixed string
		System.out.println(input);
	}
	
	//1.4
	/*
	 * 2 *O(n log n) ~ O(n log n), for runtime
	 */
	public void isAnagram1(String input1, String input2)
	{
		boolean rv = false;
		char [] input_tmp1 = input1.toCharArray();
		Arrays.sort(input_tmp1);
		String new_input1 = new String(input_tmp1);
		
		char [] input_tmp2 = input2.toCharArray();
		Arrays.sort(input_tmp2);
		String new_input2 = new String(input_tmp2);
		rv = new_input1.equals(new_input2);
		
		System.out.println(input1 + " and "+ input2 +" anagrams() are "+(rv ? "anagrams":"not anagrams"));			
	}
	
	//1.4
	/*
	 * O(n) runtime, O(n) space... 
	 */	
	public void isAnagram2(String input1, String input2)
	{
		boolean rv = true;
		
		//max size of all characters
		boolean [] bool_array = new boolean[256];
		char [] tmp1 = input1.toCharArray();
		char [] tmp2 = input2.toCharArray();
		
		for(int i = 0; i < tmp1.length; i++)
		{
			//save into the boolean array a true for the char as the index of the
			//value seen.
			bool_array[tmp1[i]] = true;
		}
		
		for(int i = 0; i < tmp2.length; i++)
		{
			//save into the boolean array a false for the char as the index of the
			//value seen.
			bool_array[tmp2[i]] = false;
		}
		
		//check to see if u find any true in the array..if so then
		//these strings aren't anagrams
		for(int i = 0; i < bool_array.length; i++)
		{
			if(bool_array[i] == true)
			{
				rv = false;
				break;
			}
		}
		
		System.out.println(input1 + " and "+ input2 +"  anagrams2() are "+(rv ? "anagrams":"not anagrams"));
	}
	
	//1.5
	public void replaceSpacesWithInput(String input, String replacingString)
	{
		char [] input_array = input.toCharArray();
		int num_spaces = 0;
		char [] special_string = replacingString.toCharArray();
		
		//count the number of spaces in the input array
		for(int i = 0; i < input_array.length; i++)
			if(input_array[i] == ' ')
				num_spaces++;
		
		if(num_spaces > 0)
		{
			int len_of_new_array = input_array.length +(num_spaces *2);
			char [] output_array = new char[len_of_new_array];
			int output_array_index = 0;
			
			for(int i = 0; i < input_array.length; i++)
			{
				if(input_array[i] == ' ')
				{
					//add extra string inside of space area
					output_array[output_array_index] = special_string[0]; 
					output_array[++output_array_index] = special_string[1];
					output_array[++output_array_index] = special_string[2];
					output_array_index++;//move over to next cell
				}
				else
				{
					//copy value into location of output array and update ptr
					output_array[output_array_index++] = input_array[i];
				}
			}
			
			System.out.print("input string = "+ input +" output string = ");
			for(int i = 0; i < output_array.length; i++)
				System.out.print(output_array[i]);
			System.out.println();
			
		}
	}
	
	//1.6
	/*
	 * this will rotate an NxN matrix 90 deg clockwise with an
	 * extra storage, runtime O(n^2), space O(n^2)
	 */
	public void rotate90ClockWise(int image[][])
	{
		int tmp [][];
		
		for(int i = 0; i < image.length; i++)
		{
			for(int j = 0; j < image.length; j++)
			{
				System.out.print(image[i][j]+" ");
			}
			System.out.println();
		}
		
		if(image.length == image[0].length)
		{			
			tmp = new int [image.length][image.length];
			
			int cols_index = image.length-1;
			
			//rotate the matrix by 90 deg into temp var
			for(int i = 0; i < image.length; i++)
			{
				for(int j = 0; j < image.length; j++)
				{
					tmp[j][cols_index] = image[i][j];
				}
				
				cols_index--;
			}
			
			//copy data back from tmp var to image var
			for(int i = 0; i < image.length; i++)
			{
				for(int j = 0; j < image.length; j++)
				{
					image[i][j] = tmp[i][j];
				}
			}
			
			for(int i = 0; i < image.length; i++)
			{
				for(int j = 0; j < image.length; j++)
				{
					System.out.print(image[i][j]+" ");
				}
				System.out.println();
			}
		}
	}
	
	//1.6
	public void RotateArrayBy90()
    {
        int[][] a = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        int n=3;
        
        for (int i = 0; i < n / 2; i++)
        {
            for (int j = i; j < (n - i - 1); j++)
            {
                int temp = a[i][j];
                a[i][j] = a[n - j - 1][ i];
                a[n - j - 1][i] = a[n - i - 1][ n - j - 1];
                a[n - i - 1][n - j - 1] = a[j][ n - i - 1];
                a[j][n - i - 1] = temp;
            }
        }
    }
	
	//1.7
	/*
	 * O(n^2) if there is no zeros in the matrix...
	 * O(n^3) if a zero is found in the matrix..
	 * O(m*n) space to hold the zeros from the original matrix
	 */
	public void setRowsColsZero(int input [][])
	{
		System.out.println("!!!!!!!!!!!!");
		
		//save the locations of where the  initial zeros
		//are before setting the rows and colums to zero.		
		int tmp [][] = new int[input.length][input[0].length];
		
		for(int i = 0; i < tmp.length; i++)
			for(int j = 0; j < tmp[0].length; j++)
				if(input[i][j] == 0)
					tmp[i][j] = 1;//this will set the location of the actual array to be a 1.
		
		for(int i = 0; i < input.length; i++)
		{
			for(int j = 0; j < input[0].length; j++)
			{
				//found a zero...need to set the rows and cols to all zeros
				//if(input[i][j] == 0)
				if(tmp[i][j] == 1)
				{
					//set the row entries to all zeros for that row
					for(int cols = 0; cols < input[0].length; cols++)
					{
						input[i][cols] = 0;
					}
					
					//set the col entries to all zeros for that col
					for(int rows = 0; rows < input.length; rows++)
					{
						input[rows][j] = 0;
					}
					
					//exit that row..since the row is all zero.
					break;
				}
			}
		}
		
		for(int i = 0; i < input.length; i++)
		{
			for(int j = 0; j < input[0].length; j++)
			{
				System.out.print(input[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public boolean isRotation(String input1, String input2)
	{
		boolean rv = false;
		
		//check to see if strings are the same length and that they are greater
		//than zero...if not then return false		
		if(input1.length() == input2.length() && input1.length() > 0)
		{
			//this places the potential input 2 string which may be rotated
			//back to back as one string
			String newInput2 = input2 + input2;
			
			//call the isSubstring function to check to see if input1 is substring of newInput2
			//if substring of newInput2, then input2 is a rotation of input not. otherwise its not.
			//instead of calling java substring..the indexOf will search the occurence of one string 
			//inside another string..and return -1 if not found or >=0 if found.
			int index = newInput2.indexOf(input1);
			
			//if index is > -1 then we found input 1 inside input 2.
			if(index > -1)
				rv = true;
		}
				
		System.out.println("input 2 = "+ input2+ " is "+(rv ? "a rotation": "not a rotation" ) + " of input1 = "+input1);
		return rv;
	}
	
	/*
	 * O(n) runtime..for removing a single char from an input char array.
	 */
	public void removeSingleInputFromString(char [] input, char removeChar)
	{
		int j = 0;
		
		System.out.println(input);
		
		for(int i = 0; i < input.length; i++)
		{
			if(input[i] == removeChar)
			{
				continue;
			}
			else
			{
				input[j++] = input[i];				
			}
		}
		
		//add the null char after all the string data.
		input[j] = '\0';		
		System.out.println(input);
	}
	
	public boolean validateEmailAddress(String emailAddress)
	{
		boolean rv = true;
		int size = emailAddress.length();
		int at_count = 0, dot_count = 0;
		
		//this for loop checks to see if the '@' or '.' 
		//and keeps a count for both chars
		for(int i = 0; i < size; i++)
		{
			if(emailAddress.charAt(i) == '@')
			{
				at_count++;
			}
			
			else if(emailAddress.charAt(i) == '.')
			{
				dot_count++;
			}
		}
		
		//if the at appears more than once..stop checking
		if(at_count > 1)
		{
			rv = false;
		}
		
		//if doesnt appear, the this is an issue. stop checking
		else if(dot_count < 1)
		{
			rv = false;
		}
		
		else
		{		
			int at_location = emailAddress.indexOf('@');
			int dot_location = emailAddress.lastIndexOf('.');
			
			//check if @ is in the begining of the string, if so stop
			if(at_location == 0 || dot_location == 0)
				rv = false;
			//check if '.' is at the end of the string, if so stop
			else if(at_location == size-1 || dot_location == size-1)
				rv = false;
			//check if the next char after @ is a dot, if so stop
			else if(emailAddress.charAt(at_location+1) == '.' )
				rv = false;
		}
		
		System.out.println("input str = "+ emailAddress+" is "+(rv ? "a valid email":"not a valid email"));
		
		return rv;
	}
	
	/*
	 * this is the non recursive palidrome algm. this runs in O(n) time.
	 */
	public boolean isPalindrome(String input)
	{
		boolean rv = true;
		char [] tmp_array = input.toCharArray();
		
		int i = 0;
		int j = tmp_array.length-1;
		
		//loop until i > j
		while(i <= j)
		{
			//check to see if the char at i is the same as
			//the char at j
			if(tmp_array[i] == tmp_array[j])
			{
				i++;
				j--;
			}
			else
			{
				//char value is diff...end the search..
				rv = false;
				break;
			}
		}
		
		System.out.println("this string = "+input+" is "+ (rv ? "a palindrome":"not a palindrome") );
		
		return rv;
	}
	
	/*
	 * this is the recursive version of the palindrome algm for testing purposes 
	 * this runs in O(n) time.
	 */
	public boolean isPalindromeRecusive(String input)
	{
		boolean rv = false;
		
		//if we have just one char, then its a valid palindrome
		if(input.length() == 1)
		{
			//is a palindrome
			rv = true;
		}
		//check if need to recurse
		else if (input.length() > 1)
		{
			//check to see if chars match
			if(input.charAt(0) == input.charAt(input.length()-1))
			{
				//check to see if len is 2, if so then valid palindrome.
				if(input.length() == 2)
					rv = true;
				//continue with recurrsion
				else
				{
					//get new substring from old input string, by reducing the edges of
					//the input string.
					String new_string = input.substring( 1,input.length() -1 );
					
					//update return val with status of recursive call.
					rv = isPalindromeRecusive(new_string);
				}
			}
		}
		
		return rv;
	}
	
	/*
	 * this will print a pyramid of numbers. runs in O(n) time.
	 * Doesnt need to save the pyramid since the output stream is
	 * the buffer used here.
	 */
	public void printNumsInPyramid(int num)
	{
		int new_line_count = 1;
		int index = 0;
		
		for(int i = 1; i <= num; i++)
		{
			System.out.print(i + " ");			
			index++;
			if(index == new_line_count)
			{
				System.out.println();
				index = 0;
				new_line_count++;				
			}			
		}
	}
	
	/*
	 * this will capitalize the string and not capitalize exclusion list.
	 * this is O(n) runtime. O(n) space. this is done with 2 buffers..
	 * one buffer for words, the other buffer final output..
	 */
	public void capitalizeString(String input)
	{
		char [] my_input = input.toCharArray();
		boolean firstChar = true;
		StringBuilder output_string = new StringBuilder();
		StringBuilder final_string = new StringBuilder();
		
		for(int i = 0; i < my_input.length; i++)
		{
			if(Character.isLetter(my_input[i]))
			{
				if(firstChar == true)
				{
					firstChar = false;
					output_string.append(Character.toUpperCase(my_input[i]));
				}
				else
				{
					output_string.append(my_input[i]);
				}
			}
			else
			{
				firstChar = true;
				
				exclusionList(output_string,final_string);
				
				final_string.append(my_input[i]);
			}
		}
		
		//final check just in case the final buff has data.
		exclusionList(output_string,final_string);
		
		System.out.println(final_string.toString());
	}
	
	private void exclusionList(StringBuilder output_string, StringBuilder final_string)
	{
		if(output_string.toString().length() > 0)
		{
			//check to see if in exclusion list. make string lower case.
			if(output_string.toString().equalsIgnoreCase("a") || 
			   output_string.toString().equalsIgnoreCase("is"))
			{
				final_string.append(output_string.toString().toLowerCase());
			}
			else
			{
				final_string.append(output_string.toString());	
			}

			output_string.delete(0, output_string.toString().length());
		}
	}
	
	public void findFirstNoneRepeatedLetter(char [] input)
	{
	   Map<Character, Integer> letter_cnt = new HashMap<Character, Integer>();
	   
	   char first_non_repeat = ' ';
	   
	   for(char in : input)
	   {
	      if(letter_cnt.get(in) == null)
	      {
	         letter_cnt.put(in, 1);
	      }
	      else
	      {
	         letter_cnt.put(in, letter_cnt.get(in)+1);
	      }
	   }
	   
	   for(char in : input)
	   {
	      if(letter_cnt.get(in) == 1)
	      {
	         first_non_repeat = in;
	         break;
	      }
	   }
	   
	   System.out.println(input);
	   System.out.println(first_non_repeat);
	}
	
	//find matching brackets in the string input.
	//returns true if matching brackets, false otherwise.
	public boolean matchingBrackets(String input)
	{
	   Stack<Character> stack = new Stack<Character>();
	   
	   char [] char_in = input.toCharArray();
	   
	   boolean rv = false;
	   boolean error_raised = false;
	   
	   for(char in : char_in)
	   {
	      if(in == '[')
	      {
	         stack.push(in);
	      }
	      else if(in == ']')
	      {
	         if(stack.size() == 0)
	         {
	            error_raised = true;
	            break;
	         }
	         else
	         {
	            stack.pop();   
	         }
	      }
	   }
	   
	   if(stack.size() == 0 && error_raised == false)
	   {
	      rv = true;
	   }
	   
	   return rv;
	}
	
	public void printIntersectionSortedArrays(int [] input1, int [] input2)
	{
	   int index1 = 0;
	   int index2 = 0;
	   
	   //check for index to not pass length of either array.
	   //which ever ends first stops the array.
	   while(index1 < input1.length && index2 < input2.length)
	   {
	      //if elem in first array is smaller then
	      //increase that index by 1
	      if(input1[index1] < input2[index2])
	      {
	         index1++;
	      }
	      //if elem in 2nd array is smaller than increase index 2 by 1
	      else if(input2[index2] < input1[index1])
	      {
	         index2++;
	      }
	      else
	      {
	         //if both elements are equal then pring the intersection/common elem
	         //and increase both indexes.
	         System.out.print(input1[index1]+",");
	         index1++;
	         index2++;
	      }
	   }
	}
	
	public void test(String s)
	{
	   System.out.println(s);
	}
	
	public void test(Object s)
	{
	   System.out.println(s);
	}
}
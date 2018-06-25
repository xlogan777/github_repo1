#!/usr/bin/python
'''
Created on Jun 1, 2018

@author: Menaj
'''
import numpy as np

def basic_func():
   print "basic func";
   
   my_list = [1,2,3,4];
   
   "convert to 1 row and 4 cols, a vector"
   arr = np.array(my_list);
   print arr;
   
   "created array with 2x3"
   print np.zeros((2, 3));
   
   "created array with 2x3"
   print np.ones((2, 3));
   
   "creates vector thats starts from 0 up to 6"
   print np.arange(7);
   
   "creates vector that starts from 2 until 10 and the types are floats in the vector"
   arr = np.arange(2, 10, dtype = np.float);
   print arr;
   print "Array Data Type :",arr.dtype;
   
   "creates vector bwt 1-4 with 6 being added evenly so 1.0, 1.6, 2.2..etc"
   print np.linspace(1., 4., 6);
   
   "create a matrix, 2x2"
   print np.matrix('1 2; 3 4');
   
   "conjugate transpose the matrix"
   mat = np.matrix('1 2; 3 4');
   print mat.H;

   "transpose of the matrix"
   mat = np.matrix('1 2; 3 4');
   print mat.T;
   
def cluster():
   print "cluster";
   
   

def main():
   "basic functions for scipy"
   basic_func();
   
   "cluster for scipy"
   cluster();

main();

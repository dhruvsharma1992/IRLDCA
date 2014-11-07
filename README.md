IRLDCA
======

The  source  code is an  implementation of  theInduced  Redundancy based Lossy data Compression Algorithm as described in the journal http://www.ijcaonline.org/archives/volume62/number16/10164-4928.

The  source-code has 2 major parts:

Compression.java :  where it provides a compress(input,factor)(where factor is a floating point number ideally between 0-1.0 which  allowes you to control the extent of compression since this is a  lossy  data compression) function  to  compress a given List of floating point numbers. It returns a generic List object.
The format in which it returns the list object is as:

[largest  Common Number , delta , maximum number after removing the largest Common Number , b , factor , number Of Binary Digits used to describe the stored int , input Length, compressed integer1, compressed integer2 ...] 


Decompression.java : which provides a decompress(input) function to decompress a List object returned fromthe compress function. It returns back the  List of floating pont numbers.


Input Format for  compression :  compress(List<Float> input, float  compress_factor) 

Output Format:  List<Float>


Input Format for decompression :  List<Float> returned from compression


Output Format : List<Float> output, a List  similar to input (not exactly same because of compression losses)


Future Plans:

To make it generic to include Integer, Decimal  type input.

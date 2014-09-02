IRLDCA
======

The  source  code is an  implementation of  theInduced  Redundancy based Lossy data Compression Algorithm as described in the journal http://www.ijcaonline.org/archives/volume62/number16/10164-4928.

The  source-code has 2 major parts:

Compression.java :  where it provides a compress(input,factor) function  to  compress a given List of floating point numbers. It returns a generic List object.
The format in which it returns the list object is as:

[largest  Common Number , delta , maximum number after removing the largest Common Number , b , factor , number Of Binary Digits used to describe the stored int , input Length, compressed integer1, compressed integer2 ...] 


Decompression.java : which provides a decompress(input) function to decompress a List object returned fromthe compress function. It returns back the  List of floating pont numbers.

Future Plans:

To make it generic to include Integer, Decimal  type input.

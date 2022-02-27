# LZ78 compression algorithm

A [LZ78](https://en.wikipedia.org/wiki/LZ77_and_LZ78) compression algorithm implementation using java. It was an assignment for the Compression and Information theory course in Cairo University Faculty of Computers and Artificial Intelligence.  
This implementation theoretically can compress text and binary files, although it is not designed to.

## How to use

To compress a file run the program with the following arguments:
```shell
java -jar lz78.jar -c <filename>
```

For decompression:
```shell
java -jar lz78.jar -d <filename>
```

## Example

Using 5 paragraphs of [Lorem ipsum](https://www.lipsum.com/feed/html):  
[Input file](examples/lipsum.txt) is 2,588 bytes  
[Compressed file](examples/lipsum.txt.lz78) is 2,212 bytes


## See also

- [LZ77 implementation](https://github.com/KareemMAX/lz77)
- [LZW implementation](https://github.com/KareemMAX/lzw)
- [Huffman coding implementation](https://github.com/mAshrafDawood/Huffman)
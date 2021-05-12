# Spell Checker

The file english.txt contains a list of English words, with one word on each line. the program look up words in this list to check whether they are correctly spelled. To make the list easy to use, words are stored in a HashSet for maximum efficiency.

you can supply a local text file to the program. all non-alphabetical characters are being ignored.

## generating suggestions

The program is doing all these 5 operations to generate a list of corrections:

1. Delete any one of the letters from the misspelled word.
2. Change any letter in the misspelled word to any other letter.
3. Insert any letter at any point in the misspelled word.
4. Swap any two neighboring characters in the misspelled word.
5. Insert a space at any point in the misspelled word (and check that both of the words that are produced are in the dictionary)

## full example

![](https://i.imgur.com/jqI8Jqf.gif)

## result example

![](https://i.imgur.com/ubcYQDF.png)
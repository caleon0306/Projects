This is a class assignment. The summary of this assignement was to create a phonebook that is sorted by a binary tree. This information stored is a name and a phone number. The information should be stored in a .txt file on save and exit. Instructions for the assignment are listed below.



Write a program that provides a way for you to store and retrieve telephone numbers. You will
use a binary search tree for this assignment.
Design a user interface that provides the following operations:
• Add a contact: Adds a person’s name and phone number to the phone book.
• Delete a contact: Deletes a given person’s name and phone number from the phone book,
given only the name.
• Search phone number: Locates a person’s phone number, given only the person’s name.
• Update phone number: Change a person’s phone number, given the person’s name and
new phone number.
• Display the phone book in sorted order: Display the phone book in sorted order (sorted
by name).
• Save and Exit: Exits the application, after first saving the phone book to the given text
file.
Each time after the result is displayed, ask user to Press Enter to Continue, and the menu will be
displayed again for the user to continue.
You can proceed as follows:
• Design and implement the class Person, which represents the name and phone number of
a person. You will store instances of this class in the phone book. The Person class needs
to extend KeyedItem<String> class (KeyedItem class is posted on Canvas).
• Design and implement the class PhoneBook, which represents the phone book. The class
should contain a binary search tree as a data field. This tree contains the people in the
book. You can use the code for binary search tree we discussed in class (also posted on
Canvas).
• Add methods that use a text file to save and store the tree.
• Design and implement the class PhoneBookManager, which includes the main method.
It provides the program’s menu and take actions when user selects an option. The user
should be allowed to perform multiple operations until they choose to exit.
The program should read data from a text file when it begins and save data into the file when the
user quits the program. Please use assg9_phoneBook.txt as the input file name.
The input file will have the following format: One person per line, with name and phone number
separated by a TAB.

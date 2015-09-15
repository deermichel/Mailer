## Mailer

### DISCLAIMER: This program ISNT made for spam purposes or other illegal activities! I am NOT responsible for any damages or legal issues due to this program! Use on your OWN risk!

Mailer is a Java based program to distribute an HTML mail (e.g. a newsletter) to a list of recipients. You can choose the distribution interval (in minutes) and the amount of mails sent per interval. The offset option let you skip some addresses that you dont always have to start from beginning of the list. Theoretically, attachments are possible but you have to figure it out on your own (some code snippets are commented out).

The source code is released under the GNU GPL v2 License. (See LICENSE for more information)

### Instructions

Import or clone the source into Eclipse (or your fav) and make sure you have at least Java JDK 8 installed. Compile and start the program. Fill out the server and mail header text fields and adjust the send options. Load a recipients list and the html message. Hit start and enjoy :D! If an error occurs or youre just curious, there is a log available.

#### Please note: The recipients list must be formatted like:
```
mail1@server.com   
mail2@server.com   
me@something.org
```

### Third-party

* [JavaMail API](https://java.net/projects/javamail/pages/Home)

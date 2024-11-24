#Gheorghe Marius Razvan 324CA

GlobalWaves-SpotifyClone

Implementation
In the page navigation system, we can track the current page where the user is first located upon adding them to the platform. A regular user has two main pages --HomePage-- and --LikedPage-- but can navigate to an artist/host’s page when they search and select them. I implemented this option through a "pageType" variable in the User class, which changes depending on the search. For artist/host navigation, I handle this change in the search method within the User class.

To display specific page data for each user, I created a class in the --pages-- package for each page type. 

For each new entity (artist/host), I created a class that implements the supported commands. These newly created entities are managed by the Admin, who can add or delete a user/artist/host through the --addUser-- and --deleteUser-- methods implemented in the Admin class.

An artist can add albums, events, or merchandise, all managed through operations in the Artist class. I used ChatGPT specifically for parsing date fields (parseDate, ValidateDate) when adding events. When an artist wants to add any of the above items, the program checks that they are not already present in the initial lists. Albums, events, or merchandise items can also be deleted, along with their content, provided they are not being accessed by other users. Specific methods are implemented for these commands.

The Host class allows a host to add podcasts and announcements and delete them, as long as they have not been accessed by other users.

The program can build collections, such as all users, implemented with {stream().map}. It can also create album rankings based on the number of likes.

Regarding design patterns, I used the Singleton pattern, implemented in LIBRARYINPUT and invoked in the action method.


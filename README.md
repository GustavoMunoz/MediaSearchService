# Media Search Service
### Gustavo Munoz

# Introduction
This project is an integration with external open APIs to retrieve, parse and display media content. There are two types of media content: movie and music media information. 

At the moment, there is one integration for each type of media content. In case of movies, the integration has been done by accessing IMDB APIs. For music, LastFm APIs has been used as external API. 

As soon the application receives media content from the external API, this information is processed into a common format. By doing this, add new APIs into the project is a light task. 

In case of movies, the consolidated format contains the following information: 
|Movie Media Content|
|----------|
| Title|
| Director|
| Year|

In case of music, the information parsed from the external APIs is the following:
|Music Media Content|
|----------|
| Album|
| Artist|
| Year|
 

# How to launch the application

Note that Java8 is required to launch the application.

## Step 1: Generate the artifact

From the root folder of the project, execute the following command:

```sh
$ ./gradlew build
```

## Step 2: Retrieve movie information by its title description

Once the artifact is generated, a movie search on IMDB by its title can be easily perform as follows:

```sh
$ java -jar -Dapi=imdb -Dmovie='Indiana Jones' build/libs/MediaSerchService-0.0.1-SNAPSHOT.jar
```

After executing this line, the movie media content information for this query is displayed in terminal as follows:

```
---------------------------------
 Result obtained from the query:

Title: 'Raiders of the Lost Ark' - Director: 'Steven Spielberg' - Year: '1981'
Title: 'Indiana Jones and the Last Crusade' - Director: 'Steven Spielberg' - Year: '1989'
Title: 'Indiana Jones and the Temple of Doom' - Director: 'Steven Spielberg' - Year: '1984'
Title: 'Indiana Jones and the Kingdom of the Crystal Skull' - Director: 'Steven Spielberg' - Year: '2008'
---------------------------------
```

## Step 3: Retrieve music information by its album description

In this case, to retrieve music details from _LastFm_ for songs with title _Here Comes The Sun_ just execute the following:

```sh
java -jar -Dapi=lastfm -Dmusic='Here Comes The Sun' build/libs/MediaSerchService-0.0.1-SNAPSHOT.jar
```

In this case, a piece of the output generated is displayed as follows:
```sh
......
Title: 'here comes the sun' - Artist: 'The Beatles' - Year: 'Unknown'
Title: 'Here Comes The Sun' - Artist: 'Tanel Padar & The Sun' - Year: 'Unknown'
Title: 'Here Comes The Sun' - Artist: 'Will K' - Year: 'Unknown'
Title: 'To Love Somebody/Here Comes the Sun [Bonus Tracks]' - Artist: 'Nina Simone' - Year: 'Unknown'
......
```

# react-json-searching

This project is simple Spring Boot/React App for searching json keys in json files.

  - Place json files you want to use in src/main/resources/json
  - Start server and navigate to localhost:8080
  - Select Which file to use
  - Type the key you want to find in the json
  - Autocomplete helps you see what keys are currently visible
  - Click enter to search and get the value.
  - Selected reverse toggle enables searching by value and returns all keys that match the search string (ignoring case)



### Stack

* [ReactJS] - Front End
* [Sping Boot] - Backend
* [Java 8] - Backend

### Installation

Running this requires [Maven](https://maven.apache.org/install.html) v3.1+ to run because of the node js plugin.

Run maven to install and start the server.

```sh
$ cd react-json-searching
$ mvn clean install
$ mvn spring-boot:run
```

Importing and running through an ide should also work (I use intellij)

then navigate to localhost:8080

### Todos

 - Tests
 - Better CSS
 - Json File upload (in progress)
    * Currently json files are loaded at server start
    *  Place any additional json files you want to use in src/main/resources/json
    *  They are stored in a Map, front end calls pass a parameter to specify the file to search

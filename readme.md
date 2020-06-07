# Memory Management Unit (MMU) - Server

## About

This project is a final project in the advanced Java course.
In the project, we implemented a memory management unit.
The memory management unit is a computer hardware unit having all memory references passed through itself, primarily performing the translation
of virtual memory addresses to physical addresses.

  
This project was created by Ayelet Avraham and Ron Peretz

## Architecture
The project has client and server side.
The client requests from the server GET/UPDATE/DELETE requests while the server has an algorithm
for making replacements (in that case LRU), and it will execute the replacements according to this algorithm.
In addition, the server computes action statistics and sends back to the client.

[<img src="/readme/architechture.JPG"
width="600"
    hspace="10" vspace="10">](/readme/architechture.JPG)


## Features

With this project we learned the following features:
- Java
- Client / Server
- OOP
- Unit tests - JUnit
- Swing
- MVC
- Observe pattern
- Singleton pattern
- OS

## Screenshots
[<img src="/readme/screenshot-1.JPG" align="left"
width="400"
    hspace="10" vspace="10">](/readme/screenshot-1.JPG)
[<img src="/readme/screenshot-2.JPG" align="center"
width="400"
    hspace="10" vspace="10">](/readme/screenshot-2.JPG)

## Client Project
[Client Project](https://github.com/PeretzRon/MMU---Memory-Management-Unit---Client)

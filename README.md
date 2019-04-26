# Calculate4U

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Assignment for network's class.
Communication protocol between server and client for math calculator system.

  - So you don't have a calculator at home? We've got you!
  - Ps. You need a computer with internet connection.
  - Magic!!

# New Features!
 - We don't have time to sleep.

### Contributors
- Nokhy
- Train
- Keyok

If for some weird reason you want to contact us, you can send an email to
ribtwentythree@gmail.com

### Tech
Technologies used:
* [Java] - Java language!
* [Eclipse] - awesome java IDE that basically makes the full code for you!

### Installation
You can use eclipse to import the project from a folder and run the main class.

### Client - Server commands:

| Client request | Server response | Absolut server response | Client receive cost in bytes | Server receive cost in bytes |
|------|------|------|------|------|
| is available| returns a confirmation message if the server is available.| yes or nothing.| 16| 16|
|solve [MATH OP]| solve the [MATH OP] and store the result in the server and return a token and a needed size to client get the message with result information.| [token];[necessary size to get token message]| 1k| 512k|
|get [TOKEN]|returns a message with informed length containing the solution.|[solution]|is volatile|1k|

### Simulation:

|Client Send|Client Receive|
|-|-|
|is available|yes|
|solve 1+2|token;2|
|get token|3|




### Development

Want to contribute? Great!
Do it.

#### License
Do whatever you want!

**Free Software, Hell Yeah!**

[Java]: <https://www.oracle.com/technetwork/java/javase/downloads/index.html>
[Eclipse]: <https://www.eclipse.org/>


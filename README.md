# Purpose
* Receiving opt codes from the Simple2Fa App  

# Requirements

* At least Java version 8

# How to start TCP Server

*  Download .jar File from release/
* Create config.properties in same folder as jar with content: 
```
passphrase=<MY_PASSPHRASE>
port=<TCP-Port> (default is 8123)
```
* Start server with: `java -jar 2faTCPServer-1.0.jar`

# Technical background
## Common
* Server receives encrypted json content on tcp port 8123
* Json content is decrypted with DES-algorithm and the defined passphrase in `config.properties`
  
## Json structure
```
{"identifier":"<IDENTIFIER>", "message":"<MESSAGE>" }
```

### Identifier "PASTE_TEXT"
1. Caches your current clipboard text
2. Copies the received message into system clipboard
3. Pastes clipboard on the screen
4. Restores your old clipboard text from cache

### Identifier "PASTE_TEXT_DELAY"
Same as "PASTE_TEXT", but with 1000 millis delay  

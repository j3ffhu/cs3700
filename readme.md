1. parse command line
2. if secure, create TSL socket. otherwise normal
3. constructure hello, post server to get id
4. pass guess to server. process retry message per marks
5. refine new guess, repeat step 4 till server return bye message

```
Connected to the TLS server on localhost:27994
Server response: {"type":"start","northeastern_username":"alex"}
Server response: {"type":"retry","word":"abcde","guesses":[{"word":"abcde","marks":[1,0,0,0,1]}]}
Server response: {"type":"retry","word":"fghij","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]}]}
Server response: {"type":"retry","word":"klmno","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]}]}
Server response: {"type":"retry","word":"pqrst","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]},{"word":"pqrst","marks":[0,0,0,1,1]}]}
Server response: {"type":"retry","word":"aaaaa","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]},{"word":"pqrst","marks":[0,0,0,1,1]},{"word":"aaaaa","marks":[1,1,1,2,1]}]}
Server response: {"type":"retry","word":"eeeae","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]},{"word":"pqrst","marks":[0,0,0,1,1]},{"word":"aaaaa","marks":[1,1,1,2,1]},{"word":"eeeae","marks":[1,1,2,2,1]}]}
Server response: {"type":"retry","word":"mmeam","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]},{"word":"pqrst","marks":[0,0,0,1,1]},{"word":"aaaaa","marks":[1,1,1,2,1]},{"word":"eeeae","marks":[1,1,2,2,1]},{"word":"mmeam","marks":[1,1,2,2,2]}]}
Server response: {"type":"retry","word":"sseam","guesses":[{"word":"abcde","marks":[1,0,0,0,1]},{"word":"fghij","marks":[0,0,0,0,0]},{"word":"klmno","marks":[0,0,1,0,0]},{"word":"pqrst","marks":[0,0,0,1,1]},{"word":"aaaaa","marks":[1,1,1,2,1]},{"word":"eeeae","marks":[1,1,2,2,1]},{"word":"mmeam","marks":[1,1,2,2,2]},{"word":"sseam","marks":[2,1,2,2,2]}]}
Server response: {"type":"bye","word":"steam","flag":"sndk83nb5ks"}
Guess it right: sndk83nb5ks
```

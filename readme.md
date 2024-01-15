1. parse command line
2. if secure, create TSL socket. otherwise normal
3. constructure hello with student name, post server to get id
4. load word list from url
5. pass guess from word list + id to server. filter word list per marks
6. repeat step 4 till server return bye message

```

# docker build -t cs3700:hw1 . 
# docker run -it --env ARGS="-p 27993 proj1.3700.network student_a"  --env ARGS_TLS="-p 27994 -s proj1.3700.network student_a"  --rm cs3700:hw1

Connected to the  server on proj1.3700.network:27993
Server response: {"id":"Ecydr/YfzwH9KMlxheLJ","type":"start"}
Server response: {"guesses":[{"word":"aahed","marks":[0,0,0,1,0]}],"id":"Ecydr/YfzwH9KMlxheLJ","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[0,0,0,1,0]},{"word":"abase","marks":[0,0,0,0,2]}],"id":"Ecydr/YfzwH9KMlxheLJ","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[0,0,0,1,0]},{"word":"abase","marks":[0,0,0,0,2]},{"word":"abate","marks":[0,0,0,0,2]}],"id":"Ecydr/YfzwH9KMlxheLJ","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[0,0,0,1,0]},{"word":"abase","marks":[0,0,0,0,2]},{"word":"abate","marks":[0,0,0,0,2]},{"word":"abaue","marks":[0,0,0,0,2]}],"id":"Ecydr/YfzwH9KMlxheLJ","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[0,0,0,1,0]},{"word":"abase","marks":[0,0,0,0,2]},{"word":"abate","marks":[0,0,0,0,2]},{"word":"abaue","marks":[0,0,0,0,2]},{"word":"abave","marks":[0,0,0,0,2]}],"id":"Ecydr/YfzwH9KMlxheLJ","type":"retry"}
  ...
Server response: {"flag":"323e1c940c6c046d63b9cf24d13fa61a1d479918935fa3b7533419f540827151","id":"Ecydr/YfzwH9KMlxheLJ","type":"bye"}
Guess it right: 323e1c940c6c046d63b9cf24d13fa61a1d479918935fa3b7533419f540827151
secret : pekoe
num of calls : 18

Connected to the TLS server on proj1.3700.network:27994
Server response: {"id":"OUKPovswi/d/TAP6GUoy","type":"start"}
Server response: {"guesses":[{"word":"aahed","marks":[2,1,1,0,0]}],"id":"OUKPovswi/d/TAP6GUoy","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[2,1,1,0,0]},{"word":"abash","marks":[2,0,2,1,2]}],"id":"OUKPovswi/d/TAP6GUoy","type":"retry"}
Server response: {"guesses":[{"word":"aahed","marks":[2,1,1,0,0]},{"word":"abash","marks":[2,0,2,1,2]},{"word":"asaph","marks":[2,2,2,0,2]}],"id":"OUKPovswi/d/TAP6GUoy","type":"retry"}
Server response: {"flag":"409d3367eff53c44f794d6b97339ade5cd7d31d3c0d01192ad8bb4323f9e4308","id":"OUKPovswi/d/TAP6GUoy","type":"bye"}
Guess it right: 409d3367eff53c44f794d6b97339ade5cd7d31d3c0d01192ad8bb4323f9e4308
secret : asarh
num of calls : 5
```

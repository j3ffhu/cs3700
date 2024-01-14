1. parse command line
2. if secure, create TSL socket. otherwise normal
3. constructure hello, post server to get id
4. pass guess to server. process retry message per marks
5. refine new guess, repeat step 4 till server return bye message

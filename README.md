# Simple multiuser GUI chat

* Each chat participant has his own nickname, which is indicated when joining the server.

* You can view the list of chat participants.

* You can send a message to the chat (to all participants).

* The client shows all the messages that were sent to the chat from the moment of connection + some number sent before; the list of messages is updated online. 

* The client displays events such as: the connection of a new person to the chat and the departure of a person from the chat.

* The server logs all events that occur on its side (enabled/disabled in the configuration file).

* The chat works via TCP/IP protocol.

The program is divided into two parts: server and client. To run it, you need to start the server separately and only then it will be possible to connect clients

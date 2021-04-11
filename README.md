# csci2020uGroupProject
Need to open client and server folders seperatly. They are two seperate intellij projects. 
Cannot just open csci2020uGroupProject. 

Rules of Blackjack:
5 hits = player wins.
Dealer and player have same total = tie and nobody wins or loses
etc...


Don't really have any server to client interaction. Would have to move the code the dealer uses to the 
server, then have the server and client interact based on hit, stand, ready. 
Ie: ready button press, send message "READY" to server. Server picks 2 cards, sends two cards to client. 
"4C,5C". Client reads this, removes two cards from list of avaliable cards and draws them. 
Button press "hit" doesn't have to do anything. 
Button press stand, send message "STAND, client's total count". Server tries to beat this.

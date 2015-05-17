# Socket
A chat room demo write with java and Android

**Module 1**: The Server of the demo is writed with java,will send the message got from a client to all the client include the sender.

And provide two Client module:

1. **Module 2**:One write with java only can run in the same computer with the server.can send a short message to Server,
2. **Module 3**:Another writed with Android ,can run in a Android Phone,Link to the Server with IP of the server,
          then send message to or get message from the Server.


default port=8080
default encoding=unicode


#Problem I meet：
  1. When make Module 1,if you close the PrintWriter got from a Client Socket,It may influence the Client Socket and give the problem like:
    * java.net.socketexception: socket closed
    
    I solve this problem when I delete one line of code:PrintWriter.close();
  
  2. When make Module 3,I forget to add uses-permission and even add the permission to a wrong place in chaos:
    * <uses-permission android:name="android.permission.INTERNET" />
  
  3. Module 3:In order to show the newest message got from the Server, I try EditView and TextView.
  Finally a ScorllView contain TextView solve this problem.While append message to the TextView,
  call this Function to set the ScorllView:
    * ScorllView.fullScroll(ScrollView.FOCUS_DOWN);
    
  4. Module 3:You need to create a new socket connection in a thread rather than UI process include View.OnClickListener
  
  5. Module 3:You need to update the UI with Handler rather Thread when receiving message from Server
  
  6. Module 3:Make the relation between threads clear,or you will get some bugs.
    
    
#Screenshot：
A Picture from Android Phone:
<img src="https://github.com/dyslove123/Socket/blob/master/Picture/Phone_Client.png" width="40%" heigh="40%">
    
A Picture from my computer for Server.jar:
<img src="https://github.com/dyslove123/Socket/blob/master/Picture/Server.jpg">

Here didn't show the Picture for Client.jar as a Easter egg,and Someone curious can take a try.

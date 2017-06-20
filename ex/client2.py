# Client for messaging service
import socket

c_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP connection
c_socket.connect(('localhost', 52000))
msg = input("Type the mode: ")

if msg == "SEND":
    while msg != "Q":
        c_socket.sendall(str.encode(msg))
        msg = input("Enter a message: ")
elif msg == "RECV":
    c_socket.sendall(str.encode(msg))
    data = c_socket.recv(1024)
    while data:
        if(data.decode("utf-8") is "LAST_MSG"): break
        print("Client received : " + data.decode("utf-8"))
        data = c_socket.recv(1024)
c_socket.close()

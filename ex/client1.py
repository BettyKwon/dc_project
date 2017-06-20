# Client for echo
import socket

c_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP connection
c_socket.connect(('localhost', 51000))

while True:
    msg = input("Enter a message: ")
    if msg == "Q":
        break
    c_socket.sendall(str.encode(msg))
    data = c_socket.recv(1024)
    print('Client received : ' + data.decode("utf-8"))

c_socket.close()

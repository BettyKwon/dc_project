# Server for messaging
import socket
from queue import *

# server setting
s_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP connection
server = 'localhost'
s_port = 52000
s_socket.bind((server, s_port))
s_socket.listen(1)

q = Queue(maxsize=0)
while True:

    conn, addr = s_socket.accept()
    data = conn.recv(1024)
    if data.decode("utf-8") == "SEND":
        while True:
            data = conn.recv(1024)
            if not data or data == "Q":
                break
            q.put(data)
        q.put(str.encode("LAST_MSG"))
    elif data.decode("utf-8") == "RECV":
        while not q.empty():
            d = q.get()
            conn.sendall(d)
            print("Server sent : " + d.decode("utf-8"))
    else:
        break
    conn.close()

conn.close()

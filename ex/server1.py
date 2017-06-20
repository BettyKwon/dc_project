# Server for echo
import socket

# server setting
s_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP connection
server = 'localhost'
s_port = 51000
s_socket.bind((server, s_port))
s_socket.listen(1)
conn, addr = s_socket.accept()

while True:
    data = conn.recv(1024)
    if not data:
        break
    conn.sendall(data)

conn.close()

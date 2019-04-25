import socket

def main():
	PORT = 54321
	HOST = 'localhost'
	conexao = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	conexao.connect((HOST, PORT))
	try:
		conexao.send("is available".encode())
	except ConnectionRefusedError:
		print("Not avaliable")
		return None
	resposta = conexao.recv(16).decode()
	print("Server on: " + resposta)
	request = "solve " + input("Type operation: ")
	conexao.send(request.encode())
	resposta = conexao.recv(1024).decode()
	print('response: ' + resposta)
	resposta = resposta.split(';')
	token = resposta[0]
	size = int(resposta[1])
	print("Token response: " + token)
	request = 'get ' + token
	conexao.send(request.encode())
	solucao = conexao.recv(size).decode()
	print('Solution = ' + solucao)
	conexao.close()
main()

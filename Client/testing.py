from tkinter import *
import socket

#

class Client:
	def __init__(self, window):
		PORT = 54321
		HOST = '172.18.16.32'
	
		self.jn = window
		
		self.label = Label(self.jn, text = 'Calculator4U')
		self.label.place(x=200, y=10, w=100, h=50)
		
		self.label1 = Label(self.jn, text = 'Solution')
		self.label1.place(x=10, y=50, w=100, h=30)
		
		self.label2 = Label(self.jn, text = 'Type operation')
		self.label2.place(x=10, y=90, w=100, h=30)
		
		self.entrySol = Entry(self.jn, relief = FLAT)
		self.entrySol.place(x = 125, y = 50, w = 300, h=30)
		
		self.entry = Entry(self.jn)
		self.entry.place(x = 125, y = 90, w = 300, h=30)
		
		button = Button(self.jn, text = 'Solve', command = self.callback)
		button.place(x = 200, y=150, w= 100,h = 30)
		
		self.conexao = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.conexao.connect((HOST, PORT))
		
		self.conection()
		
	def callback(self):
		request = self.entry.get()
		try:
			restrim = ""
			for i in request:
				if i != ' ':
					restrim += i
			request = restrim
			restrim = restrim.lower()
			if restrim.startswith('close'):
				exit(0)
			request = "solve " + request
			self.conexao.send(request.encode())
			resposta = self.conexao.recv(1024).decode()
			resposta = resposta.split(';')
			token = resposta[0]
			size = int(resposta[1])
			print("token: " + token)
			request = 'get ' + token
			self.conexao.send(request.encode())
			solucao = self.conexao.recv(size).decode()
			print('Solution = ' + solucao)
		except:
			solucao = 'Server socket error'
		self.entrySol.delete(0, END)
		self.entrySol.insert(0, solucao)
		
	def conection(self):
		try:
			self.conexao.send("is available".encode())
		except ConnectionRefusedError:
			print("Not avaliable")
			exit(0)
		resposta = self.conexao.recv(16).decode()
		print("Server on: " + resposta)
		

def main():
	jn = Tk()
	jn.resizable(False, False)
	jn.geometry('500x200')
	jn.title('Calculate4U')
	cliente = Client(jn)
	jn.mainloop()
	cliente.conexao.close()
main()

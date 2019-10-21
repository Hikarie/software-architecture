from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
from tkinter import *
import socket 
import numpy as np



class Gui:
    def __init__(self):
        self.s = socket.socket()
        # 连接EC2服务器
        self.host = "ec2-3-83-136-110.compute-1.amazonaws.com"
        self.port = 12345
        self.f = Figure(figsize=(6,4),dpi=100)

    def ui(self):
        root = Tk()
        root.title("浦发银行数据库查询系统")
        root.maxsize(600,500)
        root.minsize(600,500)
        btn_1 = Button(root,text="查差价",command=self.query_1)
        btn_1.place(x=80,y=25)
        btn_2 = Button(root,text="查转手率",command=self.query_2)
        btn_2.place(x=250,y=25)
        btn_3 = Button(root,text="查涨幅",command=self.query_3)
        btn_3.place(x=420,y=25)
        self.canvas = FigureCanvasTkAgg(self.f, master=root)
        self.canvas.get_tk_widget().place(x=15,y=85)
        self.canvas.draw()

        root.mainloop()
        

    def query_1(self):
        self.s.send("1".encode())
        data = self.s.recv(10240)
        data = data.decode()
        data = data.split(',')
        l = []
        for i in range(len(data)-2):
            l.append(float(data[i]))
        y = np.array(l)
        a = self.f.add_subplot(111)
        x = np.arange(1999, 2016, 1)
        a.bar(x, y)
        a.set_ylim(-0.06, 0.06, 0.05)
        a.set_xlim(1999, 2016, 1)
        self.canvas.draw()
        # self.s.close()

    def query_2(self):
        self.s.send("2".encode())
        data = self.s.recv(10240)
        data = data.decode()
        data = data.split(',')
        l = []
        for i in range(len(data)-2):
            l.append(float(data[i]))
        y = np.array(l)
        a = self.f.add_subplot(111)
        x = np.arange(1999, 2016, 1)
        print(y)
        a = self.f.add_subplot(111)
        a.bar(x, y)
        a.set_ylim(-0.06, 0.06, 0.05)
        a.set_xlim(1998, 2016, 1)
        self.canvas.draw()
        # self.s.close()

    def query_3(self):
        self.s.send("3".encode())
        data = self.s.recv(10240)
        data = data.decode()
        data = data.split(',')
        l = []
        for i in range(len(data)-2):
            l.append(float(data[i]))
        y = np.array(l)
        a = self.f.add_subplot(111)
        x = np.arange(1999, 2016, 1)
        print(y)
        a = self.f.add_subplot(111)
        a.plot(x, y)
        a.set_ylim(-0.06, 0.06, 0.05)
        a.set_xlim(1998, 2016, 1)
        self.canvas.draw()
        # self.s.close()        

gui = Gui()
gui.ui()

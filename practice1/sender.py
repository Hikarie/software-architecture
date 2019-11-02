import boto3
from tkinter import *
from tkinter import messagebox
import time

class sender:
    def __init__(self):
        self.sqs = boto3.resource('sqs')
        self.queue_name = "test"
        self.message = ""

    def create(self, queue_name):
        self.queue_name = queue_name
        queue = self.sqs.create_queue(QueueName=queue_name, Attributes={'DelaySeconds':'5'})    
        return (queue.url)

    def send(self):
        self.message = var_message.get()
        queue = self.sqs.get_queue_by_name(QueueName=self.queue_name)
        sender = queue.send_message(MessageBody=self.message)
        var_message.set("")
        self.log(sender)

    def log(self, sender):
        id = sender.get('MessageId')
        text_message.insert(INSERT,"id:"+id+"\n" )
        text_message.insert(INSERT,"body:"+self.message+"\n")
        text_message.insert(INSERT,"station:succeed\n" )
        text_message.insert(END,"time:"+time.strftime("%m-%d %H:%M:%S", time.localtime())+"\n")
    
    def ui(self):
        root = Tk()
        root.title("sender")
        root.maxsize(350,300)
        root.minsize(350,300)
        global var_message
        var_message = StringVar()
        Label(root, text="请输入发送的消息：").place(x=20, y=20)
        entry_message = Entry(root, textvariable=var_message, width=35)
        entry_message.place(x=20, y=50)
        btn_send = Button(root, text="发送", command=self.send)
        btn_send.place(x=280, y=48)
        global text_message
        text_message = Text(root, width=40, height=15)
        text_message.place(x=20,y=85)
        root.mainloop()

snd = sender()
snd.ui()

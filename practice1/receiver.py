import boto3
from tkinter import *
from tkinter import messagebox
import time

class receiver:
    def __init__(self):
        self.sqs = boto3.client('sqs')
        self.queue_name = "test"
        self.url = ""

    def receive(self):
        url = self.sqs.get_queue_url(QueueName=self.queue_name)
        self.url = url['QueueUrl']
        try:
            messages = self.sqs.receive_message(
                QueueUrl=self.url,
                MaxNumberOfMessages=self.num_msg,
                WaitTimeSeconds=self.wait_time,
                VisibilityTimeout=self.visible_timeout
            )
            message = messages['Messages']
        except KeyError:
            messagebox.showinfo('提示','没有还未接收的消息！')
            return
        body = message[-1]['Body']
        var_message.set(body)
        handle = message[-1]['ReceiptHandle']
        self.log(message)
        self.delete(handle)

    def delete(self, handle):
        self.sqs.delete_message(QueueUrl=self.url,
                              ReceiptHandle=handle)

    def log(self, message):
        text_message.insert(INSERT,"id:"+message[-1]['MessageId']+"\n" )
        text_message.insert(INSERT,"body:"+message[-1]['Body']+"\n")
        text_message.insert(INSERT,"station:succeed\n" )
        text_message.insert(END,"time:"+time.strftime("%m-%d %H:%M:%S", time.localtime())+"\n")

    def ui(self):
        root = Tk()
        root.title("receive")
        root.maxsize(350,300)
        root.minsize(350,300)
        Label(root, text="接收到的消息：").place(x=20, y=20)
        global var_message
        var_message = StringVar()
        entry_message = Entry(root, textvariable=var_message, width=35)
        entry_message.place(x=20, y=50)
        btn_send = Button(root, text="刷新", command=self.receive)
        btn_send.place(x=280, y=48)
        global text_message
        text_message = Text(root, width=40, height=15)
        text_message.place(x=20,y=85)
        self.receive()
        root.mainloop()

rcv = receiver()
rcv.ui()
# web应用服务器
import socket

class Bus:
    def __init__(self):
        # self.mssql = dao.Mssql('database-1.csnqzpism18o.us-east-1.rds.amazonaws.com,1433', 'admin', 'yojiro8575', 'market') 
        self.server = socket.socket()
        self.client = socket.socket()
        self.handle()
    
    def handle(self):
        self.server.bind((socket.gethostname(), 12345))
        self.server.listen(5)
        # self.client.connect(("ec2-35-170-200-212.compute-1.amazonaws.com", 12345))
        self.client.connect(("localhost", 12345))
        
        while True:
            conn,addr = self.server.accept()
            print(conn, addr)
            while True:
                num = conn.recv(1024).decode()
                self.client.send(num.encode())
                result = self.client.recv(10240)
                print("recieve",num)
                print(result)
                result = result.decode()
                result = result.strip('(').strip(')').split(')(')
                if num is "1":
                    print("查询差价")
                    data = self.handle_diff(result)
                    conn.send(data.encode())
                    break
                elif num is "2":
                    print("查询转手率")
                    data = self.handle_rate(result)
                    conn.send(data.encode())
                    break
                elif num is "3":
                    print("查询涨跌")
                    data = self.handle_up_down(result)
                    conn.send(data.encode())
                    break
            conn.close()

    # 处理数据
    def handle_diff(self,result):
        data = ""
        print(result)
        for i in range(len(result)):
            temp = result[i].split(',')
            print(temp)
            out =  str(float(temp[0]) - float(temp[1]))
            data += out
            if i < len(result):
                data += ','
        return data

    def handle_rate(self,result):
        data = ""
        for i in range(len(result)):
            data += str(result[i])
            if i < len(result):
                data += ','
        return data

    def handle_up_down(self,result):
        data = ""
        for i in range(len(result)):
            temp = result[i].split(',')
            data += str(temp[len(temp)-1])
            if i < len(result):
                data += ','
        return data
    
bus = Bus()
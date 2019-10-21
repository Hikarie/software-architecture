# 数据库服务器
import dbConnection as db
import socket

class UserDao:
    def __init__(self):
        self.server = socket.socket()
        # self.conn = db.dbConnection("localhost", "sa", "8575", "market")
        self.conn = db.dbConnection('database-1.csnqzpism18o.us-east-1.rds.amazonaws.com:1433', 'admin', '***', 'market')
        self.receive()

    def receive(self):
        # self.server.bind(("ec2-35-170-200-212.compute-1.amazonaws.com", 12345))
        self.server.bind(("localhost",12345))
        self.server.listen(5)
        while True:
            conn, addr = self.server.accept()
            while True:
                num = conn.recv(1024).decode()
                result = []
                if num is "1":
                    result = self.get_price_diff()
                    break
                elif num is "2":
                    result = self.get_rate()
                    break
                elif num is "3":
                    result = self.get_up_down()
                    break
            data = self.send_format(result)
            print(data)
            conn.send(data.encode())    # 发送查询结果
            conn.close()

    # 计算开盘价和收盘价差值
    def get_price_diff(self):
        result = self.conn.ExecQuery('select avg(开盘价), avg(收盘价) from pufa group by(datepart(yy,日期))')
        return result

    # 查询转手率
    def get_rate(self):
        result = self.conn.ExecQuery('select avg(换手率) from pufa group by(datepart(yy,日期))')
        return result

    # 查询涨跌和涨跌幅
    def get_up_down(self):
        result = self.conn.ExecQuery('select avg(涨跌幅) from pufa group by(datepart(yy,日期))')
        return result

    def send_format(self, result):
        data = ""
        for i in range(len(result)):
            temp = "("
            for j in range(len(result[i])):
                temp += str(result[i][j])
                if j < len(result[i])-1 : 
                    temp += ","
            temp += ")"
            data += temp
        return data 

dao = UserDao()
# result = dao.get_price_diff()
# data = dao.send_format(result)
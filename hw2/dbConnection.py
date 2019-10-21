import pymssql
# import boto3

class dbConnection:
    def __init__(self, host, user, pwd, db):
        self.host = host
        self.user = user
        self.pwd = pwd
        self.db = db
        
    def connect(self):
        self.conn = pymssql.connect(host=self.host, user=self.user, password=self.pwd, database=self.db)
        cursor = self.conn.cursor()
        if not cursor:
            print('数据库连接失败')
        else:
            return cursor

    def ExecQuery(self, sql):
        cursor = self.connect()
        cursor.execute(sql)
        result = cursor.fetchall()
        self.conn.close()
        return result
    
    def ExecOperate(self,sql):
        cursor = self.connect()
        cursor.execute(sql)
        self.conn.commit()
        self.conn.close()
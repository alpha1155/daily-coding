# from MyList import MyList
# myList1 = MyList([1, 2, 3, 4, 5])
# myList1.add(6)
# print(myList1)

# myList2 = list([1, 2, 3, 4, 5])
# myList2.add(6)
# print(myList2)

# from User import User

# u = User(id=12345, name="Michael", email="test@orm.org", password="my-pwd")
# u.save()


#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def consumer():
    r = ""
    while True:
        n = yield r
        if not n:
            return
        print("[CONSUMER] Consuming %s..." % n)
        r = "200 OK"


def produce(c):
    c.send(None)
    n = 0
    while n < 5:
        n = n + 1
        print("[PRODUCER] Producing %s..." % n)
        r = c.send(n)
        print("[PRODUCER] Consumer return: %s" % r)
    c.close()


c = consumer()
produce(c)

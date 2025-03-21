from Model import Model
from StringField import StringField
from IntegerField import IntegerField


class User(Model):
    id = IntegerField("id")
    name = StringField("name")
    email = StringField("email")
    password = StringField("password")

from django.db import models
import datetime


class User(models.Model):
    name = models.CharField(max_length=32)
    password_hash = models.CharField(max_length=128)

    def __str__(self):
        return self.name


class Session(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    partial_times = models.CharField(max_length=1024)
    total_time = models.IntegerField














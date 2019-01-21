from django.db import models

# Create your models here.

class Product(models.Model):
    name = models.CharField(max_length=255)
    category = models.CharField(max_length=255)

    def __str__(self):
        return self.name

class Claim(models.Model):
    n_claim = models.IntegerField()
    category = models.CharField(max_length=255)
    reason = models.CharField(max_length=255)
    date = models.DateTimeField(auto_now_add=True)
    state = models.CharField(max_length=255)

    def __str__(self):
        return self.reason    
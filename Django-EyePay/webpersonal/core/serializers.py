from rest_framework import serializers
from .models import Product
from .models import Claim

class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('name', 'category')

class ClaimSerializer(serializers.ModelSerializer):
    class Meta:
        model = Claim
        fields = ('n_claim', 'category', 'reason', 'date', 'state')
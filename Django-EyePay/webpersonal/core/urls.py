from django.urls import path
from core import views


urlpatterns = [
    path('products/', views.product_list),
    path('products/<int:pk>/', views.product_detail),
    path('claims/', views.claim_list)
]
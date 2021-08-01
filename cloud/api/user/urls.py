from django.urls import path
from rest_framework_simplejwt.views import TokenRefreshView
from user import views

app_name = 'users'

urlpatterns = [
    path('auth/register/', views.RegisterAPIView.as_view()),
    path('auth/test/', views.AuthenticatedAPIView.as_view()),
    path('auth/login/', views.LoginAPIView.as_view()),
    path('auth/refresh/', TokenRefreshView.as_view()),
]

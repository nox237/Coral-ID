from rest_framework import status
from rest_framework import generics
from rest_framework.response import Response
from rest_framework import permissions
from user import serializers
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from rest_framework_simplejwt.tokens import AccessToken
from rest_framework_simplejwt import authentication
from rest_framework_simplejwt.views import TokenObtainPairView
from user.models import UserProfile
from user import utils


class RegisterAPIView(generics.GenericAPIView):
    """Register API View using JWT token"""
    serializer_class = serializers.RegisterSerializer

    def post(self, request):
        serializer = self.get_serializer(data=request.data)
        if (serializer.is_valid()):
            user = serializer.save()
            tokenr = TokenObtainPairSerializer().get_token(request.user)
            tokena = AccessToken().for_user(request.user)
            tokenr['user_id'] = str(user.id)
            tokena['user_id'] = str(user.id)
            return Response({
                "status": 201, "message": "successful register",
                "refresh_token": str(tokenr), "access_token": str(tokena)
            }, status=status.HTTP_201_CREATED)

        name_errors = []
        email_errors = []
        password_errors = []

        if serializer.data['name'].strip() == "":
            name_errors.append("Please fill the name")

        if not utils.check_email(serializer.data['email']):
            email_errors.append('Email not valid')

        if UserProfile.objects.filter(email=serializer.data['email']).exists():
            email_errors.append('Email already used for register')

        if len(serializer.data['password']) <= 6:
            password_errors.append('Password must be > 6 characters')

        return Response({
            'status': 401, "name": name_errors,
            "email": email_errors,
            "password": password_errors,
        }, status=status.HTTP_401_UNAUTHORIZED)


class LoginAPIView(TokenObtainPairView):
    """Login API using JWT token"""
    serializer_class = serializers.CustomTokenObtainPairSerializer

class AuthenticatedAPIView(generics.GenericAPIView):
    """Authenticated API View"""
    authentication_classes = (authentication.JWTAuthentication,)
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request):
        """GET Request for authenticated user"""
        return Response({'message': 'Successfully login'})
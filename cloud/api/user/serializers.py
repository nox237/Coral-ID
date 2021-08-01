from rest_framework import serializers
from user.models import UserProfile
from django.contrib.auth import get_user_model
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from django.contrib.auth import authenticate
from rest_framework_simplejwt.settings import api_settings
from rest_framework.exceptions import PermissionDenied
from rest_framework import status
from user import utils


class CustomException(PermissionDenied):
    """Custom Exception that used on CustomTokenObtainPairSerializer"""
    status_code = status.HTTP_401_UNAUTHORIZED

    def __init__(self, detail, status_code=None):
        self.detail = detail
        if status_code is not None:
            self.status_code = status_code


class RegisterSerializer(serializers.ModelSerializer):
    """Serializer for Register api"""

    email = serializers.EmailField(max_length=50, min_length=6)
    password = serializers.CharField(min_length=7, write_only=True)

    class Meta:
        model = get_user_model()
        fields = ('name', 'email', 'password',)

    def validate(self, args):
        email = args.get('email', None)
        if UserProfile.objects.filter(email=email).exists():
            raise serializers.ValidationError({
                'email': ('email already exists')})
        return super().validate(args)

    def create(self, validated_data):
        return get_user_model().objects.create_user(**validated_data)


class CustomTokenObtainPairSerializer(TokenObtainPairSerializer):
    """Custom token obtain pair serializer"""

    def validate(self, attrs):
        authenticate_kwargs = {
            self.username_field: attrs[self.username_field],
            'password': attrs['password'],
        }
        try:
            authenticate_kwargs['request'] = self.context['request']
        except KeyError:
            pass

        self.user = authenticate(**authenticate_kwargs)

        if not api_settings.USER_AUTHENTICATION_RULE(self.user):
            email_errors = []
            password_errors = []

            if not utils.check_email(attrs['email']):
                email_errors.append("Email not valid")
            if len(attrs['password']) <= 6:
                password_errors.append("Password must be > 6 characters")

            raise CustomException(detail={'status': 401,
                                          "email": email_errors,
                                          "password": password_errors},
                                  status_code=status.HTTP_401_UNAUTHORIZED
                                  )

        data = {}
        refresh = self.get_token(self.user)
        data['refresh_token'] = str(refresh)
        data['access_token'] = str(refresh.access_token)
        data['status'] = 201
        data['message'] = "Successful Login"
        return data

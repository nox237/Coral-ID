from core.models import Forum
from rest_framework import viewsets, mixins
from rest_framework_simplejwt import authentication
from rest_framework import permissions
from forum import serializers
from rest_framework.response import Response
from rest_framework import status

class ForumAPIViewSet(viewsets.GenericViewSet,
                   mixins.ListModelMixin,
                   mixins.CreateModelMixin):
    """Member Course API View"""

    authentication_classes = (authentication.JWTAuthentication,)
    permission_classes = (permissions.IsAuthenticated,)
    serializer_class = serializers.ForumAPISerializers
    queryset = Forum.objects.all()

    def list(self,request):
        queryset = Forum.objects.all()
        data = self.serializer_class(queryset, context={'id': request.user.id}, many=True).data
        return Response(data, status.HTTP_200_OK)

    def create(self, request):
        Forum.objects.create(user_id=str(request.user.id), messages=request.data.get('messages'),date=request.data.get('date'))
        return Response({'message': 'success post'}, status.HTTP_200_OK)
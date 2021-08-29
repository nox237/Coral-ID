from rest_framework import serializers
from core.models import Forum
from user.models import UserProfile

class ForumAPISerializers(serializers.ModelSerializer):
    """Serializer for ForumAPIView"""
    name = serializers.SerializerMethodField()

    class Meta:
        model = Forum
        fields = ('id','name', 'messages', 'date')

    def get_name(self, obj):
        data = UserProfile.objects.filter(id=obj.user_id).first()
        return data.name
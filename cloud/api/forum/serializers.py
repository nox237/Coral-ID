from rest_framework import serializers
from core.models import Forum
from user.models import UserProfile

class ForumAPISerializers(serializers.ModelSerializer):
    """Serializer for ForumAPIView"""
    name = serializers.SerializerMethodField()

    class Meta:
        model = Forum
        fields = ('name', 'messages', 'date')

    def get_name(self, obj):
        data = UserProfile.objects.filter(id=self.context.get("id")).first()
        return data.name
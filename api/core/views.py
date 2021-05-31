from rest_framework import views, status, viewsets, mixins
from rest_framework.response import Response
from django.core.files.storage import default_storage
from django.conf import settings
import uuid
import subprocess

from core import serializers, predictions
from core.models import Coral, Species


class CallModel(views.APIView):
    """Call the machine learning model"""
    serializer_class = serializers.ImageSerializer

    def post(self, request, pk=None):
        """Test for returning json request"""
        serializer = serializers.ImageSerializer(data=request.FILES)

        if serializer.is_valid():
            if len(request.FILES['image'].name.split('.')) != 2:
                return Response({'message': 'Invalid File Extension'}, status=status.HTTP_400_BAD_REQUEST)

            extension = request.FILES['image'].name.split('.')[1]
            unique_filename = str(uuid.uuid4()) + f'.{extension}'

            file_name = default_storage.save(
                f"./{unique_filename}", request.FILES['image'])
            file_url = default_storage.url(file_name)
            img_tensor = predictions.load_image_from_path(file_url)
            prediction = settings.H5_MODEL.predict(img_tensor)[0]
            default_storage.delete(file_name)

            payload = {'image': request.FILES['image'].name, 'prediction': {
                'dogs': prediction, 'cats': 1 - prediction}}
            return Response(payload, status=status.HTTP_200_OK)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class CoralViewSet(viewsets.GenericViewSet,
                   mixins.ListModelMixin,
                   mixins.RetrieveModelMixin):
    # class CoralViewSet(viewsets.ModelViewSet):
    """List and Retrieve Coral Model from database"""
    queryset = Coral.objects.all()
    serializer_class = serializers.CoralSerializer


class SpeciesViewSet(viewsets.GenericViewSet,
                     mixins.ListModelMixin,
                     mixins.RetrieveModelMixin):
    # class SpeciesViewSet(viewsets.ModelViewSet):
    """List and Retrieve Coral Species from database"""
    queryset = Species.objects.all()
    serializer_class = serializers.SpeciesSerializer

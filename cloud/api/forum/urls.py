from forum import views
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register('forum', views.ForumAPIViewSet, basename="forum")

urlpatterns = []
urlpatterns += router.urls
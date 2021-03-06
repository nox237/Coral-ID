"""
Django settings for app project.

Generated by 'django-admin startproject' using Django 2.2.22.

For more information on this file, see
https://docs.djangoproject.com/en/2.2/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/2.2/ref/settings/
"""

import os
from tensorflow.keras.models import load_model
import rest_framework

if os.getenv("GCP_PRODUCTION"):
    from google.cloud import storage


def download_file(bucketName, bucketFolder, localFolder, fileName):
    """Download file from GCP bucket."""
    blob = bucket.blob(fileName)
    blob.download_to_filename(localFolder + fileName)


# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/2.2/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = "APP-SECRET-KEY"

# SECURITY WARNING: don't run with debug turned on in production!
if os.getenv("PROD_SERVER"):
    DEBUG = False
    REST_FRAMEWORK = {
        'DEFAULT_RENDERER_CLASSES': (
            'rest_framework.renderers.JSONRenderer',
        )
    }
else:
    DEBUG = True

ALLOWED_HOSTS = ["0.0.0.0",'*']

# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'rest_framework',
    'core',
    'user',
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'app.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'app.wsgi.application'

# Database
# https://docs.djangoproject.com/en/2.2/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}

if os.getenv("GCP_PRODUCTION_DATABASE"):
    if os.getenv("GCP_PRODUCTION_DATABASE") == "mysql":
        DATABASES = {
            'default': {
                'ENGINE': 'django.db.backends.mysql',
                'HOST': 'YOUR-CONNECTION-NAME',
                'USER': 'YOUR-USERNAME',
                'PASSWORD': 'YOUR-PASSWORD',
                'NAME': 'YOUR-DATABASE',
            }
        }
    else:
        bucketName = 'coral-dataset'
        bucketFolder = './'
        storage_client = storage.Client()
        bucket = storage_client.get_bucket(bucketName)
        download_file(bucketName, bucketFolder, "./", "db.sqlite3")

# Password validation
# https://docs.djangoproject.com/en/2.2/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/2.2/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = False

USE_L10N = False

USE_TZ = False


# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/2.2/howto/static-files/

STATIC_URL = '/static/'
if os.getenv("PROD_SERVER"):
    MEDIA_ROOT = '/media/'

# Machine Learning Model
# Pull Machine Learning Model from GCS

if os.getenv("GCP_PRODUCTION"):
    bucketName = 'coral-dataset'
    bucketFolder = './'
    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucketName)
    download_file(bucketName, bucketFolder, "./", "yolov4.h5")

H5_MODEL = load_model("./yolov4.h5")

AUTH_USER_MODEL = 'user.UserProfile'
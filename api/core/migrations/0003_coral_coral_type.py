# Generated by Django 3.2.3 on 2021-06-01 11:27

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0002_coral_image_path'),
    ]

    operations = [
        migrations.AddField(
            model_name='coral',
            name='coral_type',
            field=models.CharField(default=None, max_length=255),
            preserve_default=False,
        ),
    ]

# Generated by Django 3.2.3 on 2021-08-28 20:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0002_alter_forum_date'),
    ]

    operations = [
        migrations.AlterField(
            model_name='forum',
            name='date',
            field=models.DateField(auto_now_add=True),
        ),
    ]
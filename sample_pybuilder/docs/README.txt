
#set python path for your main source code
set PYTHONPATH=%PYTHONPATH%;C:\Users\menaj\Desktop\oxygen_workspace\sample_pybuilder\src\main\python

#set python path for tar.gar uncompressed folder.
set PYTHONPATH=%PYTHONPATH%;C:\Users\menaj\Desktop\sample_pybuilder-1.0.dev0

#this allows to run the scripts\hello-pybuilder.py file and have the correct imports work.

#this is maven concept for python creating projects.
http://pybuilder.github.io/

#do this
 1. pip install pybuilder
 
 #create a sample folder and accept the defaults for now
 2.0 mkdir helloworld
 2.1 pyb --start-project
 
 #this will drag in ur deps
 3. pyb install_dependencies publish
 
 #follow this link to allow pydev/pycharm ide to view and use your proj
 http://pybuilder.github.io/documentation/ide.html#.WwVALu4vzIU
 
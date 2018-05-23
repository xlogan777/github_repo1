#!/usr/bin/python
import sys
from helloworld import helloworld

sys.path.append('../')
sys.stdout.write('Hello from my script!\n')
rv = helloworld("JIMMY")
print rv;
#!/usr/bin/python
import unittest

from helloworld import helloworld

class HelloWorldTest(unittest.TestCase):
   def test_should_issue_hello_world_message(self):
      rv = helloworld("JIMMY")
      self.assertEqual('collado'.upper(), rv)
      #self.assertEqual("mena".upper(), rv)
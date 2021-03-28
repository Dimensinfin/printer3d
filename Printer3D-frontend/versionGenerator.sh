#!/bin/bash
grep -w version package.json | grep -v figlet | awk '{print $2}' | sed 's/\"//g' | sed 's/,//g' | sed 's/\./\. /g'

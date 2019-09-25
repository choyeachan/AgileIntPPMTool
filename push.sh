#!/bin/bash

echo "======> branch$1"
# git branch branch$1
git checkout branch$1
git add .
git commit -m "branch$1 - $2"
git push --set-upstream origin branch$1
git checkout master
git merge branch$1
git push


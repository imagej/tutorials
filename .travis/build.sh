#!/bin/sh
dir=$(cd "$(dirname "$0")" && pwd)
if [ "$TRAVIS_SECURE_ENV_VARS" = true \
  -a "$TRAVIS_PULL_REQUEST" = false \
  -a "$TRAVIS_BRANCH" = master ]
then
  cd maven-projects &&
  mvn -Pdeploy-to-imagej deploy --settings "$dir/settings.xml"
else
  cd maven-projects &&
  mvn install
fi

#! /bin/bash

./gradlew sonarqube \
  -Dsonar.projectKey=Miage-Paris-Ouest_realtime-keynote-feedback-2 \
  -Dsonar.organization=miage-paris-ouest-1 \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=4b0c0de06c2cb616e711dde7dc911512e4a6f6a6

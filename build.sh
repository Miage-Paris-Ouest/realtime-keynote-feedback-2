#! /bin/bash

./gradlew sonarqube \
  -Dsonar.projectKey=Miage-Paris-Ouest_realtime-keynote-feedback-2 \
  -Dsonar.organization=miage-paris-ouest-1 \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=4a2619a6b2108fb788121ad64b5c38018463b277

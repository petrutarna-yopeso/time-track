To start jenkins locally run next command. Replace path to jenkins folder.
docker run -d -v /Users/petrutarna/Dev/time-track/infrastructure/jenkins/jenkins_home:/var/jenkins_home -p 8088:8080 -p 50000:50000 --restart=on-failure jenkins/jenkins:lts-jdk11

Username: admin
Password: password123!
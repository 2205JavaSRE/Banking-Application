pipeline {
    agent any
    environment{
        DOCKERHUB_CREDS = credentials(dockerHubCredentials)
    }
    stages {
        stage(" Maven Build"){
            steps{
                sh "echo stage 1"
                sh "/usr/bin/mvn clean package"
            }
        }
        stage("Docker Build"){
            steps{
                sh "docker image rm --force ooido/pg-pod"
                sh "docker image rm --force ooido/banking-api"
                sh "docker build -t ooido/pg-pod -f ./resources/postgres/Dockerfile ."
                sh "docker build -t ooido/banking-api -f ./resources/banking-api/Dockerfile ."
            }
        }
        stage("Docker Push"){
            steps{
                sh "docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW"
                sh "docker image push ooido/pg-pod"
                sh "docker image push ooido/banking-api"
            }
        }
        stage("Stage 3"){
            steps{
                sh "echo stage 3"
            }
        }
        stage("stage 4"){
            steps{
                sh "echo stage 4"
            }
        }
    }
}
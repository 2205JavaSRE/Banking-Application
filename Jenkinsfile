pipeline {
    agent any
    environment{
        registry='ooido/banking-api';
        dockerImage=''
        dockerHubCredentials='dockerHubCredentials'
    }
    stages {
        stage(" Maven Build"){
            steps{
                sh "echo stage 1"
                sh "/usr/bin/mvn clean package"
            }
        }
        stage("Docker Build and Push"){

            steps{
                sh "echo stage 2"
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
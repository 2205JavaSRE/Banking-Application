pipeline {
    agent any
    environment{
        DOCKERHUB_CREDS = credentials('dockerHubCredentials')
        AWS_CREDS = credentials('awsCreds')
    }
    stages {
        stage("Maven Build"){
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
        stage("Deployment"){
            steps{
                sh "aws --profile ben-sre-1368 configure set aws_access_key_id $AWS_CREDS_USR"
                sh "aws --profile ben-sre-1368 configure set aws_secret_access_key $AWS_CREDS_PSW"
                sh "aws eks --region us-east-1 update-kubeconfig --name ben-sre-1368 --profile ben-sre-1368"

                echo "______applying new kube resources"
                sh "kubectl apply -f ./resources/postgres/postgres-configmap.yml -n null-space"
                sh "kubectl apply -f ./resources/postgres/postgres-pod.yml -n null-space"
                sh "kubectl apply -f ./resources/postgres/postgres-pvc.yml -n null-space"
                sh "kubectl apply -f ./resources/postgres/postgres-service.yml -n null-space"

                sh "kubectl apply -f ./resources/banking-api/banking-api-deployment.yml -n null-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-monitor.yml -n null-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-service.yml -n null-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-ingress.yml -n null-space"
            }
        }
        stage("stage 4"){
            steps{
                sh "echo stage 4"
            }
        }
    }
}
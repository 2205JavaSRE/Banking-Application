pipeline {
    agent any
    environment{
        DOCKERHUB_CREDS = credentials('dockerHubCredentials')
        AWS_ACCESS_KEY_ID     = credentials('jenkins-aws-secret-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins-aws-secret-access-key')
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
                sh "aws --profile ben-sre-1368 configure set aws_access_key_id "$AWS_ACCESS_KEY_ID""
                sh "aws --profile ben-sre-1368 configure set aws_secret_access_key "$AWS_SECRET_ACCESS_KEY""
                sh "aws eks --region us-east-1 update-kubeconfig --name ben-sre-1368 --profile ben-sre-1368"

                echo ______deleting old kube resources
                sh "kubectl delete -f ./resources/postgres/postgres-pod.yml -n ooido-space"
                sh "kubectl delete -f ./resources/postgres/postgres-pvc.yml -n ooido-space"
                sh "kubectl delete -f ./resources/postgres/postgres-service.yml -n ooido-space"
                sh "kubectl delete -f ./resources/postgres/postgres-configmap.yml"
                sh "kubectl delete -f ./resources/postgres/postgres-configmap.yml -n ooido-space"
                #kubectl delete -f ./resources/postgres/pg-monitor.yml -n ooido-space"

                sh "kubectl delete -f ./resources/banking-api/banking-api-deployment.yml -n ooido-space"
                sh "kubectl delete -f ./resources/banking-api/banking-api-monitor.yml -n ooido-space"
                sh "kubectl delete -f ./resources/banking-api/banking-api-service.yml -n ooido-space"
                sh "kubectl delete -f ./resources/banking-api/banking-api-ingress.yml -n ooido-space"

                echo ______applying new kube resources
                sh "kubectl apply -f ./resources/postgres/postgres-configmap.yml -n ooido-space"
                sh "kubectl apply -f ./resources/postgres/postgres-pod.yml -n ooido-space"
                sh "kubectl apply -f ./resources/postgres/postgres-pvc.yml -n ooido-space"
                sh "kubectl apply -f ./resources/postgres/postgres-service.yml -n ooido-space"
                #kubectl apply -f ./resources/postgres/pg-monitor.yml -n ooido-space"

                sh "kubectl apply -f ./resources/banking-api/banking-api-deployment.yml -n ooido-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-monitor.yml -n ooido-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-service.yml -n ooido-space"
                sh "kubectl apply -f ./resources/banking-api/banking-api-ingress.yml -n ooido-space"
            }
        }
        stage("stage 4"){
            steps{
                sh "echo stage 4"
            }
        }
    }
}
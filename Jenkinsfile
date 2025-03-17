pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sudeep0408/ecommerce-backend.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'  // Builds the project
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'  // Runs unit tests
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
            }
        }
    }
}

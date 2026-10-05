pipeline {
    agent any

    environment {
        // GitHub
        GITHUB_REPO = 'https://github.com/saravananmnm/student_rest_api.git'
        GITHUB_BRANCH = 'master'

        // Docker
        DOCKER_IMAGE = 'saro/student_mgmt'
        DOCKER_TAG = "${BUILD_NUMBER}"

        // Application
        CONTAINER_NAME = 'student_mgmt'
        APP_PORT = '8015'
    }

    stages {

        /*
         * 1. Checkout source code from GitHub
         */
        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'

                git(
                        branch: "${GITHUB_BRANCH}",
                        url: "${GITHUB_REPO}"
                )
            }
        }

        /*
         * 2. Build Spring Boot application
         */
        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'

                bat '''
                    chmod +x mvnw
                    ./mvnw clean package -DskipTests
                '''
            }
        }

        /*
         * 3. Run unit tests
         */
        stage('Test') {
            steps {
                echo 'Running unit tests...'

                bat '''
                    ./mvnw test
                '''
            }
        }

        /*
         * 4. Build Docker image
         */
        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                bat """
                    docker build \
                        -t ${DOCKER_IMAGE}:${DOCKER_TAG} \
                        -t ${DOCKER_IMAGE}:latest \
                        .
                """
            }
        }

        /*
         * 5. Push Docker image to Docker Hub
         */
        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([
                        usernamePassword(
                                credentialsId: 'dockerhub-credentials',
                                usernameVariable: 'gsaravanan3.3sgm@gmail.com',
                                passwordVariable: 'f@pbDaqHY96PT=a'
                        )
                ]) {

                    bat '''
                        echo "f@pbDaqHY96PT=a" | docker login \
                            -u "gsaravanan3.3sgm@gmail.com" \
                            --password-stdin

                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        docker push ${DOCKER_IMAGE}:latest

                        docker logout
                    '''
                }
            }
        }

        /*
         * 6. Deploy application
         */
        stage('Deploy') {
            steps {
                echo 'Deploying Spring Boot application...'

                bat '''
                    docker pull ${DOCKER_IMAGE}:latest

                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true

                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p ${APP_PORT}:8080 \
                        --restart unless-stopped \
                        ${DOCKER_IMAGE}:latest
                '''
            }
        }

        /*
         * 7. Verify deployment
         */
        stage('Health Check') {
            steps {
                echo 'Checking application health...'

                bat '''
                    sleep 10

                    curl --fail http://localhost:${APP_PORT}/actuator/health
                '''
            }
        }
    }

    /*
     * Pipeline result
     */
    post {

        success {
            echo """
            ==========================================
            Deployment Successful!
            Application: ${DOCKER_IMAGE}
            Version: ${DOCKER_TAG}
            ==========================================
            """
        }

        failure {
            echo """
            ==========================================
            Deployment Failed!
            Check Jenkins console output.
            ==========================================
            """
        }

        always {
            echo 'Cleaning Jenkins workspace...'
            cleanWs()
        }
    }
}

pipeline {
    agent any

    tools {
        jdk 'JAVA_21'
    }

    environment {
        // GitHub
        GITHUB_REPO   = 'https://github.com/saravananmnm/student_rest_api.git'
        GITHUB_BRANCH = 'master'

        // Docker
        DOCKER_IMAGE = 'saravanang87/student_mgmt'
        DOCKER_TAG   = "${BUILD_NUMBER}"

        // Application
        CONTAINER_NAME = 'student_mgmt'
        APP_PORT       = '8015'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'
                deleteDir()
                git(
                        branch: "${GITHUB_BRANCH}",
                        url: "${GITHUB_REPO}"
                )
            }
        }
        stage('Debug Workspace') {
            steps {
                bat '''
                    echo ==============================
                    echo CURRENT DIRECTORY
                    echo ==============================
                    cd
        
                    echo.
                    echo ==============================
                    echo WORKSPACE FILES
                    echo ==============================
                    dir /a
        
                    echo.
                    echo ==============================
                    echo DOCKERFILE SEARCH
                    echo ==============================
                    dir /s /b Dockerfile*
        
                    echo.
                    echo ==============================
                    echo DOCKERFILE ROOT CHECK
                    echo ==============================
                    if exist Dockerfile (
                        echo FOUND: Dockerfile
                    ) else (
                        echo NOT FOUND: Dockerfile
                    )
                '''
            }
        }


        stage('Check Java') {
            steps {
                bat '''
                    echo JAVA_HOME=%JAVA_HOME%
                    where java
                    java -version
                '''
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'

                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'

                bat 'mvnw.cmd test'
            }
        }

        stage('Check Files') {
            steps {
                echo 'Checking Dockerfile and JAR...'

                bat '''
                    echo.
                    echo Current directory:
                    cd

                    echo.
                    echo Project files:
                    dir

                    echo.
                    echo Target files:
                    dir target
                '''
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                bat 'docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% -t %DOCKER_IMAGE%:latest .'
            }
        }
        stage('Docker Login Test') {
            steps {
                withCredentials([
                        usernamePassword(
                                credentialsId: 'dockerhub-credentials',
                                usernameVariable: 'DOCKER_USERNAME',
                                passwordVariable: 'DOCKER_PASSWORD'
                        )
                ]) {
                    bat '''
                        echo Username=[%DOCKER_USERNAME%]
                        docker logout
                        echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin
                    '''
                }
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([
                        usernamePassword(
                                credentialsId: 'dockerhub-credentials',
                                usernameVariable: 'DOCKER_USERNAME',
                                passwordVariable: 'DOCKER_PASSWORD'
                        )
                ]) {

                    bat '''
                        docker logout
                        echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin
                
                        if errorlevel 1 exit /b 1
                
                        docker push %DOCKER_IMAGE%:%DOCKER_TAG%
                
                        if errorlevel 1 exit /b 1
                
                        docker push %DOCKER_IMAGE%:latest
                
                        if errorlevel 1 exit /b 1
                
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'

                bat '''
                    docker pull %DOCKER_IMAGE%:latest

                    docker stop %CONTAINER_NAME% >nul 2>&1 || exit /b 0
                    docker rm %CONTAINER_NAME% >nul 2>&1 || exit /b 0

                    docker run -d ^
                        --name %CONTAINER_NAME% ^
                        -p %APP_PORT%:8080 ^
                        --restart unless-stopped ^
                        %DOCKER_IMAGE%:latest
                '''
            }
        }

        stage('Verify') {
            steps {
                echo 'Checking Docker container...'

                bat 'docker ps'
            }
        }
    }

    post {

        success {
            echo '''
            ==========================================
              Deployment Successful!
            ==========================================
            '''
        }

        failure {
            echo '''
            ==========================================
              Deployment Failed!
            ==========================================
            '''
        }

        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
    }
}

pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                    sh 'mvn clean package'
            }
        }
        stage ('Deploy') {
            steps {
                pushToCloudFoundry cloudSpace: 'development', credentialsId: 'PCF_LOGIN', organization: 'Spring Apps', pluginTimeout: '300', target: 'https://api.run.pivotal.io/'
            }
        }
    }
}
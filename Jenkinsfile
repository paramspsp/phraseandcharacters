pipeline {

    agent any

    stages {

        stage ('Build') {
            steps {
                  def mvn_version = ''
                  withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] )
                    {
                        sh  'mvn clean package'
                    }
            }
        }

        stage ('Deploy') {
            steps {

                withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : 'PCF_LOGIN',
                                  usernameVariable: 'USERNAME',
                                  passwordVariable: 'PASSWORD']]) {

                    sh '/usr/local/bin/cf login -a http://api.run.pivotal.io -u $USERNAME -p $PASSWORD'
                    sh '/usr/local/bin/cf push'
                }
            }

        }

    }

}
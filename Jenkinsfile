pipeline {
    agent any
	
 environment {
        registryCredential = 'dockerhub'
        registry = "houssemsy/tpachatproject"
    }

    stages {
        stage('Getting the project from GIT') {
            steps {
               echo 'Test';
            }
        }
        
        stage('MVN CLEAN') {
            steps {
               
              script {

                  sh 'mvn clean'

 
                      }
                   }        
         }
        stage('MVN compile') {
            steps {
               
              script {

                  sh 'mvn compile'

 
                      }
                   }        
         }
         stage('MVN INSTALL') {
            steps {
               
              script {

                  sh 'mvn install'

 
                      }
                   }        
         }
         stage('Test unitaire') {
            steps {
                script {
                    
                sh 'mvn test'  
		
               
                }

		        
            } 
           
        }
	
         
          stage('SONAR') {
            steps {
               
              script {
		
                   jacoco execPattern: 'target/jacoco.exec'
                  sh 'mvn sonar:sonar  -Dsonar.sources=src/main/java -Dsonar.css.node=. -Dsonar.java.binaries=. -Dsonar.host.url=http://192.168.1.166:9000/ -Dsonar.login=admin   -Dsonar.password=sonar'

 
                      }
                   }         
         }
	    
          stage('MVN package') {
            steps {
                script {
                    
                sh 'mvn package'  
                }

		        
            } 
           
        }
	
         stage('nexus') {
            steps {
               
              script {

sh 'mvn deploy -e'                      }
                   }         
         }
	    
	    
	    stage('Building our image') {
  steps {
               
sh 'docker build -t houssemsy/tpachatproject .'
               
            }
        }
	    
   stage('Docker compose') {
             
             
            steps {
               
            sh 'docker-compose up -d'
               
            }
        }
       
   stage('Push Dockerhub') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        sh "docker push $registry"
                    }
                    
                }
                
            }
            
        }
	    stage('Run Spring et MySQL Containers') {
                                steps {
                                    script {
                                      sh ' docker-compose up -d '
                                    }
                                }
                            }
       
     }
	post {
         always {
            junit(testResults: 'target/surefire-reports/*.xml', allowEmptyResults : true)
        }
         success {
            emailext attachLog: true, body: '''End of Pipeline
            Finished: SUCCESS''', subject: '#Success', to: 'houssemsayehi90@gmail.com'
         }
         failure  {
            emailext attachLog: true, body: '''End of Pipeline
            Finished: FAILURE''', subject: '#Failure', to: 'houssemsayehi90@gmail.com'
         }
     
    }
     
}

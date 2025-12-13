pipeline {
    agent any

    tools {
            maven 'MAVEN_3'   // Maven installed via Global Tool Configuration
        }

    parameters {
            choice(
                name: 'PLATFORM_TYPE',
                choices: ['android', 'ios'],
                description: 'Target platform'
            )

            string(
                name: 'DEVICE_PROFILE',
                defaultValue: 'local.virtual.pixel7.json',
                description: 'Device profile JSON'
            )

            choice(
                name: 'BRANCH',
                choices: ['main', 'cicd'],
                description: 'Git branch'
            )

            choice(
                name: 'TESTNG_SUITE',
                choices: ['testng-develop.xml', 'testng-sanity.xml', 'testng-regression.xml'],
                description: 'TestNG suite file'
            )
        }

    environment {
            SAUCE_USERNAME = credentials('sauce-username')
            SAUCE_ACCESS_KEY = credentials('sauce-access-key')
        }

    stages {
            stage('Checkout') {
                steps {
                    git branch: "${params.BRANCH}",
                        credentialsId: 'github-creds',
                        url: 'https://github.com/automationworldindia/SwagLabsMobileTests.git'
                }
            }

            stage('Run Appium Tests') {
                steps {
                    sh """
                        mvn clean test \
                        -Dplatform.type=${PLATFORM_TYPE} \
                        -Ddevice.profile=${DEVICE_PROFILE} \
                        -DsuiteXmlFile=${TESTNG_SUITE} \
                        -Dperfecto.username=${SAUCE_USERNAME} \
                        -Dperfecto.accessKey=${SAUCE_ACCESS_KEY}
                    """
                }
            }

            stage('Allure Report') {
                steps {
                    allure(
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'allure-results']]
                    )
                }
            }
        }

    post {
            always {
                archiveArtifacts artifacts: '**/surefire-reports/*.xml', allowEmptyArchive: true
            }
            failure {
                echo '❌ Test execution failed'
            }
            success {
                echo '✅ Tests completed successfully'
            }
        }
}
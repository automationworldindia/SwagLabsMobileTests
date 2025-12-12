pipeline {
    agent any

    tools {
            maven 'MAVEN_3'   // Maven installed via Global Tool Configuration
        }

    parameters {
        // ---------------------------
        // Environment Params
        // ---------------------------
        string(name: 'APPIUM_JS_PATH', defaultValue: '/opt/homebrew/lib/node_modules/appium/build/lib/main.js', description: 'Path to appium main.js on host machine')
        string(name: 'APPIUM_NODE_PATH', defaultValue: '/opt/homebrew/bin/node', description: 'Path to node binary on host machine')
        string(name: 'APPIUM_IP', defaultValue: '127.0.0.1', description: 'Appium server IP')
        string(name: 'APPIUM_PORT', defaultValue: '4723', description: 'Appium server port')

        booleanParam(name: 'PERFECTO_ENABLE', defaultValue: false, description: 'Enable Perfecto cloud execution')
        string(name: 'PERFECTO_USERNAME', defaultValue: '', description: 'Perfecto username')
        string(name: 'PERFECTO_ACCESSKEY', defaultValue: '', description: 'Perfecto access key')

        string(name: 'PLATFORM_TYPE', defaultValue: 'android', description: 'android / ios')
        string(name: 'DEVICE_PROFILE', defaultValue: 'local.virtual.pixel7.json', description: 'Device profile json')

        // ---------------------------
        // TestNG Suite
        // ---------------------------
        choice(
            name: 'TESTNG_SUITE',
            choices: [
                'testng-develop.xml',
                'testng-regression.xml',
                'testng-sanity.xml'
            ],
            description: 'Select the TestNG suite to execute'
        )

        // ---------------------------
        // Branch
        // ---------------------------
        choice(
            name: 'GIT_BRANCH',
            choices: [
                'main',
                'cicd'
            ],
            description: 'Select the Git branch to checkout'
        )
    }

    environment {
        APPIUM_JS_PATH     = "${params.APPIUM_JS_PATH}"
        APPIUM_NODE_PATH   = "${params.APPIUM_NODE_PATH}"
        APPIUM_IP          = "${params.APPIUM_IP}"
        APPIUM_PORT        = "${params.APPIUM_PORT}"

        PERFECTO_ENABLE    = "${params.PERFECTO_ENABLE}"
        PERFECTO_USERNAME  = "${params.PERFECTO_USERNAME}"
        PERFECTO_ACCESSKEY = "${params.PERFECTO_ACCESSKEY}"

        PLATFORM_TYPE      = "${params.PLATFORM_TYPE}"
        DEVICE_PROFILE     = "${params.DEVICE_PROFILE}"

        TESTNG_SUITE       = "${params.TESTNG_SUITE}"

        GIT_BRANCH         = "${params.GIT_BRANCH}"
    }

    stages {
        // ---------------------
        // Checkout Code
        // ---------------------
        stage('Checkout') {
            steps {
                git branch: '${GIT_BRANCH}',
                    url: 'https://github.com/automationworldindia/SwagLabsMobileTests.git',
                    credentialsId: 'github-creds'
            }
        }
        // ---------------------
        // Update Config
        // ---------------------
        stage('Update Config') {
            steps {
                sh '''
                echo "Updating config.properties..."

                cat > Config.properties <<EOF
appium.js.path=${APPIUM_JS_PATH}
appium.node.path=${APPIUM_NODE_PATH}
appium.ip.address=${APPIUM_IP}
appium.port=${APPIUM_PORT}

perfecto.enable=${PERFECTO_ENABLE}
perfecto.username=${PERFECTO_USERNAME}
perfecto.accessKey=${PERFECTO_ACCESSKEY}

platform.type=${PLATFORM_TYPE}
device.profile=${DEVICE_PROFILE}
EOF
                '''
            }
        }

        // ---------------------
        // Install Dependencies
        // ---------------------
        stage('Install Dependencies') {
            steps {
                sh "mvn -v"
            }
        }

        // ---------------------
        // Run Tests
        // ---------------------
        stage('Run Tests') {
            steps {
                sh """
                    echo "Running TestNG suite: ${TESTNG_SUITE}"

                    mvn clean test \
                        -DsuiteXmlFile=${TESTNG_SUITE}
                """
            }
        }
        // ---------------------
        // Allure Report
        // ---------------------
        stage('Allure Report') {
            steps {
                allure includeProperties: true, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
        }
    }
}

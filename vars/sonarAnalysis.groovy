def call(boolean abortPipeline = false, boolean waitForSonarResults = true) {
    stage('SonarQube Analysis') {
        steps {
            script {
                // Ejecuta el análisis de SonarQube
                sh 'sonnar-scanner'
                
                if (waitForSonarResults) {
                    // Espera durante 5 minutos por los resultados del análisis
                    timeout(time: 5, unit: 'MINUTES') {
                        def qualityGate = waitForQualityGate() // Función que verifica el QualityGate de SonarQube
                        if (abortPipeline && qualityGate.status != 'OK') {
                            error("QualityGate failed, aborting the pipeline.")
                        }
                    }
                } else {
                    echo 'SonarQube analysis completed.'
                }
            }
        }
    }
}
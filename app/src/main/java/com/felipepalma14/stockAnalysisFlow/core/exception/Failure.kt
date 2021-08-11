package com.felipepalma14.stockAnalysisFlow.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object DataError : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
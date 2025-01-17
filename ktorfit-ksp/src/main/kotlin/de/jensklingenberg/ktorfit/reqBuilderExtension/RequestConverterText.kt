package de.jensklingenberg.ktorfit.reqBuilderExtension

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ksp.toClassName
import de.jensklingenberg.ktorfit.model.ParameterData
import de.jensklingenberg.ktorfit.model.annotations.ParameterAnnotation.RequestType
import de.jensklingenberg.ktorfit.model.converterHelper

fun FunSpec.Builder.addRequestConverterText(parameterDataList: List<ParameterData>) = apply {
    if (parameterDataList.any { it.hasAnnotation<RequestType>() }) {
        parameterDataList.map { parameter ->
            val requestTypeClassName =
                parameter.annotations.filterIsInstance<RequestType>().first().requestType.toClassName()
            if (parameter.hasAnnotation<RequestType>()) {
                this.addStatement(
                    "val %L: %T = %L.convertParameterType(%L,%L::class,%T::class)",
                    parameter.name,
                    requestTypeClassName,
                    converterHelper.objectName,
                    parameter.name,
                    parameter.name,
                    requestTypeClassName
                )
            }
            parameter.name
        }
    }
}
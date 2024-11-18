package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.CategoryDbModel
import mega.triple.aaa.data.network.response.model.CategoryResponse
import mega.triple.aaa.presentation.core.ui.model.forecast.CategoryUiModel

data class CategoryDomainModel(
    val category: String?,
    val categoryValue: Int?,
    val name: String?,
    val type: String?,
    val value: Int?,
) {
    companion object {
        fun CategoryResponse.toDbModel(): CategoryDbModel =
            CategoryDbModel(
                category = category,
                categoryValue = categoryValue,
                name = name,
                type = type,
                value = value,
            )

        fun CategoryResponse.toDomainModel(): CategoryDomainModel =
            CategoryDomainModel(
                category = category,
                categoryValue = categoryValue,
                name = name,
                type = type,
                value = value,
            )

        fun CategoryDbModel.toDomainModel(): CategoryDomainModel =
            CategoryDomainModel(
                category = category,
                categoryValue = categoryValue,
                name = name,
                type = type,
                value = value,
            )

        fun CategoryDomainModel.toUiModel(): CategoryUiModel =
            CategoryUiModel(
                category = category,
                categoryValue = categoryValue,
                name = name,
                type = type,
                value = value,
            )
    }
}

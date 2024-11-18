package mega.triple.aaa.presentation.core.ui.model.forecast


data class ValueWrapperUiModel(
    val average: ValueUiModel?,
    val maximum: ValueUiModel?,
    val minimum: ValueUiModel?
) {
    val mathAverage by lazy {
        maximum?.value?.plus(minimum?.value ?: 0.0)?.div(2)
    }
}

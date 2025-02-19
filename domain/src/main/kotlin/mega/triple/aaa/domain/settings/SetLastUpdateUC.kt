package mega.triple.aaa.domain.settings

interface SetLastUpdateUC {
    suspend operator fun invoke(date: Long): Result<Unit>
}

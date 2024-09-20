package mega.triple.aaa.domain.location.model

import mega.triple.aaa.data.proto.LocationProto
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toProtoModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toProtoModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toProtoModel
import mega.triple.aaa.presentation.core.ui.model.CityUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.ContinentUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.CountryUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.LocationUiModel

data class LocationDomainModel(
    val continent: ContinentDomainModel? = null,
    val country: CountryDomainModel? = null,
    val city: CityDomainModel? = null,
) {
    companion object {
        fun LocationDomainModel.toUiModel(): LocationUiModel =
            LocationUiModel(
                continent = continent?.toUiModel(),
                country = country?.toUiModel(),
                city = city?.toUiModel(),
            )

        fun LocationUiModel.toDomainModel(): LocationDomainModel =
            LocationDomainModel(
                continent = continent?.toDomainModel(),
                country = country?.toDomainModel(),
                city = city?.toDomainModel(),
            )

        fun LocationDomainModel.toProtoModel(): LocationProto =
            LocationProto.newBuilder()
                .setContinent(continent?.toProtoModel())
                .setCountry(country?.toProtoModel())
                .setCity(city?.toProtoModel())
                .build()

        fun LocationProto.toDomainModel(): LocationDomainModel =
            LocationDomainModel(
                continent = continent?.toDomainModel(),
                country = country?.toDomainModel(),
                city = city?.toDomainModel(),
            )
    }
}
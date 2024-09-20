package mega.triple.aaa.data.proto.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import mega.triple.aaa.data.proto.LocationProto
import java.io.InputStream
import java.io.OutputStream

object LocationProtoSerializer : Serializer<LocationProto> {
    override val defaultValue: LocationProto
        get() = LocationProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocationProto {
        try {
            return LocationProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LocationProto, output: OutputStream) = t.writeTo(output)
}
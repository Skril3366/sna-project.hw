package mongo

import java.time.Instant

import ru.tinkoff.oolong.bson.BsonEncoder
import ru.tinkoff.oolong.bson.BsonDecoder
import ru.tinkoff.oolong.bson.given

case class LogModel(
    date: Instant,
    host: String,
    process: String,
    pid: Int,
    message: String
) derives BsonEncoder, BsonDecoder

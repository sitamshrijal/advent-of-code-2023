fun main() {
    fun part1(input: List<String>): Long {
        val almanac = Almanac.parse(input)

        return almanac.seeds.minOf { seed ->
            val soil = almanac.seedToSoil.retrieve(seed)
            val fertilizer = almanac.soilToFertilizer.retrieve(soil)
            val water = almanac.fertilizerToWater.retrieve(fertilizer)
            val light = almanac.waterToLight.retrieve(water)
            val temperature = almanac.lightToTemperature.retrieve(light)
            val humidity = almanac.temperatureToHumidity.retrieve(temperature)
            almanac.humidityToLocation.retrieve(humidity)
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input5")
    part1(input).println()
    part2(input).println()
}

data class Almanac(
    val seeds: List<Long>,
    val seedToSoil: Map<LongRange, LongRange>,
    val soilToFertilizer: Map<LongRange, LongRange>,
    val fertilizerToWater: Map<LongRange, LongRange>,
    val waterToLight: Map<LongRange, LongRange>,
    val lightToTemperature: Map<LongRange, LongRange>,
    val temperatureToHumidity: Map<LongRange, LongRange>,
    val humidityToLocation: Map<LongRange, LongRange>
) {
    companion object {
        fun parse(input: List<String>): Almanac {
            val seeds = input[0].substringAfter("seeds: ").split(" ").map { it.toLong() }

            val groups = buildList {
                val list = mutableListOf<String>()
                input.drop(2).forEach { line ->
                    if (line.isEmpty()) {
                        add(list.toList())
                        list.clear()
                    } else {
                        list += line
                    }
                }
                if (list.isNotEmpty()) {
                    add(list.toList())
                }
            }

            var seedToSoil = mutableMapOf<LongRange, LongRange>()
            var soilToFertilizer = mutableMapOf<LongRange, LongRange>()
            var fertilizerToWater = mutableMapOf<LongRange, LongRange>()
            var waterToLight = mutableMapOf<LongRange, LongRange>()
            var lightToTemperature = mutableMapOf<LongRange, LongRange>()
            var temperatureToHumidity = mutableMapOf<LongRange, LongRange>()
            var humidityToLocation = mutableMapOf<LongRange, LongRange>()

            for (group in groups) {
                val type = group[0]
                val values = group.drop(1)

                val map = mutableMapOf<LongRange, LongRange>()

                for (line in values) {
                    val (destinationStart, sourceStart, length) = line.split(" ")
                        .map { it.toLong() }

                    val source = (sourceStart..<(sourceStart + length))
                    val destination = (destinationStart..<(destinationStart + length))
                    map[source] = destination
                }
                when (type) {
                    "seed-to-soil map:" -> seedToSoil = map
                    "soil-to-fertilizer map:" -> soilToFertilizer = map
                    "fertilizer-to-water map:" -> fertilizerToWater = map
                    "water-to-light map:" -> waterToLight = map
                    "light-to-temperature map:" -> lightToTemperature = map
                    "temperature-to-humidity map:" -> temperatureToHumidity = map
                    "humidity-to-location map:" -> humidityToLocation = map
                }
            }
            return Almanac(
                seeds,
                seedToSoil,
                soilToFertilizer,
                fertilizerToWater,
                waterToLight,
                lightToTemperature,
                temperatureToHumidity,
                humidityToLocation
            )
        }
    }
}

fun Map<LongRange, LongRange>.retrieve(number: Long): Long {
    val contains = keys.any { number in it }
    return if (contains) {
        val source = keys.find { number in it }!!
        val destination = getOrDefault(source, 0L..0L)

        val index = number - source.first
        destination.first + index
    } else {
        number
    }
}

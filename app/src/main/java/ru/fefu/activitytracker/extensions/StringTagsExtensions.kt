package ru.fefu.activitytracker.extensions

fun String.getTagsIntervals(tags: Set<Char> = setOf('[', ']')): MutableList<Pair<Int, Int>> {
    val intervals = mutableListOf<Pair<Int, Int>>()
    var tagsToBeRemovedCount = 0
    var index = 0

    while (index < length) {
        if (tags.contains(this[index])) {
            val intervalStart = index - tagsToBeRemovedCount
            ++tagsToBeRemovedCount

            do {
                ++index
            } while (!tags.contains(this[index]))

            val intervalEnd = index - tagsToBeRemovedCount
            ++tagsToBeRemovedCount
            intervals.add(Pair(intervalStart, intervalEnd))
        }

        ++index
    }

    return intervals
}

fun String.removeTags(tags: Set<Char> = setOf('[', ']')): String = filterNot { tags.contains(it) }
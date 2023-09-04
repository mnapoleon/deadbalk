package org.napoleon.deadbalk.utils

inline infix fun <reified E: Enum<E>, V> ((E) -> V).findBy(value: V): E? {
    return enumValues<E>().firstOrNull{ this(it) == value}
}

fun String.isEmptyOrBlank() = this.isEmpty() or this.isBlank()
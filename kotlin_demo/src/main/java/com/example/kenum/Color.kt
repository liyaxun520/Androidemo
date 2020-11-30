package com.example.kenum

enum class Color(val rgb:Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

fun main(args:Array<String>) {
    var blue = Color.RED
    Color.values().forEach {
       println(it)
    }
    println(Color.valueOf("RED"))
    println(blue.name)
    println(blue.ordinal)
    printAllValues<Color>()

    var rgb = blue.rgb
    println("色值  "+ Integer.toHexString(rgb))

    val initRGB = 255
    Color.values().forEach {
        if (it.rgb == initRGB) {
            println(it.name+"    "+it.ordinal+"    "+it.rgb)
        }
    }
}
package dev.nhalase

fun main(vararg args: String) {
    println("Hello, World!")
    if (args.isNotEmpty()) {
        println("args: ${args.joinToString(",")}")
    }
}

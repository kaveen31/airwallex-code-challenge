package com.airwallex.codechallenge

import com.airwallex.codechallenge.input.Reader

fun main(args: Array<String>) {
    Reader()
        .read(args[0])
        //
        // TODO process the input
        //
        .forEach {
            println(it)
        }
}
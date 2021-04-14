package com.flash21.giftrip.lib

import java.util.*

class GenerateCode {

    companion object {
        private val certCharLength: Int = 6
        private val characterTable = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0')

        fun execute(): String {
            val random: Random = Random(System.currentTimeMillis())
            val buf: StringBuffer = StringBuffer()

            for(i in 1..certCharLength) {
                buf.append(characterTable[random.nextInt(certCharLength)])
            }

            return buf.toString();
        }
    }

}
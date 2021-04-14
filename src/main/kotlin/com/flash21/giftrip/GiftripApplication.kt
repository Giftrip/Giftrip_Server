package com.flash21.giftrip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["org.bitbucket.tek.nik.simplifiedswagger", "com.flash21.giftrip"])
class GiftripApplication

fun main(args: Array<String>) {
	runApplication<GiftripApplication>(*args)
}

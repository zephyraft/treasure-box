package zephyr.demo

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val customer = Customer("123", "a@qq.com", 12)
    println("get=>${customer.email}")
    println("equal=>${customer == customer}")
    println("hashCode=>${customer.hashCode()}")
    println("toString=>$customer")
    println("copy=>${customer.copy(name = "a")}")
    println("component1=>${customer.component1()}")
    customer.age = 1 // set

    val list = listOf(1, -1, 2)
    println("filter=>${list.filter { it > 0 }}")
    println("contains=>${1 in list}")
    println("not contains=>${2 !in list}")

    val obj: Any = 1
    println("instanceof=>${obj is Int}")

    when (obj) {
        1 -> println("when=>is 1")
        2 -> println("when=>is 2")
        else -> println("when=>is else")
    }

    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    println("map=>${map}")
    println("map get=>b=${map["b"]}")

    println("============")
    println("map iterator")
    for ((k, v) in map) {
        println("$k -> $v")
    }
    println("============")

    val p: String by lazy {
        println("do lazy calculate")
        "lazy result"
    }
    println("============")
    print("lazy=>")
    println(p)
    println("============")

    println("extends=>${"Convert this to camelcase".spaceToCamelCase()}")

    println("Singleton=>${Singleton} | ${Singleton.name}")

    println("============")
    println("空检查=>")
    val nullFiles = File("Test").listFiles()
    val notNullFiles =
        File("/Users/zhonghaoyuan/IdeaProjects/treasure-box-gradle/treasure-box-basic/treasure-box-kotlin/src/main/kotlin/zephyr/demo").listFiles()
    println("${nullFiles?.size} | ${notNullFiles?.size} | ${nullFiles?.size ?: "empty"}")
    map["d"] ?: println("value is missing!")
    map["d"]?.let { println("value is missing!") }
    println(map["d"]?.let { transformValue(it) } ?: "defaultValue")

    println("============")
    println("对一个对象实例调用多个方法(with)")
    val myTurtle = Turtle()
    with(myTurtle) {
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }
    println("============")

    println("配置对象的属性（apply）")
    println("对于配置未出现在对象构造函数中的属性非常有用")
    val myRectangle = Customer("123", "a@qq.com", 12).apply {
        this.applyValue = 23
    }
    println("apply=>${myRectangle.applyValue}")
    println("============")

    println("try with resources=>")
    val stream =
        Files.newInputStream(Paths.get("/Users/zhonghaoyuan/IdeaProjects/treasure-box-gradle/treasure-box-basic/treasure-box-kotlin/src/main/resources/file.txt"))
    stream.buffered().reader().use { reader ->
        println(reader.readText())
    }
    println("============")

    println("交换两个变量(also)")
    var a = 1
    var b = 2
    println("before=>a=$a | b=$b")
    a = b.also {
        b = a
    }
    a = b.apply { b = a }
    println("also=>a=$a | b=$b")
    println("============")


}


// POJO
data class Customer(
    val name: String,
    val email: String,
    var age: Int,
    val fieldWithDefault: String = "default",
    var applyValue: Int? = -1,
)

// 扩展函数
// 空格转驼峰
fun String.spaceToCamelCase(): String {
    val source = this.trim().toCharArray()

    var spaceCount = 0
    val camelIndexList = ArrayList<Int>()
    for ((index, c) in source.withIndex()) {
        if (c == ' ') {
            spaceCount++
            camelIndexList.add(index + 1)
        }
    }

    val target = CharArray(source.size - spaceCount)
    var indexOffset = 0
    for ((index, c) in source.withIndex()) {
        when {
            index == 0 && c.isUpperCase() -> {
                target[index - indexOffset] = c.toLowerCase()
            }
            index in camelIndexList -> {
                target[index - indexOffset] = c.toUpperCase()
            }
            c != ' ' -> {
                target[index - indexOffset] = c
            }
            else -> {
                indexOffset++
            }
        }
    }

    return String(target)
}

// 单例
object Singleton {
    const val name = "Name"
}

fun transformValue(@Suppress("UNUSED_PARAMETER") it: Int): String? {
    return null
}

class Turtle {
    fun penDown() = println("penDown")
    fun penUp() = println("penUp")
    fun turn(degrees: Double) = println("turn degrees $degrees")
    fun forward(pixels: Double) = println("forward pixels $pixels")
}
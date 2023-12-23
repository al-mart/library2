package types

import Person2

// In kotlin types are explicitly nullable

fun sendEmail(email : String) = null

fun charCount(s: String): Int = s.count()


fun nullableCharCount( s: String?): Int? = s?.count() // ?. safe call operator calls function or returns null, so we must return Int?

fun testCharCount(){
    // charCount(null) // compile error as we provide null
    val count : Int? = nullableCharCount("hello")
    val count2 : Int = nullableCharCount("hello")!! //  !! non-null assertion gives us non-null type and if it gets null throws an error
    val intCount: Int = nullableCharCount("hello") ?: 0 // elvis operator returns second if the first is null

    val myPerson = Person("Mike", "Tyson", null)
    // sendEmail(myPerson.email) // we can solve it using let function which calls when we have non-null prop
    myPerson.email?.let { sendEmail(it) }
}


class Person(val fName: String, val lName: String, val email: String?){

    //var service: String
    var service2: String? = null
    lateinit var service3: String // lateinit is our saviour

    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person ?: return false // ?as safe cast so that we dont get an ClassCastException
        return otherPerson.fName == fName && otherPerson.lName == lName
    }

    fun before(){
        // this.service = "Service Initialized"
        this.service2 = "Service Initialized"
        this.service3 = "Service Initialized"
    }

    fun test(){
        println("service.count() ${service2!!.count()} ${service3.count()}")
    }
}

fun String?.isAndrew(): Boolean = this == "Andrew"  // nullable type extension

fun <T> printHashCode(t: T){ // In the book Type parameter T extends Any? but i didn't see it here so its better to use
// fun <T: Any>(t: T){
    println(t.hashCode())
}

fun testPrintHashCode(){
    printHashCode(null)
}

/**
 *  If working with java code kotlin interprets @Nullable as nullable and @NotNullable as not-nullable but if it is not annotated it becomes
 *  platform types so the kotlin compiler knows nothing about it. no compile errors but it can be nullable and throw errors need to be ready
 */


/**
 * Kotlin primitives
 *
 * Integer types—Byte, Short, Int, Long
 * Floating-point number types—Float, Double
 * Character type—Char
 * Boolean type—Boolean
 *
 * no wrapper types and kotlin decides what to use int or Integer at compile time
 * Int a = 8 would become int a = 8 in java
 * List<Int> = listOf() would become List<Integer> in java
 *
 * If we use Int? nullable integer kotlin would use Integer wrapper type
 *
 * no numeric conversion
 * val i: Int = 8
 * val longs: List<Long> = listOf()
 * longs.add(i) -> error
 * longs.add(i.toLong()) great)
 *
 *
 * Any is parent of all  non-nullable types as Object in java
 *
 */
// Unit is Void and can be skipped
fun returnUnit(a: Int, b: Int): Unit{
    val c: Int = a + b;
}

fun neverReturns(): Nothing{
    throw IllegalArgumentException("Never returns ")
}

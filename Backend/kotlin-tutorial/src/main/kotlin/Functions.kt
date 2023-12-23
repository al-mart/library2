@file:JvmName("FunctionInKotlinForJava")
package functions
import java.lang.StringBuilder

fun main(){
    collectionCreation()
    testExtensionFunctionOverride()
    testVarargsAndSpread("Hello", "This", "Is","My", "Args")
    val green = Colors.GREEN
    println("is it green ? ${green.isGreen()}")
}

fun collectionCreation(){
    val set = setOf(1, 7, 53)
    val hashSet = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 7, 53)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

    println(set.javaClass)      // class java.util.LinkedHashSet
    println(hashSet.javaClass)  // class java.util.HashSet
    println(list.javaClass)     // class java.util.ArrayList
    println(map.javaClass)      // class java.util.HashMap

    // Kotlin doesn’t have its own set of collection classes
    // Kotlin preserves Java collections to skip convertion when working with java code and adds functionality

    println(set.max())
    println(list.last())
    // default call to a function
    println(joinToString(list, "/", "{", "}"))
    // call with named arguments
    println(joinToString(list, separator="/", prefix="{","}" ))
    // print(list.joinToString( separator="/", prefix="}","{" ))

}

/**
 * A function to join collection to a sting
 * this function when converted to java would be static method of FunctionKt class (see 2 )
 * 1) To generate overloads in java we use @JvmOverloads Annotation
 * 2) To change class name we use @file:JvmName("FunctionInKotlinForJava")
 */
// @JvmOverloads
fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String{
    val result = StringBuilder(prefix)

    for((index, item) in collection.withIndex()){
        if(index > 0) result.append(separator)
        result.append(item)
    }
    result.append(postfix)
    return result.toString()
}


/**
 * By default, top-level properties, just like any other properties,
 * are exposed to Java code as accessor methods
 * (a getter for a val property and a getter/setter pair for a var property).
 */
val UNIX_LINE_SEPARATOR = "\n"
// on the other hand public static final field,
const val UNIX_LINE_SEPARATOR_2 = "\n"


/**
 * extension function is a simple thing: it’s a function that can be called as a member of a class but is defined outside of it
 * All you need to do is put the name of the class or interface that you’re extending before the name of the function you’re adding
 */
fun String.lastChar(): Char = this[this.length - 1]
// We can also emmit this keyword as in regular methods
fun String.preLastChar(): Char = get(length - 2)

// rewrite joinToString to extension function
/*

fun <T> Collection<T>.joinToString(separator: String, prefix: String, postfix: String): String =
    joinToString(this, separator, prefix, postfix)
*/


/**
 * extension functions cannot be overriden
 * Even though you can define extension functions with the same name
 * and parameter types for a base class and its subclass, the function that’s called depends on the declared
 * static type of the variable, not on the runtime type of the value stored in that variable.
  */
open class View{
    open fun open() = println("View open")
}

class Button: View(){
    override fun open() = println("Button is open")
}

fun View.showOff() = println("View Show off!")
fun Button.showOff() = println("Button Show off!")

fun testExtensionFunctionOverride(){
    val button: View = Button()
    button.open()

    /**
     * extension function is compiled to a static function in Java with the receiver as the first argument
     * If the class has a member function with the same signature as an extension function, the member function always takes precedence
     * View button = new Button()
     * FunctionsKt.showOff(button)
     */
    button.showOff() // View Show off as Button Static type is View
}

/**
 * Extension properties
 */

val String.lastChar: Char
    get() = get(length - 1)
// it is var because its properties can be modified
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) = setCharAt(length - 1, value)


fun testVarargsAndSpread(vararg args: String) {
    // vararg args is an array, so we need to unpack it using * spread operator
    val list = listOf("args", args) // [args, [Ljava.lang.String;@506e1b77]
    val list2 = listOf("args", *args) // [args, Hello, This, Is, My, Args]
    println(list)
    println(list2)
}

fun testInfixFunction(){
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
    val list = listOf(1 to "one", 7 to "seven", 53 to "fifty-three")
    // to is not an operator it is an infix function call
    // infix fun Any.to(other: Any) = Pair(this, other)

    val (number, name) = 1 to "one"
    //  1 to "one" is Pair(1, "one")
    // (number, name) is destructured from Pair
    for ((index, element) in list.withIndex()) {
        println("$index: number: ${element.first} name: ${element.second}")
    }
}

/**
 * Local Functions
 */

class User(val id: Int, val name: String, val email: String)

fun validate1(user: User){
    if(user.name.isEmpty()){
        throw IllegalArgumentException("Can't save user with id: ${user.id} with empty name")
    }
    if(user.email.isEmpty()){
        throw IllegalArgumentException("Can't save user with id: ${user.id} with empty email")
    }
    // Do smth.
}

// To avoid duplication we can use local function

fun validate2(user: User){
    fun validate(user: User, value: String, fieldName: String){
        if(value.isEmpty()){
            throw IllegalArgumentException("Can't save user with id: ${user.id} with empty $fieldName")
        }
    }
    validate(user, user.name, "name")
    validate(user, user.email, "email")
}

// But we can skip passing the user because local function has access to all parameters and variables of the enclosing function

fun validate3(user: User){
    fun validate( value: String, fieldName: String){
        if(value.isEmpty()){
            throw IllegalArgumentException("Can't save user with id: ${user.id} with empty $fieldName")
        }
    }
    validate( user.name, "name")
    validate( user.email, "email")
}

// And we can also make validate an extension function
fun User.validateBeforeSave(user: User){
    fun validate( value: String, fieldName: String){
        if(value.isEmpty()){
            throw IllegalArgumentException("Can't save user with id: ${user.id} with empty $fieldName")
        }
    }
    validate( user.name, "name")
    validate( user.email, "email")
}

/**
 *
 */
enum class Colors {
    RED, GREEN, BLUE, YELLOW;

    fun isGreen(): Boolean = this == Colors.GREEN
}

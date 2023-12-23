import java.io.File
import java.io.Serializable
import java.util.LinkedList
import javax.naming.Context
import javax.swing.text.AttributeSet

interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable.")
}

interface Focusable {
    fun setFocus(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")

    fun showOff() = println("I'm focusable!")
}

/**
 *  : is used for extends and implements
 *  Class can implement several interfaces and extend one class
 *  If two classes have default implementation of same function  we must override it in class
 */
class Button : Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

/**
 * In Kotlin classes are final as default, and we must open it and also open all methods we want to extend
 * access modifiers are open final abstract override
 */
open class RichButton : Clickable {
    fun disable() {}
    open fun animate() {}
    final override fun click() {}
}

class SuperRichButton : RichButton() {
    override fun animate() {
        println("Super Rich Button")
        super.animate()
    }
}

internal open class TalkativeButton : Focusable {
    private fun yell() {}
    protected fun whisper() {}
}

/*

fun TalkativeButton.giveSpeech(){
    yell()
    whisper()
}
*/

/**
 * In java inner classes has access to outer class instance and if we want to cut the ref we must make then static
 * In kotlin inner classes are by default static and if we want it to acces outer instance we must make it inner
 */

interface State : Serializable {
    var updateCount: Int
    fun update() {
        updateCount++
    }
}

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class Button2 : View {
    override fun getCurrentState(): State {
        return ButtonState();
    }

    override fun restoreState(state: State) { /*...*/
    }

    /**
     *  If we remove inner we would not have the  this@Button2 reference
     */
    inner class ButtonState : State {
        override var updateCount: Int = 0
        override fun update() {
            super.update()
            this@Button2.restoreState(this)
        }
    }
}

/**
 * Sealed Classes
 */

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
        /**
         * Here else is obligatory because Expr could be implemented in other classes
         */
        else -> throw IllegalArgumentException("Expr should be Num or Sum")
    }

/**
 * Solving the issue with sealed class
 * Sealed is the class that can not have future instances
 */

sealed class Expr2 {
    class Num(val value: Int) : Expr2()
    class Sum(val left: Expr, val right: Expr) : Expr2()
}

fun eval2(e: Expr2): Int =
    when (e) {
        is Expr2.Num -> e.value
        is Expr2.Sum -> eval(e.left) + eval(e.right)
    }

/**
 * Declaring classes
 */

// Simple data class (val nickname: String) this is the primary constructor
class User(val nickname: String)

// This is similar to
class User2 constructor(nickname: String) {
    val nickname: String

    // The init keyword introduces an initializer block. Such blocks contain initialization code that’s
    // executed when the class is created, and are intended to be used together with primary constructors
    init {
        this.nickname = nickname
    }
}

open class User3(_nickname: String) {
    val nickname = _nickname
}
// this three are the same

// If we want private constructor here is the code
class User4 private constructor(_nickname: String) {
    val nickname = _nickname
}

// If we extend
class User5(_nickname: String, val age: Int) : User3(_nickname)


/**
 * Secondary constructors
 */
//The majority of situations where you’d need overloaded constructors in Java are covered by Kotlin’s
// support for default parameter values and named argument syntax.

open class View3 {
    constructor(context: Context) {
        // logic
    }

    constructor(context: Context, attr: List<Int>) {
        // logic
    }
}

class Button3 : View3 {
    constructor(context: Context) : super(context)/*or this(context, LinkedList())*/ {
        //logic
    }

    constructor(context: Context, attr: List<Int>) : super(context, attr) {
        //logic
    }
}

// Implementing Properties

interface IUser {
    val nickname: String
}

class PrivateUser(override val nickname: String) : IUser

class SubscribedUser(val email: String) : IUser {
    override val nickname: String
        get() = email.substringBefore("@")
}

class FacebookUser(private val accountId: Int) : IUser {
    override val nickname: String = getFacebookNickName(accountId)

    private fun getFacebookNickName(accountId: Int) = "Custom nickname $accountId"
}

/**
 * property with private setter
 */

class LengthCounter {
    var count: Int = 0
        private set

    fun addWord(word: String) {
        count += word.length
    }
}
/**
 *  Classes have toString() equals and hashCode() methods
 *  toString is the same in java
 *  we can call equals or == . in Kotlin == is calling equals() underneath and we can check for only reference by calling ===
 *  If we add data before class all methods are generated by kotlin
 *  data class Client(val name: String, val postalCode: Int)
 *  data class also adds copy() method to copy an object and not modify it
 */

/**
 * In kotlin we can implement delegation of innerclass to by using by keyword
 */

class CountingSet<T>(val innerClass: MutableSet<T> = HashSet<T>()) : MutableSet<T> by innerClass {
    var objectsAdded = 0;

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerClass.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerClass.addAll(elements)
    }
}

/**
 * object keyword
 *  1 - let you create a class declaration and an instance (used for singletons ) cannot have constructors
 *      object instance is constructed at declaration time
 *      Object declarations can also inherit from classes and interfaces
 *      An object declaration in Kotlin is compiled as a class with a static field holding its single instance, which is always named INSTANCE
 *  2 - there is no static in Kotlin so in this case we can use top level function or objects as function don't have access to private fields
 *      for this case there is companion objects in kotlin
 *      it is a good choice for factory method
 *      companion objects can have names and if they don't we can add extension functions using
 */

object Payroll {
    val employees = arrayListOf<Person>()

    fun calculateSalary() {
        for (employee in employees) {
            // logic
        }
    }
}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(
            file2.path,
            ignoreCase = true
        )
    }
}

data class Person2(val name: String) {
    object NameComparator : Comparator<Person2> {
        override fun compare(p1: Person2, p2: Person2): Int =
            p1.name.compareTo(p2.name)
    }
}


class CompanionedClass {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

class User6 {
    var nickname: String

    constructor(email: String) {
       nickname = email.substringBefore("@")
    }
    constructor(id: Int){
        nickname = getFacebookNicknameFromId(id)
    }

    private fun getFacebookNicknameFromId(id: Int) = " Facebook Nickname $id"
}

// the same implementation by companion objects

class User7 private constructor(val nickname: String){
    companion object{
        fun getNickNameFromEmail(email: String): User7{
            return User7(email.substringBefore("@"))
        }
        fun getNickNameFromFacebook(id: Int): User7{
            return User7( "Facebook Nickname $id")
        }
    }
}

class WebService{
    companion object{

    }
}

fun WebService.Companion.fromJson(json: String): Person {
    return Person(json, true)
}

class WebService2{
    companion object ServiceCompanion{}
}

fun WebService2.ServiceCompanion.fromJson(json: String): Person {
    return Person(json, true)
}


fun testObjects() {
    Payroll.employees.add(Person("First", true))
    Payroll.calculateSalary()
    println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user")))
    var list = listOf<Person2>()
    list = list.sortedWith(Person2.NameComparator)
    CompanionedClass.bar()
    val user =  User7.getNickNameFromEmail("asd@asd.asd")
    val user2 =  User7.getNickNameFromFacebook(1)
}

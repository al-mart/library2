package lambdas

import com.sun.org.apache.xpath.internal.operations.Bool

data class Person(val name: String, val age: Int){
    val email: String
        get() {
            return "my@email.com"
        }
}

fun Person.someExtendedFunc(){}

fun topLevelFunc(){}

/**
 *  Lambda syntax is val sum = { x: Int, y : Int -> x + y } lambdas are always written inside { }
 *
 *  lambda immediately called { println(42) }()
 *  lambda call with run function  run {println(42)}
 *
 */
fun main(args: Array<String> ){
    var list = listOf(Person("Jane", 20), Person("Dave", 19));

    println(list.maxBy({ p: Person -> p.age })) // default lambda
    println(list.maxBy() { p: Person -> p.age } ) // if lambda is the last argument it can be moved out from ( )
    println(list.maxBy { p: Person -> p.age } ) //  ( ) can be ommited
    println(list.maxBy { it.age }) // it is the default generated parameter name can be used for arguments

    println(list.maxBy(Person::age)) // member reference

    val names = list.joinToString(separator = " ", transform = { p: Person -> p.name }) // lambda with named argument
    val names2 = list.joinToString( " ") { p: Person -> p.name } // lambda moved out of the  ( )

    // Multi expression lambda
    val sum = { x: Int, y: Int ->
            println("Computing the sum of $x and $y...")
            x + y
    }

    // field reference
    var ref = Person::email //  property ref
    var personRef = ::Person // constructor reference
    var toplevelFunc = ::topLevelFunc // top level func reference

    // Bound references
    // Kotlin 1.0
    val p = Person("Dima", 10)
    val extendedFuncRef = Person::someExtendedFunc
    extendedFuncRef(p)
    // From Kotlin 1.1
    val extendedFuncRef2 = p::someExtendedFunc
    extendedFuncRef2()
}

/**
 * Variables in scope
 * in kotlin we can access all variables from lambda and also modify it
 */
fun printProblemCount(responses: Collection<String>){
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if( it.startsWith("4")) clientErrors++
        else serverErrors++
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

/**
 * Collections functional api
 * filter
 * map
 * all
 * any
 * count
 * find
 * groupBy
 * flatMap
 * flatten
 */

fun collectionFunctions(){
    var people = listOf(Person("Jane", 20), Person("Dave", 19));

    people.filter { it.age > 30 }.map(Person::name)

    val canBeInClub27 = { p: Person -> p.age <= 27 }
    println(people.all(canBeInClub27))
    println(people.count(canBeInClub27))
    // Alternative to count can be
    println(people.filter(canBeInClub27).size) // But this would be costly cause filter will create a new list

    println(people.find(canBeInClub27))
    val map: Map<Int, List<Person>> =  people.groupBy { it.age }

    class Book(val title: String, val authors: List<String>)
    val books = listOf<Book>()
    books.flatMap { it.authors }.toSet()
    val strings = listOf("abc", "def")

    val strings2 = listOf("abc", "def")
    println(strings2.flatMap { it.toList() })   //  [a, b, c, d, e, f]


}

/**
 * Sequences
 */

fun testSequence(){
    listOf<Person>().map(Person::name).filter { it.startsWith("A") } // creates second eager list by map and third by filter
    listOf<Person>().asSequence().map(Person::name).filter { it.startsWith("A") }.toList() // optimisation is using sequences

    // TO illustrate an example lets find an item with age higher than 10
    listOf(1, 2, 3, 4)
        .map { it * it }.find { it > 3 } // here we would have 1 2 3 4 - > 1 4 9 16 -> 1 4 end we found the item    eager
    listOf(1, 2, 3, 4).asSequence()
        .map { it * it }.find { it > 3 } // here we would have 1 -> 1 -> 2 -> 4 end we found the item               lazy

}

/**
 * With and apply
 */

fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}
// refactor using with
// with is a lib function that takes two args (reciever, lambda)
fun alphabet2(): String = with(StringBuilder()){
    for (letter in 'A'..'Z') {
        append(letter)
    }
    this.append("\nNow I know the alphabet!")
    return this.toString()
}

// refactor with apply
// apply always returns the receiver
fun alphabet3(): String = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

// buildstring method
fun alphabet4() = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}

fun main(args: Array<String>){
    println("Hello Kotlin!")
    println("Second ln!")

    println("Hello, ${if (args.isNotEmpty() ) args[0] else "someone"}!")
    PersonTest().testPerson()
}

// Block body function
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

// Expression body function
fun max2(a: Int, b: Int): Int = if (a > b) a else b
// Return type is infered
fun max3(a: Int, b: Int) = if (a > b) a else b


fun testAssignment(){
    val a: Int = 8;
    val c = a;
    // This variable must either have a type annotation or be initialized
    // val d;
    val d: Int;
    // or
    val e = 10;
    // Assignments are not expressions, and only expressions are allowed in this contex
    // var b = c = a;
}

class Person(val name: String, var isMarried: Boolean)

class PersonTest{
    fun testPerson(){
        val person = Person("Jerry", true)
        println("Person name is: ${person.name}")
        person.isMarried = false
        println("${person.name} ${ if (person.isMarried) "Is Married!" else "Is Not Married"}")
    }
}

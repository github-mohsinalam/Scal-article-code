import scala.annotation.tailrec
object Recursion {


 /**
  * Computes the factorial of a non-negative integer using traditional recursion.
  *
  * @param x The non-negative integer for which factorial is computed.
  * @return The factorial of the given integer.
  */
 def factorial(x: Int): Int = {
   // Ensure that x is non-negative
   require(x >= 0, "x must be greater than or equal to zero!")


   // Base case
   if x < 1 then 1
   else
   // Recursive step
     x * factorial(x - 1)
 }


 /**
  * Computes the factorial of a non-negative integer using tail recursion.
  *
  * @param x The non-negative integer for which factorial is computed.
  * @return The factorial of the given integer.
  */
 def factorialTail(x: Int): Int = {
   require(x >= 0, "x must be greater than or equal to zero!")


   /**
    * Helper function for tail-recursive factorial computation.
    *
    * @param n Accumulator for intermediate results.
    * @param x Current value.
    * @return The result of factorial computation.
    */
   @tailrec
   def factorialHelper(n: Int, x: Int): Int = {
     // Base case
     if x <= 1 then n
     else
     // Purely recursive call
       factorialHelper(n * x, x - 1)
   }


   factorialHelper(1, x)
 }


 /**
  * Checks whether parentheses in a given list are balanced.
  *
  * @param chars The list of characters containing parentheses.
  * @return True if parentheses are balanced, false otherwise.
  */
 def balance(chars: List[Char]): Boolean = {
   /**
    * Helper function for checking balanced parentheses.
    *
    * @param chars      The list of characters.
    * @param openCount  The count of open parentheses.
    * @return True if parentheses are balanced, false otherwise.
    */
   @tailrec
   def balanceHelper(chars: List[Char], openCount: Int): Boolean =
     if chars.isEmpty then openCount == 0
     else if chars.head.equals(')') && openCount == 0 then false
     else if chars.head.equals('(') then balanceHelper(chars.tail, openCount + 1)
     else if chars.head.equals(')') && openCount > 0 then balanceHelper(chars.tail, openCount - 1)
     else balanceHelper(chars.tail, openCount)


   balanceHelper(chars, 0)
 }


 /**
  * Computes the sum of a function over a range using tail recursion.
  *
  * @param f The function to sum.
  * @param a The start of the range.
  * @param b The end of the range.
  * @return The sum of applying the function over the specified range.
  */
 def sum(f: Int => Int, a: Int, b: Int): Int =
   /**
    * Helper function for tail-recursive sum computation.
    *
    * @param a   Current value.
    * @param acc Accumulator for the sum.
    * @return The result of sum computation.
    */
   @tailrec
   def sumHelper(a: Int, acc: Int): Int =
     if a > b then acc
     else sumHelper(a + 1, acc + f(a))


   sumHelper(a, 0)




 def main(args: Array[String]): Unit = {
   println(balance("(if (zero? x) max (/ 1 x))".toList))
   println(balance("())(".toList))
   println(sum(x => x*x , 1, 5))  //sum of squares of numbers in 1 to 5
 }
}




import scala.util.{Failure, Random, Success, Try, Using}
import scala.io.Source
import scala.collection.mutable


object TryDemo extends App{


  /**
   * Reads a file and returns a Map to simulate a database
   * @param path Path of the file
   * @return Map of users with its key as userid and value as a Tuple2 with its
   *         first element as name and second as age
   */
  def readFile(path : String) : Map[Int,(String,Int)] = {
    val userData = mutable.Map[Int, (String,Int)]()
    Using.resource(Source.fromFile(path)){source =>
      for (line <- source.getLines()) {
        val userInfo = line.split(",")
        userData.addOne(userInfo(0).toInt -> (userInfo(1),userInfo(2).toInt))
      }
    }

    userData.toMap
  }

  def displayUserInfo(userId: Int, database: Map[Int, (String, Int)]): Try[String] =
    Try(database(userId))
      .flatMap(parseUserInfo)
      .flatMap(formatUserInfo)



  def parseUserInfo(userInfo : (String,Int)) : Try[(String,String,Int)] = {
    val names = userInfo._1.split("_").map(_.capitalize)
    if (names.length < 2) Failure(new Exception("Incorrect user name format"))
    else Success(names(0),names(1),userInfo._2)
  }

  def formatUserInfo(details: (String, String, Int)): Try[String] =
    if (details._3 < 0) Failure(new Exception("Age must be Positive"))
    else Success(s"FirstName = ${details._1} ; LastName = ${details._2} ; Age = ${details._3}")





  
  val database = readFile("specify the path of user_info.txt")  // download user_info.txt
  //test1
  //User do not exist in the database
  val test1 = displayUserInfo(25,database)

  println(test1)

  //test2
  //user exist in the database

  val test2 = displayUserInfo(20, database)

  println(test2)

  //test3
  //user's age is negative
  val test3 = displayUserInfo(19, database)

  println(test3)

  //test4
  //user's name is in incorrect format

  val test4 = displayUserInfo(4,database)

  println(test4)

  

  /*
    Some key points from above example
      1. We specified the return type of each method as Try[T] instead of just T.
         This not only allows us to handle exceptions in a functional way but also
         enables us to use the methods directly in flatMap as lambda expressions.

      2. Each computation (like parsing or formatting) can be applied only
         if the previous computation was successful.

      3. If an error occurs at any point, it is automatically propagated as a Failure
         to the final result without executing the remaining logic.

      4. You don't need explicit error handling for each function call,
         the chaining takes care of it, passing Success values to the next step or stopping with a Failure.

   */

}

import org.apache.spark.sql.functions.{col}

val numbers = List(1,2,3,4,5)

val aBinaryOperator : (Int,Int) => Int = (a,b) => {
  println(s"a = $a ; b = $b")
  a+b
}

val sum = numbers.reduce(aBinaryOperator)




val data1 = Seq(
  (1,"aaa","aa","a"),
  (2,"bbb","bb","b"),
  (3,"ccc","cc","c")
)


val data2 = Seq(
  ("2024-11-25","aaa","aa","a",100),
  ("2024-11-25","bbb","bb","b",120),
  ("2024-11-26","aaa","aa","a",140),
  ("2024-11-26","ccc","cc","c",90),
  ("2024-11-26","bbb","bb","b",110)
)




val df1 = data1.toDF("UnitId","UnitA","UnitB","UnitC")

val df2 = data2.toDF("Date","Level1","Level2","Level3","Spend")



//APPROACH : 1

val joinCondition= (df1.col("UnitA") === df2.col("Level1")) and (df1.col("UnitB") === df2.col("Level2")) and (df1.col("UnitC") === df2.col("Level3"))
val resultDF = df2.join(df1,joinCondition,"left").select(
  col("Date"),
  col("UnitId"),
  col("Spend")
)

resultDF.show




//APPROACH : 2
val correspondingColumns = Seq(
  ("Level1","UnitA"),
  ("Level2","UnitB"),
  ("Level3","UnitC")
)

val joinCondition2 = correspondingColumns
  .map{case(leftCol , rightCol) => df2.col(leftCol) === df1.col(rightCol)}
  .reduce((a,b) => a and b)

val resultDf = df2.join(df1,joinCondition2,"left").select(
  col("Date"),
  col("UnitId"),
  col("Spend")
)

resultDf.show

package io.github.rollenholt.scala.email.sender

/**
 * @author rollenholt 
 */
case class EmailMessage(title:String, content:String, from:String, to:List[String], cc:List[String], bcc:List[String]) {}

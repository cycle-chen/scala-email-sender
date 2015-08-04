import akka.actor.{ActorRef, Props, ActorSystem}
import io.github.rollenholt.scala.email.sender.{EmailMessage, EmailSenderActor}

/**
 * @author rollenholt 
 */
object Test {
  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem("scalaEmailSender")
    val actor: ActorRef = system.actorOf(Props[EmailSenderActor], name = "master")

    val message: EmailMessage = EmailMessage("test", "from rollen", "no-replay@qq.com", List("191430791@qq.com"), List(), List())
    for( index <- 1 to 200){
      actor ! message
    }
//    system terminate
  }
}

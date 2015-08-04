package io.github.rollenholt.scala.email.sender

import akka.actor.SupervisorStrategy.{Escalate, Resume}
import akka.actor._
import akka.event.{Logging, LoggingAdapter}
import akka.routing.{DefaultResizer, RoundRobinPool}

import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * @author rollenholt 
 */
class EmailSenderActor extends Actor{
  private val logger: LoggingAdapter = Logging(context.system, context.self.getClass)

  val resizer = DefaultResizer(lowerBound = 4, upperBound = 50)
  val worker: ActorRef = context.actorOf(RoundRobinPool(30, Some(resizer))
    .withSupervisorStrategy(EmailSenderActor.buildSupervisorStrategy())
    .props(Props[EmailSendWorker]))

  override def receive: Receive = {
    case emailMessage:EmailMessage => {
      logger.debug("receive a email message : {}", emailMessage)
      worker ! emailMessage
    }
  }

  override val supervisorStrategy = EmailSenderActor.buildSupervisorStrategy()

}

object EmailSenderActor{
  def buildSupervisorStrategy() = {
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: Exception => Escalate
    }
  }
}

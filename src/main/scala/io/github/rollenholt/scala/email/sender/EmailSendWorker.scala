package io.github.rollenholt.scala.email.sender

import java.util.Properties
import javax.mail.internet.MimeMessage

import akka.actor.Actor
import akka.event.{Logging, LoggingAdapter}
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.mail.javamail.{JavaMailSenderImpl, MimeMessageHelper}

/**
 * @author rollenholt 
 */
class EmailSendWorker extends Actor{
  private val logger: LoggingAdapter = Logging(context.system, context.self.getClass)
  override def receive: Receive = {
    case emailMessage:EmailMessage => {
      logger.debug("do send")
        sendEmail(emailMessage)
    }
  }

  def sendEmail:(EmailMessage) => Unit = (emailMessage:EmailMessage) => {
    val javaMailSenderImpl: JavaMailSenderImpl = EmailSendWorker.initJavaMailSenderImpl()
    val mimeMessage: MimeMessage = javaMailSenderImpl.createMimeMessage
    val helper: MimeMessageHelper = new MimeMessageHelper(mimeMessage)
    helper.setTo(emailMessage.to.toArray)
    helper.setFrom(emailMessage.from)
    helper.setCc(emailMessage.cc.toArray)
    helper.setBcc(emailMessage.bcc.toArray)
    mimeMessage.setSubject(emailMessage.title, "UTF-8")
    mimeMessage.setContent(emailMessage.content, "text/html; charset=\"UTF-8\"")
    mimeMessage.setHeader("Content-Type", "text/html; charset=\"UTF-8\"")
    javaMailSenderImpl.send(mimeMessage)
    logger.info("send email ok")
  }
}

object EmailSendWorker{
  def initJavaMailSenderImpl:() => JavaMailSenderImpl = () => {
    val mailSender: JavaMailSenderImpl = new JavaMailSenderImpl()
    val conf: Config = ConfigFactory.load("emailConfig.properties")
    mailSender.setHost(conf.getString("host"))
    mailSender.setPort(conf.getInt("port"))
    mailSender.setUsername(conf.getString("username"))
    mailSender.setPassword(conf.getString("password"))
    val properties: Properties = new Properties()
    properties.setProperty("mail.smtp.auth", "true")
    mailSender.setJavaMailProperties(properties)
    mailSender
  }

}

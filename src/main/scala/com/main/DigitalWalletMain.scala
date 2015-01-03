package main.scala.com.main

import org.springframework.boot.SpringApplication
import java.util.HashMap
import scala.beans.BeanProperty
import main.scala.com.model.User

object DigitalWalletMain {
  
  def main(args: Array[String]) {
  SpringApplication.run(classOf[Services]);
}
}
package main.scala.com.main

import scala.beans.BeanProperty
import java.util.HashMap
import main.scala.com.model.User

object DigitalWallet
  {
    @BeanProperty
  var userList = new HashMap[String,User]();
  
  @BeanProperty
  var userIdCount = 10000;
  
  @BeanProperty
  var idCardCount = 10000;
  
  @BeanProperty
  var webLoginCount = 10000;
  
  @BeanProperty
  var bankAccntCount = 10000;
  
  @BeanProperty
  var appVersion : String ="v1";
  }
package main.scala.com.main.services
import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import main.scala.com.model.User
import java.util.ArrayList
import java.util.HashMap
import main.scala.com.model.WebLogin
import main.scala.com.model.BankAccount
import main.scala.com.model.IDCard
import javax.validation.Valid
import org.springframework.validation.BindingResult
import main.scala.com.model.IDCard
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import main.scala.com.model.User
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.validation._
import java.util.Date
import main.scala.com.main.DigitalWallet
import main.scala.com.model.IDCard
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.GregorianCalendar

@RestController
@RequestMapping(value= Array("/digiwallet"+"/v1"))
class IDCardService {
  
	@RequestMapping(value= Array("/users/{user_id}/idcards"), method=Array(RequestMethod.POST),
	    produces = Array("application/json"), consumes = Array("application/json"))
	@ResponseBody 
    def createIDCard(@PathVariable("user_id")userId:String, 
       @RequestBody @Valid idcard: IDCard): ResponseEntity[IDCard] = {
	 var nullId : IDCard = null; 
	 var user_id : User = DigitalWallet.getUserList().get(userId);
	 if(user_id!=null)
	 {
	   var count : Int = DigitalWallet.getIdCardCount()+1
	   var card_id:String  = "DWS_IDC-" + count
	   idcard.setCard_id(card_id)
	   
	  // var calendar : Calendar = new GregorianCalendar(2017,12,31,22,59,0);	
	   //var d : String = new SimpleDateFormat("yyyy-MM-dd") format idcard.getExpiration_date()
	   //idcard.setExpiration_date(d)
	   DigitalWallet.setIdCardCount(count)    
	   
	   user_id.getId_cardList().put(card_id,idcard);
	   return new ResponseEntity[IDCard](idcard,HttpStatus.CREATED);
	 }
	 else
	 {
	   return new ResponseEntity[IDCard](nullId,HttpStatus.BAD_REQUEST);
	 }
     }
	
	@RequestMapping(value= Array("/users/{user_id}/idcards"), method=Array(RequestMethod.GET),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
    def lisAllIDCards(@PathVariable("user_id")userId:String): ResponseEntity[HashMap[String,IDCard]] = {
	  
	var nullId : HashMap[String,IDCard] = null;  
    var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id !=null)
    {
    return new ResponseEntity[HashMap[String,IDCard]](user_id.getId_cardList(),HttpStatus.OK)  
    }
    else
    {
      return new ResponseEntity[HashMap[String,IDCard]](nullId,HttpStatus.BAD_REQUEST)
    }
    
    
     }
	@RequestMapping(value= Array("/users/{user_id}/idcards/{card_id}"), method=Array(RequestMethod.DELETE),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.NO_CONTENT)
    def deleteIDCard(@PathVariable("user_id")userId:String,
        @PathVariable("card_id") cardId : String): ResponseEntity[String] = {
	  
    var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id!=null)
    {
     var card :IDCard = user_id.getId_cardList().get(cardId);
     if(card!=null)
     {
       user_id.getId_cardList().remove(cardId)
       return new ResponseEntity[String]("Card Deleted",HttpStatus.NO_CONTENT)
     }
     else
     {
       return new ResponseEntity[String]("Invalid Card.. Please enter correct card details",HttpStatus.BAD_REQUEST)
     }
    }
    else
    {
      return new ResponseEntity[String]("Invalid User.. Please enter correct user details",HttpStatus.BAD_REQUEST)
    }
    
     }
	
}
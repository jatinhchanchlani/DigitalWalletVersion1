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
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount

@RestController
@RequestMapping(value= Array("/digiwallet"+"/v1"))
class BankAccountService {

	@RequestMapping(value= Array("/users/{user_id}/bankaccounts"), method=Array(RequestMethod.POST),
	    produces = Array("application/json"), consumes = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.CREATED)
    def createBankAccount(@PathVariable("user_id")userId:String
        ,@RequestBody @Valid bankAccnt: BankAccount): ResponseEntity[BankAccount] = {
	  
	var user_id : User = DigitalWallet.getUserList().get(userId);
	var nullId : BankAccount = null;
	if(user_id!=null)
	{
	var count : Int = DigitalWallet.getBankAccntCount()+1  
	var aacntId :String  = "DWS_ACC-" + count
    bankAccnt.setBa_id(aacntId)
    DigitalWallet.setBankAccntCount(count)    
    user_id.getAccnt().put(aacntId, bankAccnt)
    return new ResponseEntity[BankAccount](bankAccnt,HttpStatus.CREATED)
	}
	else
	{
	return new ResponseEntity[BankAccount](nullId,HttpStatus.BAD_REQUEST)  
	}
  }
	
	@RequestMapping(value= Array("/users/{user_id}/bankaccounts"), method=Array(RequestMethod.GET),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
    def lisAllBankAccnts(@PathVariable("user_id")userId:String): ResponseEntity[HashMap[String,BankAccount]] = {
	  
    var user_id : User = DigitalWallet.getUserList().get(userId);
    var nullid : HashMap[String,BankAccount] = null;
    if(user_id!=null)
    {
      return new ResponseEntity[HashMap[String,BankAccount]](user_id.getAccnt(),HttpStatus.OK)
    }
    else
    {
      return new ResponseEntity[HashMap[String,BankAccount]](nullid,HttpStatus.BAD_REQUEST)
    }
  }
	@RequestMapping(value= Array("/users/{user_id}/bankaccounts/{ba_id}"), method=Array(RequestMethod.DELETE))
	@ResponseBody 
	@ResponseStatus(HttpStatus.NO_CONTENT)
    def deleteBankAccnt(@PathVariable("user_id")userId:String,
        @PathVariable("ba_id") ba_id : String): ResponseEntity[String] = {
	  
    var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id!=null)
    {
     var accnt : BankAccount = user_id.getAccnt().get(ba_id)
     if(accnt!=null)
     {
      user_id.getAccnt().remove(ba_id)
      return new ResponseEntity[String]("Bank Account Deleted",HttpStatus.NO_CONTENT); 
     }
     else
     {
       return new ResponseEntity[String]("Invalid Bank Account Id.. Please enter correct bank account details",HttpStatus.BAD_REQUEST);
       
     }
    }
    else
    {
      return new ResponseEntity[String]("Invalid User.. Please enter correct user details",HttpStatus.BAD_REQUEST);
      
    }
	}    
}
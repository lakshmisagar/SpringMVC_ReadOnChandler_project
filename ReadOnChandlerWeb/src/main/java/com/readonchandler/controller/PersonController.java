package com.readonchandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.readonchandler.spring.dao.PersonDAO;
import com.readonchandler.spring.model.Person;

@Controller
public class PersonController {
	 
	private PersonDAO personservice;
	@Autowired(required = true)
	@Qualifier(value="personService")
	public void setPersonservice(PersonDAO ps) {
		this.personservice = ps;
	}
	
	//map method to the value path
	@RequestMapping(value="/persons", method=RequestMethod.GET)
	public String listPersons(Model model){
		model.addAttribute("person",new Person());
		model.addAttribute("listPersons", this.personservice.listPersons());
		return "person";
		
	}
	
	
	//For add and update person both
		@RequestMapping(value= "/person/add", method = RequestMethod.POST)
		public String addPerson(@ModelAttribute("person") Person p){
			if(p.getId() == 0){
				//new person, add it
				this.personservice.addPerson(p);
			}else{
				//existing person, call update
				this.personservice.updatePerson(p);
			}
			return "redirect:/persons";
		}

		@RequestMapping("/remove/{id}")
	    public String removePerson(@PathVariable("id") int id){
			
	        this.personservice.removePerson(id);
	        return "redirect:/persons";
	    }
		
	/*public ModelAndView helloworld(){
		String message = "<br><div style ='text-align:center;'>"+
						 "<h3>****** Hello World, Spring MVC Example</h3>"
						 + "This message is coming from CrunchifyHelloWorld.java "
						 + "**********</div><br><br>";
		return new ModelAndView("welcome","message",message);
	}
	*/
}

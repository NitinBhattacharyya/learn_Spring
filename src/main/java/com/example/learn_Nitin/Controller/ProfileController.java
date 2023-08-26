package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Address;
import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.model.Profile;
import com.example.learn_Nitin.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ProfileController {
    @Autowired
    PersonRepository personRepository;
    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(HttpSession session)
    {
        Person person=(Person)session.getAttribute("loggedInPerson");
        Profile profile=new Profile();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        if(person.getAddress()!=null && person.getAddress().getAddressID()>0)
        {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView=new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }
    @RequestMapping(value="/updateProfile",method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute("profile")Profile profile, Errors errors,HttpSession session)
    {
        if(errors.hasErrors())
        {
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress() ==null || !(person.getAddress().getAddressID()>0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        Person savedPerson = personRepository.save(person);
        session.setAttribute("loggedInPerson", savedPerson);
        return "redirect:/displayProfile";
    }
}
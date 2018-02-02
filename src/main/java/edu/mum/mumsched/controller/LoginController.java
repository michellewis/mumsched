package edu.mum.mumsched.controller;


import edu.mum.mumsched.model.User;
import edu.mum.mumsched.model.UserForm;
import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

	@Autowired
	private UserService<User, UserForm> userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		UserForm userForm = new UserForm();
		modelAndView.addObject("user", userForm);
		modelAndView.setViewName("users/registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid UserForm userForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		UserForm userExists = userService.findByEmail(userForm.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user", "There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("users/registration");
		} else {
			userService.save(userForm);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("users/registration");
		}
		return modelAndView;
	}

	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserForm userForm = userService.findByEmail(auth.getName());
		modelAndView.addObject("message", "Welcome " + userForm.getFirstName() + " " + userForm.getLastName() + " (" + userForm.getEmail() + ")");
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin(){
		return "admin/admin";
	}

	@RequestMapping(value="/faculty", method = RequestMethod.GET)
	public String faculty(){
		return "faculty/faculty";
	}
	@RequestMapping(value="/403", method = RequestMethod.GET)
	public String accessDenied(){
		return "403";
	}

}

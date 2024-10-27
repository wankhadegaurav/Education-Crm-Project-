package in.sp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import in.sp.main.entities.Course;
import in.sp.main.services.CourseService;

@Controller
public class AdminController 
{
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/adminLogin")
	public String openAdminLoginPage()
	{
		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String adminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass, Model model)
	{
		if(aemail.equals("admin@gmail.com") && apass.equals("admin123"))
		{
			return "admin-profile";
		}
		else
		{
			model.addAttribute("errorMsg", "Invalid email id or password");
			return "admin-login";
		}
	}
	
	@GetMapping("/courseManagement")
	public String openCourseManagementPage(Model model,
					@RequestParam(name="page", defaultValue = "0") int page,
					@RequestParam(name="size", defaultValue = "4") int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Course> coursesPage = courseService.getAllCourseDetailsByPagination(pageable);
		
		model.addAttribute("coursesPage", coursesPage);
		
		return "course-management";
	}
	
	//-------------admin logout------------------------
	@GetMapping("/adminLogout")
	public String adminLogout(SessionStatus sessionStatus)
	{
		sessionStatus.setComplete();
		return "admin-login";
	}
}

package com.rapl.carros.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @GetMapping()
    public String get() {
        return "API dos Carros";
    }
    
    // representa o usuário logado na aplicação
    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }
    
    @Value("${app.message}")
	private String welcomeMessage;
	
	@GetMapping("/welcome")
	public String getDataBaseConnectionDetails() {
		return welcomeMessage;
	}

//    @PostMapping()
//    public String post() {
//        return "Post......";
//    }
//    
//    @PutMapping()
//    public String put() {
//        return "Put......";
//    }
//    
//    @DeleteMapping
//    public String delete() {
//    	return "Delete.....";
//    }
}

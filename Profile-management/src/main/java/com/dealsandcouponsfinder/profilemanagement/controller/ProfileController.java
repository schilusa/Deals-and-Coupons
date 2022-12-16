package com.dealsandcouponsfinder.profilemanagement.controller;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dealsandcouponsfinder.profilemanagement.config.JwtUtil;
import com.dealsandcouponsfinder.profilemanagement.model.AuthenticationRequest;
import com.dealsandcouponsfinder.profilemanagement.model.AuthenticationResponse;
import com.dealsandcouponsfinder.profilemanagement.model.Coupon;
//import com.dealsandcouponsfinder.profilemanagement.exception.ProfileRequestException;
import com.dealsandcouponsfinder.profilemanagement.model.Credentials;
import com.dealsandcouponsfinder.profilemanagement.model.Profile;
import com.dealsandcouponsfinder.profilemanagement.model.User1;
import com.dealsandcouponsfinder.profilemanagement.repository.UserRepo;
import com.dealsandcouponsfinder.profilemanagement.service.MyUserDetailsService;
import com.dealsandcouponsfinder.profilemanagement.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	ProfileService profileService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private UserRepo repo;
	
	
	@PostMapping("/seecoupon")
    public List<Coupon> getAllDetails(){
		return Coupon.getAllCoupons();
	}
	
	@PostMapping("/add")
	public Profile addProfile(@RequestBody Profile pro) {
		Profile profile = profileService.save(pro);
		return profile;
	}

	@GetMapping("/find/{id}")
	public Optional<Profile> searchProfileById(@PathVariable("id") String id) {
		Optional<Profile> profile = profileService.findById(id);
		return profile;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteProfileById(@PathVariable("id") String id) {
		String result = profileService.deleteById(id);
		return result;
	}

	@GetMapping("/findall")
	public List<Profile> findProfiles() {
		return (List<Profile>) profileService.findAll();
	}

	@PutMapping("/update")
	public Profile updateProfile(@RequestBody Profile pro) {
		Profile profile = profileService.save(pro);
		return profile;
	}

	@PostMapping("/login")
	public String loginProfile(@RequestBody Credentials cred) {
		boolean login = profileService.login(cred);
		if (login) {
			return "Login is successful";
		} else {
			return "Login is failed";
		}
	}

	@PostMapping("/logout")
	public String logoutProfile(@RequestBody Credentials cred) {
		boolean logout = profileService.logout(cred);
		if (logout) {
			return "Logout is successful";
		} else {
			return "Logout is failed";
		}

	}
	/*-----------------------------------security----------------------------------*/
	@PostMapping("/reg")
	private ResponseEntity<?> subscribe(@RequestBody AuthenticationRequest request)
	{
       // System.out.println("shari");
		String username = request.getUsername();
		String password = request.getPassword();

		User1 model = new User1();
		model.setUsername(username);
		model.setPassword(password);
		try {
			repo.save(model);
		}
		catch (Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("Error while subsribing the user with username " + username));
		//System.out.println(e);
		}
		    return ResponseEntity.ok(new AuthenticationResponse("client subscribed with username " + username));
	}
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try
		{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
	}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}


}



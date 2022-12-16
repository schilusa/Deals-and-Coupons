package com.dealsandcouponsfinder.profilemanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dealsandcouponsfinder.profilemanagement.exception.ProfileRequestException;
import com.dealsandcouponsfinder.profilemanagement.model.Credentials;
import com.dealsandcouponsfinder.profilemanagement.model.Profile;
import com.dealsandcouponsfinder.profilemanagement.repository.CredentialsRepository;
import com.dealsandcouponsfinder.profilemanagement.repository.ProfileRepository;

@SpringBootTest
public class ProfileServiceTest {

	@Autowired
	ProfileService profileService;

	@MockBean
	private ProfileRepository profileRepository;
	@MockBean
	private CredentialsRepository credentialsRepository;

	@Test
	void saveTest() {
		Profile pro = new Profile("chilusani@gmail.com", "shari", "6303990331", "peddamupparam");
		when(profileRepository.save(pro)).thenReturn(pro);
		assertEquals(pro, profileService.save(pro));
	}
	
	@Test 
	 public void deleteByIdTest() throws ProfileRequestException {
		when (profileRepository.findById("chilusani@gmail.com")).thenReturn(Optional.of(new Profile("chilusani@gmail.com", "shari", "6303990336", "peddamupparam")));
		doNothing().when(profileRepository).deleteById("chilusani@gmail.com");
		assertEquals(profileService.deleteById("chilusani@gmail.com"), "Id chilusani@gmail.com deleted!");
	}	
	
	@Test
	public void findByIdTest() throws ProfileRequestException {
		when (profileRepository.findById("chilusani@gmail.com")).thenReturn(Optional.of(new Profile("chilusani@gmail.com", "shari", "6303990336", "peddamupparam")));
		Optional<Profile> profile = profileService.findById("chilusani@gmail.com");
		assertEquals("chilusani@gmail.com",profile.get().getEmailId());
		assertEquals("shari",profile.get().getFullName());
		assertEquals("6303990336",profile.get().getMobileNo());
		
		
	}

	
}

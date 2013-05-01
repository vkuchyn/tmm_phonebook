package ua.com.kuchyn.tmm.phonebook.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {

    public String generateId() {
	return UUID.randomUUID().toString();
    }

}

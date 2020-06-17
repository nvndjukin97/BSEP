package bsep.service;

import bsep.model.Authority;

import java.util.List;

public interface AuthorityService {
	List<Authority> findById(Long id);
	List<Authority> findByname(String name);
}

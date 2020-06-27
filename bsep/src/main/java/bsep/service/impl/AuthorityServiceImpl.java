package bsep.service.impl;

import bsep.model.Authority;
import bsep.repository.AuthorityRepository;
import bsep.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

  @Autowired
  private AuthorityRepository authorityRepository;

  @Override
  public List<Authority> findById(Long id) {
    Authority auth = this.authorityRepository.getOne(id);
    List<Authority> auths = new ArrayList<>();
    auths.add(auth);
    return auths;
  }

  @Override
  public List<Authority> findByname(String name) {
    Authority auth = this.authorityRepository.findByName(name);
    List<Authority> auths = new ArrayList<>();
    auths.add(auth);
    return auths;
  }


}

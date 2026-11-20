package com.library.managament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.managament.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

	boolean existsByEmail(String email);
	
	boolean existsByEmailAndIdNot(String email, Long id);
}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Category;

// Không cần annotation Repository vì đã kế thừa JpaRepository
// class JpaRepository này đã có anotation @Repository rồi nên khi sử dụng @Autowired thì sẽ tìm đến Bean JPARepository
public interface CategoryRepository extends JpaRepository<Category, String> {
}

package com.shiy.springmvc.data;


import com.shiy.springmvc.Spitter;

public interface SpitterRepository {

  Spitter save(Spitter spitter);
  
  Spitter findByUsername(String username);

}

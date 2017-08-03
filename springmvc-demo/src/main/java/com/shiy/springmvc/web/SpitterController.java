package com.shiy.springmvc.web;

import com.shiy.springmvc.Spitter;
import com.shiy.springmvc.data.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm() {
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
      @Valid SpitterForm spitterForm, //@Valid 是对spitter进行检验
//      Errors errors, Model model) {//如果检验出错的话，可以通过errors对象拿到错误信息。Errors参数必须紧跟带有@Valid注解的参数后面
      Errors errors, RedirectAttributes model) {//如果检验出错的话，可以通过errors对象拿到错误信息。Errors参数必须紧跟带有@Valid注解的参数后面
    if (errors.hasErrors()) {//检查是否有错误
      return "registerForm";
    }

    Spitter spitter = spitterForm.toSpitter();
    spitterRepository.save(spitter);


    /**
     * 重定向的问题
     *
     *  传递普通的基本类型的数据值
     *
     *  这里不用String连接，是出于安全考虑。
     *  比如sql这样的，防止sql注入
     *  当username填充到URL模板中时，不安全字符会进行转义，这样会更加安全
     *
     *  这里的spitterId没有填充到URL中，会以查询参数的形式添加到重定向的URl中
     *  /spitter/{usernmae}?spitterId={spitterId}
     */
//    return "redirect:/spitter/" + spitter.getUsername();
    model.addAttribute("username", spitter.getUsername());
    model.addAttribute("spitterId", spitter.getId());

    /**
     *  传递对象
     */
    model.addFlashAttribute("spitter", spitter);
    return "redirect:/spitter/{usernmae}";
  }
  
  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {
    Spitter spitter = spitterRepository.findByUsername(username);
    model.addAttribute(spitter);
    return "profile";
  }
  
}

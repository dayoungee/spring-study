package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // 홈페이지 url
    public String hello(Model model){ // 톰캣내장서버에서 모델을 던져줌
        model.addAttribute("data","hello!!"); // 모델에 데이터를 태움
        return "hello"; // hello.html로 보낸다
    }
    // MVC 방법
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    // API방법 1)
    @GetMapping("hello-string")
    @ResponseBody // http 바디부에 리턴 데이터를 넣어주겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // html이 아닌 데이터 자체를 준다.
    }
    // 2)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체를 반환하면 제이슨 형태로 데이터를 던져줌
    }


    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

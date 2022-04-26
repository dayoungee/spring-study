package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
 //       AppConfig appConfig = new AppConfig();
 //       MemberService memberService = appConfig.memberService();
        // 설정정보에 들어가 스프링이 직접관리함
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService",MemberService.class);

        Member member = new Member(1L, "MemberA", Grade.VIP);
        memberService.join(member);

        Member finderMember = memberService.findMember(1L);
        System.out.println("new Member   "+ member.getName());
        System.out.println("finderMember = " + finderMember.getName());
    }
}

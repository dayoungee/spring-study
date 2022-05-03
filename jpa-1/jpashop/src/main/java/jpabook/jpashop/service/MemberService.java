package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //jpa의 데이터 변경은 트랜잭션 안에서 이뤄져야한다. 리드온니 트루면 데이터 읽기에 최적화
public class MemberService {
    //@Autowired // 스프링 빈에서 인잭션된걸 가져옴
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     */
    @Transactional // 현재 클래스가 리드온니 트루이니 데이터 변경할 때는 따로 또 넣어줘야함
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity //해당 클래스를 테이블과 매핑한다.
@Getter @Setter // lombok 알아서 게터 세터 만들어줌
public class Member {

    @Id @GeneratedValue // id는 기본키 // 키본키를 자동으로 생성해줌
    /**GeneratedValue 기본키 생성 전략
     * IDENTITY : 디비에 위임하는 전략
     * 주로 mySql에서 사용 이 전략은 디비에 값을 저장하고 키본키를 구할 수 있을 때 사용함
     * 주의 ! 기본키를 디비에 저장되기 전에는 모름, 그래서 persist하는 순간 즉시 insert되기 때문에 쓰기지연이 동작하지 않음
     *
     * SEQUENCE : 유니크한 값을 순서대로 생성함. 시퀀스를 사용해서 기본키를 생성하기 때문에 오라클, h2에 사용
     * @SequenceGenerator를 사용해야함 자세한건  https://ttl-blog.tistory.com/123#--%--SEQUENCE%--%EC%A-%--%EB%-E%B- 참고
     *
     * TABLE : --
     *
     * AUTO : 기본값임. 알아서 자동으로 선택해줌 어떤 전략을 사용할지
     * */
    @Column(name = "member_id") // 필드를 컬럼에 매핑한다. 생략하면 필드명을 사용해서 매핑한다.
    private Long id;
    private String name;
    @Embedded // 새로운 값 타입을 직접 정의해서 사용할 수 있다. 값타입을 사용하는 곳에 표시, 기본 생성자 필수
    private Address address;
    @OneToMany(mappedBy = "member") // 일대 다 관계, mappedBy:  관계의 주체가 되는 쪽
    private List<Order> orders = new ArrayList<>();

}

package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 컬럼안에 새로운 컬럼이 존재한다. 이 주체에 사용하는 어노테이션 회원인데 이런 정보를 가지고 있어? 응집력을 떨어트린다. 값타입을 정의하는 곳
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {

    }// 기본 생성자 필수라서 넣은건가? 그런 거 같음

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}

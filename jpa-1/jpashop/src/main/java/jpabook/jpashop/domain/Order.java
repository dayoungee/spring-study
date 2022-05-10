package jpabook.jpashop.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders") // 엔티티 클래스에 매핑할 테이블 정보를 알려줌, 생략하면 클래스 이름으로 매핑함
@Getter @Setter
public class Order {
    @Id @GeneratedValue // id임을 명시
    @Column(name = "order_id") //컬럼에 매핑한다.
    private Long id;

    /**
     * 즉시 로딩과 지연 로딩 그리고 프록시
     *
     * 프록시 패턴 : 사용할 객체의 제어권을 위임함으로써, 객체에 대한 클라이언트의 요청을 대신 받아 전달(대리인, 가짜엔티티)
     *
     * 지연 로딩(LAZY LOADING)
     * 엔티티 A를 조회시 관련(Reference)되어 있는 엔티티 B를 한번에 가져오지 않는다. 프록시를 맵핑하고 실제 B를 조회할때 쿼리가 나간다.
     *
     * 즉시 로딩(EAGER LOADING)
     * 엔티티 A 조회시 관련되어 있는 엔티티 B를 같이 가져온다. 실제 엔티티를 맵핑한다. Join을 사용하여 한번에 가져온다.
     *
     * 되도록이면 지연로딩 을 쓰는게 좋다. A를 호출하면 연관된 B도 같이 호출된다. 그럼 한번의 쿼리를 호출했는데도 N+1이 호출되는 것이다. 이것을 N+1문제라고 부름
     * 그러니 지연로딩으로 잡아놓고, A의 B를 조회할 때, fetch join을 사용해보자
     *
     * @ManyToOne , @OneToOne 은 LAZY로 바꿔주자, ManyToOne과 OneToOne은 기본값이 EAGER다
     *
     * */
    @ManyToOne(fetch = FetchType.LAZY) // 다대 일 관계,
    @JoinColumn(name = "member_id") // 연관관계 매핑, 외래키를 매핑할 때 사용. 테이블 연관관계처럼 양방향일 수 없다. 그러니 연관관계 편의 메소드를 사용하자.
    private Member member; //주문 회원

    /**
     * casecade 영속성 전이
     *
     * 부모 엔티티가 영속화될 때 자식 엔티티도 같이 영속화되고, 부모 엔티티가 삭제될 때 자식엔티티도 삭제됨.
     * 일반적으로는 CascadeType.ALL 옵션을 줘서 사용한다.
     * ex) 게시글(부모)에 달린 댓글(자식)
     * */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) // enum 타입을 저장할 때, type이 STring이면 문자열 ORDINAL이면 숫자로
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    // ==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    // ==비즈니스 로직 ==//
    /**
     * 주문취소
     * */
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    // ==조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
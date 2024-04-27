package company.orderApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import company.orderApp.domain.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private String businessNumber;

    @ElementCollection(fetch = FetchType.EAGER) //jpa에 컬렉션 타입임을 알려주기 위해 사용.
    @Builder.Default //인스턴스 생성시 초기화.
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();


    // ==생성 메서드== //
    public static User createUser(String name, String username, String password, Address address, String businessNumber, String phoneNumber, String ...roles) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setBusinessNumber(businessNumber);
        for (String role : roles) {
            user.addRole(role);
        }

        return user;
    }

    //==setter==//

    //==비즈니스 로직==//
    public void updatePassword(String password) {
        setPassword(password);
    }
    public void addRole(String role) {this.roles.add(role);}




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

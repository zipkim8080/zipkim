package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.register.Registered;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertyRepositoryTest {

    @Autowired
    PropertyRepository propertyRepository;

    @Test
    public void 매물등록테스트() throws Exception{
        //given
        Property property = Property.builder()
                .addressName("테스트")
                .floor(2)
                .hasEv(true)
                .build();
        Registered registered = new Registered();
        property.register(registered);
        //when

        propertyRepository.save(property);
        Long findId = propertyRepository.findById(property.getId()).get().getId();
        //then
        Assertions.assertThat(findId).isEqualTo(property.getId());

     }
}
package com.kb.zipkim.domain.ocr.dto;

import com.kb.zipkim.domain.ocr.domain.DocVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocDTO {
    private Long no;      // auto-increment
    private Long id2;     // 매물 테이블 매물번호
    private String pno;   // 고유번호
    private String owner; // 소유주명
    private String add1;  // 건물명 주소
    private String add2;  // 도로명 주소
    private boolean trust; // 신탁여부
    private boolean attachment1;        // 압류여부
    private boolean attachment2;        // 가압류 여부
    private boolean leasehold;          // 임차권 등기명령
    private Long loan;                  // 근저당 총액
    private Date open_date;             // 열람일시


    // VO -> DTO 변환
    public static DocDTO of(DocVO vo){
        return vo == null? null : DocDTO.builder()
                .no(vo.getNo())
                .id2(vo.getId2())
                .pno(vo.getPno())
                .owner(vo.getOwner())
                .add1(vo.getAdd1())
                .add2(vo.getAdd2())
                .trust(vo.getTrust()) // boolean type이면 안뜨는데 왜?
                .attachment1(vo.getAttachment1())
                .attachment2(vo.getAttachment2())
                .leasehold(vo.getLeasehold())
                .loan(vo.getLoan())
                .open_date(vo.getOpen_date())
                .build();
    }

    // DTO -> VO 변환
    public DocVO toVo(){
        return DocVO.builder()
                .no(no)
                .id2(id2)
                .pno(pno)
                .owner(owner)
                .add1(add1)
                .add2(add2)
                .trust(trust)
                .attachment1(attachment1)
                .attachment2(attachment2)
                .leasehold(leasehold)
                .loan(loan)
                .open_date(open_date)
                .build();

    }
}

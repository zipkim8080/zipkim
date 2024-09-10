package com.kb.zipkim.domain.ocr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.Doc;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocVO {
    private Long no;      // auto-increment
    private Long id2;     // 매물 테이블 매물번호
    private String pno;   // 고유번호
    private String owner; // 소유주명
    private String add1;  // 건물명 주소
    private String add2;  // 도로명 주소
    private Boolean trust; // 신탁여부
    private Boolean attachment1;        // 압류여부
    private Boolean attachment2;        // 가압류 여부
    private Boolean leasehold;          // 임차권 등기명령
    private Long loan;                  // 근저당 총액
    private Date open_date;             // 열람일시
}

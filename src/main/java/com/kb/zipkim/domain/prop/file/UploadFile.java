package com.kb.zipkim.domain.prop.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kb.zipkim.domain.prop.entity.Property;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_id")
    private Property property;

    private String uploadFileName; //유저가 업로드한 파일명
    private String storeFileName;  //서버에서 관리하는 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}

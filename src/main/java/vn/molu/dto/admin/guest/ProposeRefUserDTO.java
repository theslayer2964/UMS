package vn.molu.dto.admin.guest;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProposeRefUserDTO {
    private String username;
    private String shopCode;
    private Integer typePropose;
    private Timestamp actionTime;
}

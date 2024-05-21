package vn.molu.domain.admin;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "PROGRAM")
@Entity
public class Program implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_PROGRAM_SEQ")
    @SequenceGenerator(name = "AD_PROGRAM_SEQ", sequenceName = "AD_PROGRAM_SEQ", allocationSize = 1)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "url")
    private String url;

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", programName='" + programName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

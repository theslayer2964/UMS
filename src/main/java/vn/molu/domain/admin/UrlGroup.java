package vn.molu.domain.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "AD_URL_GROUP", uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
public class UrlGroup implements Serializable {
	private Long urlGroupId;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_URL_GROUP_SEQ")
	@SequenceGenerator(name = "AD_URL_GROUP_SEQ", sequenceName = "AD_URL_GROUP_SEQ", allocationSize = 1)
	@Column(name = "url_group_id")
	public Long getUrlGroupId() {
		return urlGroupId;
	}

	public void setUrlGroupId(Long urlGroupId) {
		this.urlGroupId = urlGroupId;
	}

	private String code;

	@Basic
	@Column(name = "code", length = 30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String name;

	@Basic
	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String description;

	@Basic
	@Column(name = "description", length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String flgDelete;

	@Basic
	@Column(name = "flg_delete", length = 1)
	public String getFlgDelete() {
		return flgDelete;
	}

	public void setFlgDelete(String flgDelete) {
		this.flgDelete = flgDelete;
	}

	private Timestamp createdDate;

	@Basic
	@Column(name = "created_datetime")
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	private Timestamp modifiedDate;

	@Basic
	@Column(name = "modified_datetime")
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	private List<Url> urls;
	@OneToMany(mappedBy = "urlGroup")
	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}

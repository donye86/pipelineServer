package com.rym.module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * 实体抽象基类
 * @author zqy
 * @version
 * <pre>
 * Author	Version		Date		Changes
 * Onglu 	1.0  2018年04月11日 Created
 *
 * </pre>
 * @since 1.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, insertable = false, updatable = false)
    protected ID id;

    @Override
    public int hashCode() {
        if (null == id) {
            return ObjectUtils.nullSafeHashCode(this);
        }
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj) {
            return false;
        }

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        if (null == id) {
            return this.equals(obj);
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;
        return null != id && id.equals(that.getId());
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return null == id;
    }
}

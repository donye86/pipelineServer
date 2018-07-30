package com.rym.module.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * 抽象的日期基类
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
public abstract class BaseDatetimeEntity<ID extends Serializable> extends AbstractEntity<ID> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 创建时间，用于展示
     */
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    protected Date createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected Date updatedAt;

    public BaseDatetimeEntity(ID id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BaseDatetimeEntity<?> that = (BaseDatetimeEntity<?>) o;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        return updatedAt != null ? updatedAt.equals(that.updatedAt) : that.updatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}

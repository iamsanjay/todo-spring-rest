package org.techkaksha.todo.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

@Entity
public class Todos {
    private String content;

    @Id
    @GeneratedValue(generator = "custom-generator",
            strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "custom-generator",
            strategy = "org.techkaksha.todo.model.BaseIdentifierGenerator")
    private String id;

    @Column(name="user_id")
    private String userId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Instant modifiedAt;

    public Todos() {
    }

    public Todos(String userId, String content) {
        this.content = content;
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Todos{" +
                "content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
class BaseIdentifierGenerator extends UUIDGenerator {

    private static final int NUMBER_OF_CHARS_IN_ID_PART = -5;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        // Generate a custom ID for the new entity
        final String uuid = super.generate(session, obj).toString();

        final long longTimeRandom = System.nanoTime() + System.currentTimeMillis()
                + new Random().nextLong() + Objects.hash(obj);

        final String timeHex = Long.toHexString(longTimeRandom);
        return StringUtils.substring(timeHex, NUMBER_OF_CHARS_IN_ID_PART)
                + StringUtils.substring(uuid, NUMBER_OF_CHARS_IN_ID_PART);
    }
}
package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table( name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_follow_follower_followee",
                        columnNames = {"follower_id", "followee_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private Profile follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followee_id", nullable = false)
    private Profile followee;

    public Follow(Profile follower, Profile followee) {
        this.follower = follower;
        this.followee = followee;
    }
}

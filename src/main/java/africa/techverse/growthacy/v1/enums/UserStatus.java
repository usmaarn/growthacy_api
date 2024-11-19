package africa.techverse.growthacy.v1.enums;


import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE(5),
    SUSPENDED(10),
    BANNED(20);

    private final int value;
    UserStatus(int value) {
        this.value = value;
    }
}